#!/bin/bash
## Delete all files except file1 ##
rm !(file1)

## Delete all file except file1 and file2 ##
rm !(file1|file2)

## Delete all file except all zip files ##
rm !(*.zip)

