#!/usr/bin/env python
# -*-coding=utf-8 -*-
from datetime import *
import os
from pathlib import Path


import sys
#reload(sys)
#sys.setdefaultencoding("ascii")

theta = 0;
flibf = r"D:\Downloads\PotPlayer\PotPlayerMini64.ini"

import codecs


#ffmpegexe = r'D:\Downloads\ffmpeg-4.1.3-win64-shared\bin\ffmpeg.exe'
ffmpegexe = r'ffmpeg.exe'
relaunch=True
    
while True:
    #print "\r\n"
    dowloadurl = ""
    videoPathName = ""
    tmp_reader=""
    tmp_reader = str(input("todownload"))
    while(tmp_reader!=""):
        if tmp_reader=="1": 
            relaunch=False
        else:
            if len(tmp_reader)>1 and tmp_reader[0].lower()=='h' and tmp_reader[1].lower()=='t':
                dowloadurl=tmp_reader
            else:
                videoPathName = tmp_reader;
        tmp_reader = str(input("todownload"))
        
    if videoPathName.startswith("D:\\Downloads\\"):
        videoPathName=videoPathName[13:]
    if videoPathName.find(":")<0:
        videoPathName = "F:\\vide\\"+videoPathName
    
    _ConTin_ = True
    cc=0
    while _ConTin_:
        videoPathI = videoPathName
        if videoPathI[0]=='"':
            videoPathI=videoPathI[1:len(videoPathI)-1]
        o = 0
        while(videoPathI.find("\\",o)!=-1):
            o=videoPathI.find("\\",o)+1
        videoName = videoPathI[o:]
        videoPath = videoPathI[:o]
        
        if "" in videoName:
            #print ("bilibili  :  "+videoName)
            #D:\Downloads\超甜的软妹♡ - 禾子卡哇伊 - 哔哩哔哩直播，二次元弹幕直播平台.flv
            idx_1=videoName.find(" - ")
            if idx_1!=-1:
                idx_2=videoName.find(" - ",idx_1+1)
                if idx_2 > 0 and idx_2+3<=len(videoName):
                    videoName=videoName[idx_1+3:idx_2+3]+videoName[0:idx_1+3]+videoName[idx_2+3:len(videoName)]
                    print (":"+videoName)#str(idx_1)+":"+str(idx_2)+
        #if True:
        #    break
        #获取文件名END
        print("videoName",videoName,videoPath)
        new_path = videoPath+videoName[:-4]+"_streamNo."+str(cc)+".flv"
        my_file = Path(new_path)
        while my_file.is_file():
            cc+=1
            new_path = videoPath+videoName[:-4]+"_streamNo."+str(cc)+".flv"
            my_file = Path(new_path)

        mingling=" -i "+'"'+dowloadurl+'"'+   " -c copy "   +'"'+new_path+'"'
        print ("\r\ncommand on queue: FFMPEG "+mingling)
        mingling = ffmpegexe + mingling
        os.system(mingling+" -n")





        
    
    
    
    
