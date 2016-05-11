#requires -version 3.0
$r =  Invoke-RestMethod http://smcm-jenkins-rhel7.hpeswlab.net:8080/jenkins/queue/api/json
$r = @($r.items| %{$_.task.url} | group| ?{$_.count -gt 2})

$r|Select-Object -Property count,name
function SendAlertEmail{
  param(
  [string] $Subject,
  [string] $Body
  )
    Send-MailMessage -SmtpServer "smtp3.hp.com" -From "hai-qing.bu@hpe.com" -Subject $Subject -Body $Body -To "PDL_SPM_DevOps@hpe.com"
}
if($r.Length -gt 0){
$message =  $r|Select-Object -Property count,name|ft -AutoSize|Out-String
 $subject = "[ACTION REQUIRED]There are some Jenkins Jobs jamed in queue"
  $body =@"
    $message
"@
# SendAlertEmail -subject $subject -Body $body
}

