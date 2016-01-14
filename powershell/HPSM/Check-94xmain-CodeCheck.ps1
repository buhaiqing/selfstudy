$xml= [xml](iwr http://shcm-2008-04.chn.hp.com:8080/job/CI-SM-9.4x-CodeCheck-RTE-TEST-WIN/api/xml)
$build = $xml.freeStyleProject.build.Get(0).url
$xml=[xml](iwr "$build/api/xml")
if ($xml.freeStyleBuild.result -eq $null){
  echo "the build is still not finished"
  $rs = $xml.freeStyleBuild.id -split "_"
  $rs[1]= $rs[1] -replace "-",":"
  
  $d = [datetime]($rs -join " ")
  $diff = ([System.DateTime]::now - $d )
  if( $diff.Days-lt 1){
    exit 1
  }
}
