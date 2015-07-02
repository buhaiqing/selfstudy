#requires -version 3.0
 function SendAlertEmail{
  param(
  [string] $Subject,
  [string] $Body
  )
    Send-MailMessage -SmtpServer "smtp3.hp.com" -From "hai-qing.bu@hp.com" -Subject $Subject -Body $Body -To "PDL_SPM_DevOps@hp.com"
}

 $response = invoke-restmethod http://16.186.72.7/api/machine_list|ConvertTo-Json |ConvertFrom-Json
 $machines = $response.value
 $fpe_machines = $machines|?{($_.owner -eq 'sm-client' -or $_.owner -eq 'SMA' -or $_.owner -eq "SM-PD" -or $_.owner -eq "SM_ADZHOU" -or $_.owner -eq "SM_ADZHOU1" -or $_.owner -eq "SM_ADZHOU2" -or $_.owner -eq "SM_CI_SIT" -or $_.owner -eq 'Jenkins') -and $_.status -eq "Down" -and @(1, 100, 133, 308, 391, 399) -notcontains $_.id}|select id,name,ip
 #$fpe_machines = $machines|?{ $_.owner -eq "sm-client" -and $_.status -eq "Down"}
 
 if ($fpe_machines.Length -gt 0){
  $message = $fpe_machines|ft|Out-String
  $subject = "[ACTION REQUIRED]Some of Automation VMs are not in good condition"
  $body =@"
    please check below machines which agent status is down:
    $message
"@
 SendAlertEmail -subject $subject -Body $body

 }
 



 
 
