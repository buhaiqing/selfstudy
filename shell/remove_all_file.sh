#!/bin/bash
## Delete all files except file1 ##
rm !(file1)
#OR
find . -not -name "file1" -delete

## Delete all file except file1 and file2 ##
rm !(file1|file2)
#OR
find . -not -name "file1" -not -name "file2" -delete

## Delete all file except all zip files ##
rm !(*.zip)
#OR
find . -not -name "*.zip" -delete

