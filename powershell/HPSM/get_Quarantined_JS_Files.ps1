#####################################################################################
#Tested on PowerShell v3.0
#####################################################################################
<#
 # This script is used to report script files which are Quarantined
 # Message From Jerry:
 1)	We will regard any JS test with 1st line comment of 
   //Quarantined
   as disabled tests for statistics purpose.
  2)	Meanwhile use xdescribe(…) in test body to skip its running in Jasmine framework.

Please follow the rules above in subsequent tests developing.
  
#>
# you need to configure path you want to scan
Param([String]$root_folder=".")
$Quarantined_js_files = @()
$(dir -Path $root_folder -Filter *.js -Recurse) |%{ 
$content = cat $_.FullName |Out-String
 
if ($content.StartsWith("//Quarantined")){
  $Quarantined_js_files += $_.FullName
}
}
$Quarantined_js_files|ft -AutoSize




