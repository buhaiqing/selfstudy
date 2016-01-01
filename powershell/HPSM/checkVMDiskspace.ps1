#requires -version 3
# check diskspace of VM, send alert email if less than 1.5 GB
 function SendAlertEmail{
  param(
  [string] $Subject,
  [string] $Body
  )
    Send-MailMessage -SmtpServer "smtp3.hp.com" -From "PDL_SPM_CI_Notification@hpe.com" -Subject $Subject -Body $Body -To "PDL_SPM_CI_Notification@hpe.com"
}

$data =[xml] (iwr http://smcm-jenkins-rhel7.hpswlabs.hp.com:8080/jenkins/computer/api/xml)

$list =@($data.computerSet.computer|? {$_.displayName -ne 'master' }|?{

$size = $_.monitorData."hudson.node_monitors.TemporarySpaceMonitor".size

$res =  [double]($size) -lt 1.5GB 
 $res
}| Select-Object  displayName,offlineCauseReason)


 if ($list.Length -gt 0){
  echo "send alert email"
  $message = $list|Select-Object -Property displayName|ft -AutoSize|Out-String
  $subject = "[ACTION REQUIRED]Disk space of Some of Jenkins nodes are lower than 1.5GB"
  $body =@"
    please check below machines :
    $message
"@
 SendAlertEmail -subject $subject -Body $body

 }
