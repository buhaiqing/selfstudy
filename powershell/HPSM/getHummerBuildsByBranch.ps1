$build_branch = 'SM-9.4x-DAILY'
$builds = irm -Uri http://16.186.72.7/builds/getBuildsByBranch.json -body @{all=true;build_branch=$build_branch}
$builds | Out-GridView


