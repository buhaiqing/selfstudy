 function SendAlertEmail{
  param(
  [string] $Subject,
  [string] $Body
  )
    Send-MailMessage -SmtpServer "smtp3.hp.com" -From "hai-qing.bu@hp.com" -Subject $Subject -Body $Body -To "PDL_SPM_DevOps@hp.com"
}

$data = irm http://smcm-jenkins-rhel7.hpswlabs.hp.com:8080/jenkins/computer/api/json|ConvertTo-Json|ConvertFrom-Json
$list = @($data.computer|?{$_."offline" -eq 'true'})

 if ($list.Length -gt 0){
  $message = $list|Select-Object -Property displayName,offlineCauseReason|ft -AutoSize|Out-String
  $subject = "[ACTION REQUIRED]Some of Jenkins nodes are not in good condition"
  $body =@"
    please check below machines :
    $message
"@
 SendAlertEmail -subject $subject -Body $body

 }


