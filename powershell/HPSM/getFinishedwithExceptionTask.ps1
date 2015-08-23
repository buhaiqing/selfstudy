#requires -version 3.0
[cmdletbinding()]
Param([parameter(mandatory=$true)][string]$build_id)
echo $build_id
$tasks = $(Invoke-RestMethod "http://16.186.72.7/api/task_list?build=$build_id"|ConvertTo-Json |ConvertFrom-Json).value |?{$_.ci -eq 'Yes'}|select -ExpandProperty id
$res =@()
$issued_tasks =@{}

$tasks | %{
  $task_id = $_
  $res = Invoke-RestMethod -uri http://16.186.72.7/tasks/$task_id.json

  if ($res.status_history.Contains("Finished with exceptions")){
    $issued_tasks[$task_id] = $res.status_history
  }
  
  
}
echo "Count of Issued Tasks: $($issued_tasks.Count)"
$issued_tasks | ft -AutoSize -Wrap

