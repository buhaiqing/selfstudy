function SendAlertEmail{
  param(
  [string] $Subject,
  [string] $Body
  )
    Send-MailMessage -SmtpServer "smtp3.hp.com" -From "hai-qing.bu@hp.com" -Subject $Subject -Body $Body -To "bhq@hp.com"
}

function Check-LogicalDisk {
param (
    [string]$computername = "localhost"
)
	$a= Get-WmiObject -Class Win32_LogicalDisk -ComputerName $computername| ?{$_.DeviceID -eq "c:"} |  select pscomputername, DeviceID, Size,FreeSpace
	$ratio = $a.FreeSpace*100/$a.size
  
	
    if($ratio -gt 90){
      SendAlertEmail -Subject "disk is over than 90% on computer $a.pscomputername}" -Body "please fix it as soon as possible"
    }	
}

#test 
# Check-LogicalDisk