#!/usr/bin/env python
# -*-coding=utf-8 -*-
from datetime import *
import os

import sys
#reload(sys)
#sys.setdefaultencoding("ascii")

theta = 0;
flibf = r"D:\Downloads\PotPlayer\PotPlayerMini64.ini"

import codecs


#ffmpegexe = r'D:\Downloads\ffmpeg-4.1.3-win64-shared\bin\ffmpeg.exe'
ffmpegexe = r'ffmpeg.exe'
    
    
while True:
    #print "\r\n"
    videoPathNames = []
    videoPathName=""
    videoPathName = str(input("toconvert"))
    while(videoPathName!=""):
        videoPathNames.append(videoPathName)
        videoPathName = str(input("toconvert"))
        
	
    for videoPathI in videoPathNames:
        if videoPathI[0]=='"':
            videoPathI=videoPathI[1:len(videoPathI)-1]
        o = 0
        while(videoPathI.find("\\",o)!=-1):
            o=videoPathI.find("\\",o)+1
        videoName = videoPathI[o:]
        videoPath = videoPathI[:o]
        #获取文件名END
        print("videoName",videoName,videoPath)
        mingling=" -i "+'"'+videoPathI+'"'+   " -c copy "   +'"'+videoPath+videoName[:-4]+"_ConvertNo."+str(0)+".mp4"+'"'
        print ("\r\ncommand on queue: FFMPEG "+mingling)
        mingling = ffmpegexe + mingling
        os.system(mingling+" -n")





        
    
    
    
    
