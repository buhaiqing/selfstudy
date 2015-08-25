#requires -version 3
$pass = convertto-securestring "1Qaz2wsx3edc" -asplaintext -force
$mycred = new-object -typename System.Management.Automation.PSCredential -argumentlist "sm-ant",$pass


$res =  iwr https://csvnchn-pro.chn.hp.com:18580/svn/itsm-sm/ServiceManager/branches/9.4x/9.41/parent/build-info.properties -Credential $mycred | ConvertFrom-StringData
$num =$res['release.string'].split('.')
$i = [int] $($num[2] -1)
$num[2] = "{0:D4}" -f $i

$release_string = $num -join "."
$release_string 



