#!/usr/bin/env python
# -*-coding=utf-8 -*-
from datetime import *
from pathlib import Path
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
    f = codecs.open(flibf,"r","utf-16")
    flib = f.read()
    f.close()
    #获取文件名
    bmlOff = flib.find("[BMList]")
    bmlOff2 = flib.find("=\r",bmlOff)
    bml = flib[bmlOff:bmlOff2]
    #print bml
    o=bml.find(str(theta)+"=")
    findCount = -1#这是可能要处理的文件个数
    while(bml.find("\n",o)!=-1):
        o=bml.find("\n",o)+2
        findCount+=1
    print ("acc = ",findCount,"\r\n")
    
    #print "\r\n"
    videoPathNames = []
    videoPathName=""
    videoPathName = str(input("videoPathName"))
    while(videoPathName!=""):#q
        videoPathNames.append(videoPathName)
        videoPathName = str(input("videoPathName"))
        
	
    for videoPathI in videoPathNames:
        for fileIdx in range(1,findCount+1):
            ducOff = bml.find(str(1+theta+fileIdx-1)+"=")
            ducOff2 = bml.find("\r",ducOff)
            try:
                videoPathName = bml[ducOff:ducOff2].split("=")[1]
            except IndexError:
                print ("\n\nError_log_ theta threshold is too big \n\n")
            #print ("acc = ",videoPathI,videoPathName)
            if(videoPathName!=videoPathI and videoPathI!='"'+videoPathName+'"'):
                continue
            o = 0
            while(videoPathName.find("\\",o)!=-1):
                o=videoPathName.find("\\",o)+1
            videoName = videoPathName[o:]
            videoPath = videoPathName[:o]
            #获取文件名END
            print("videoName",videoName,videoPath)
            #获取当前文件的书签列表
            #print flib.find("[BMItem_"+str(theta+fileIdx))
            itemSectionOff = flib.find("[BMItem_"+str(theta+fileIdx))
            #print flib.find("=\r",itemSectionOff)
            itemSectionOff2 = flib.find("=\r",itemSectionOff)
            section = flib[itemSectionOff:itemSectionOff2]

            bookMarkList = []
            for i in section.split("\r\n"):
                tmp = i.split("*")[0]
                if tmp.find("=")!=-1:
                    bookMarkList.append(int(tmp.split("=")[1]))
            #print bookMarkList
            #获取当前文件的书签列表END


            cc = 0
            for i in range(int(len(bookMarkList)/2)):
                #print "doin"
                start = bookMarkList[i*2]
                end = bookMarkList[i*2+1]
                tm = start/1000
                h=int(tm/60/60)
                m=int(tm%(60*60)/60)
                s=int(tm%(60*60)%60)
                #print(start)
                tm1 = str(time(h,m,s))   +"."+str(start%1000)
                
                tm = end/1000
                h=int(tm/60/60)
                m=int(tm%(60*60)/60)
                s=int(tm%(60*60)%60)
                tm2 = str(time(h,m,s))   +"."+str(end%1000)
                
                new_path = videoPath+videoName[:-4]+"_SchivoNo."+str(cc)+".mp4"
                my_file = Path(new_path)
                while my_file.is_file():
                    cc+=1
                    new_path = videoPath+videoName[:-4]+"_SchivoNo."+str(cc)+".mp4"
                    my_file = Path(new_path)
                cc+=1
                mingling=(tm1)+"  -t  "+str(1.0*(end-start)/1000)+" -accurate_seek "   +" -i "+'"'+videoPathName+'"'+   " -codec copy -avoid_negative_ts 1 -strict -2 "   +'"'+new_path+'"'
                print ("\r\ncommand on queue: FFMPEG "+mingling)
                mingling = ffmpegexe + r' -ss '+ mingling
                
                os.system(mingling+" -n")

        #os.system("pause")







        
    
    
    
    
