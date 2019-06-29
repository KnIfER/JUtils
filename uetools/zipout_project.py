import zipfile, os, re
import time, datetime
from pathlib import Path

RejectFilter = ["Binaries".lower()
, ".vs".lower()
, "Intermediate".lower()
, "Saved".lower()
]

skipInput=True
entertoexit=True
OutputDirectory = "F:\\UE_4.17"
logfiles=True
logsize=True
double_check=False
pwd = os.getcwd()
PathToCompress = pwd

def recurseFiles(input_path,result):
    files = os.listdir(input_path)
    for fI in files:
        if os.path.isdir(input_path+'/'+fI):
            if fI.lower() not in RejectFilter:
                #print(fI)
                recurseFiles(input_path+'/'+fI,result)
        else:
            result.append(input_path+'/'+fI)            

def humanbytes(B):
   'Return the given bytes as a human friendly KB, MB, GB, or TB string'
   B = float(B)
   KB = float(1024)
   MB = float(KB ** 2) # 1,048,576
   GB = float(KB ** 3) # 1,073,741,824
   TB = float(KB ** 4) # 1,099,511,627,776

   if B < KB:
      return '{0} {1}'.format(B,'Bytes' if 0 == B > 1 else 'Byte')
   elif KB <= B < MB:
      return '{0:.2f} KB'.format(B/KB)
   elif MB <= B < GB:
      return '{0:.2f} MB'.format(B/MB)
   elif GB <= B < TB:
      return '{0:.2f} GB'.format(B/GB)
   elif TB <= B:
      return '{0:.2f} TB'.format(B/TB)

print("1 Logfiles=true      CURRENT: "+str(logfiles))
print("2 Doublecheck=true   CURRENT: "+str(double_check))
print(";Path/to/compress    CURRENT: "+PathToCompress)
print("Path/to/output       DEFAULT: "+OutputDirectory)
print("Sample:◀ 1")
print("      :◀ H:\\UnrealProjectBackups\\2019")
print("      :◀ ;F:\\myUnrealProject")
print("      :◀ \\n")
print()


while True:
    mTimeArray = time.localtime(int(time.time()))
    mTimeStamp = time.strftime("%Y-%m-%d", mTimeArray)
    tmp = ""
    if not skipInput:
        str(input("OutputDirectory "))
    if tmp != "":
        if re.match("^[0-9 ]{1,}$", tmp, flags=0):
            args = tmp.split(" ")
            for aI in args:
                if aI=='1':
                    logfiles=True
                elif aI=='2':
                    double_check=True
        if tmp[0] == ';':
            tmp = tmp[1:]
            if os.path.exists(tmp) and os.path.isdir(tmp):
                PathToCompress = tmp
        elif os.path.exists(tmp) and os.path.isdir(tmp):
            OutputDirectory = tmp
    else:
        if not os.path.exists(OutputDirectory) and os.path.isdir(OutputDirectory):
            if not os.makedirs(path):
                print ("OutputDirectory invalid : "+OutputDirectory)
                continue
        CompressName=''        
        if not os.path.exists(PathToCompress) and os.path.isdir(PathToCompress):
            print ("PathToCompress invalid : "+OutputDirectory)
            continue
        else:
            CompressName=os.path.basename(PathToCompress)
            CompressName = CompressName[0].upper() + CompressName[1:]
        packpathname = os.path.join(OutputDirectory, CompressName)
        cc=0
        new_path = packpathname+"_BackupNo."+mTimeStamp+"-"+str(cc)+".zip"
        my_file = Path(new_path)
        while my_file.is_file():
            cc+=1
            new_path = packpathname+"_BackupNo."+mTimeStamp+"-"+str(cc)+".zip"
            my_file = Path(new_path)
        packpathname=new_path
        print ( "-------------------")
        print ( "--: "+PathToCompress)    
        print ( "--> "+packpathname)    

        filelists = []
        recurseFiles(PathToCompress,filelists)
        print(str(len(filelists))+" files to compress"+"...")
        if double_check:
            if str(input("double checking... "))!='':
                continue
        BackupPack = zipfile.ZipFile(packpathname, "w", zipfile.ZIP_DEFLATED, True)
        for fI in filelists:
            if logfiles:
                print(fI)
            BackupPack.write(fI, fI[len(pwd)+1:])
            #break;
        BackupPack.close()
        sizeis = ""
        if logsize:
            sizeis = humanbytes(str(os.path.getsize(packpathname)))
        if not (skipInput and not entertoexit):
            print ( "done. "+sizeis)    
        print ( "-------------------")
        if skipInput:
            if entertoexit:
                input("press enter to continue...")
            break;
        
        