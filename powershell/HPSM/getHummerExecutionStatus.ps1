#requires -version 3.0
[cmdletbinding()]
Param([parameter(mandatory=$true)][string]$build_id)
#$build_id = "20151206175540"
$res = (Invoke-RestMethod "http://16.186.72.7/api/task_list?build=$build_id")| %{
$passed = $_.passing_ratio.passed
$failed_case = $_.passing_ratio.failed
$case_number = $_.passing_ratio.case_number
$passing_ratio = $_.passing_ratio."passed%"

$_| Add-Member -MemberType NoteProperty -Name passingratio $passing_ratio
$_|Add-Member -MemberType NoteProperty -Name passed -Value $passed
$_| Add-Member -MemberType NoteProperty -Name failed $failed_case
$_| Add-Member -MemberType NoteProperty -Name case_number $case_number


$_
}|?{$_.ci -eq 'Yes' -and ([double]$_.passingratio) -lt 75 }|Sort-Object -Property group,passingratio,category,sub_catetory,duration|Out-GridView -PassThru
$res  | ft  
