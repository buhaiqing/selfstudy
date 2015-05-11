[cmdletbinding()]
Param([parameter(mandatory=$true)][string]$build_id)
# you need to specify the build number
#$build_id = "20150507091513"

$art_all = 0
$art_failed = 0
$res = (Invoke-RestMethod "http://16.186.72.7/api/task_list?build=$build_id")| %{
$passed = $_.passing_ratio.passed
$failed_case = $_.passing_ratio.failed
$case_number = $_.passing_ratio.case_number
$passing_ratio = $_.passing_ratio."passed%"
$art_all += $case_number
$art_failed += $failed_case

$_ |add-Member -MemberType NoteProperty -Name passed -Value $passed
$_| Add-Member -MemberType NoteProperty -Name failed $failed_case
$_| Add-Member -MemberType NoteProperty -Name case_number $case_number
$_| Add-Member -MemberType NoteProperty -Name passed% $passing_ratio

$_
} |Group-Object -Property pd,group


$result = $res|%{
    $r  = New-Object -TypeName psobject
    $r| Add-Member -MemberType NoteProperty -Name "pd,group"  -Value ""
    $r | Add-Member -MemberType NoteProperty -Name "Task Count" -Value 0
    $r| Add-Member -MemberType NoteProperty -Name TotalCases -Value 0
    $r| Add-Member -MemberType NoteProperty -Name FailedCases -Value 0
    $r | Add-Member -MemberType NoteProperty -Name PassingRatio -Value 0

    $r."pd,group" = $_.Name  | Out-String
    $r."Task Count" = $_.Count |Out-String
    $_.Group| %{
      $r.TotalCases +=$_.case_number
      $r.FailedCases += $_.failed
      $r.PassingRatio = (($r.TotalCases - $r.FailedCases)/$r.TotalCases)*100 
    }

    $r

} 

$art_ratio = (($art_all - $art_failed)/$art_all) * 100
echo  "========Summary Report for build $build_id=================="
$result |Out-GridView -Title "SM941 ART Summary Report for build $build_id (Total case number:$art_all, ratio: $art_ratio)"

