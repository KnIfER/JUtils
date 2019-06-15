#!/usr/bin/env python
# -*-coding=utf-8 -*-
import os
import re


print("1 md5 check off")
print("2 don't delete automatically on md5 check")
print("3 output_concat_list=false")
print("4 output_kuaijianjia_proj=false")
print("sample:◀ 1")
print("      :◀ F:\\DCIM\\New folder")
print("      :◀ \\n")
print()



#print(re.match("^[0-9 ]{1,}$", "1 2 3x", flags=0))

while True:
    videoPathName = str(input("concat args "))
    args=""
    targetPath=""
    while(videoPathName!=""):
        if re.match("^[0-9 ]{1,}$", videoPathName, flags=0):
            args=videoPathName
        else:
            targetPath=videoPathName
        videoPathName = str(input("concat args "))

    os.system('java -jar concat_helper.jar "'+targetPath+'" '+args)