import os
import time

        
def switch_video():
    # os.system("adb shell input swipe 540 1300 540 500 100")
    # 适用于1280x720
    os.system('adb shell input swipe 540 700 540 100 100')
    
    
    
i = 1
while(1):
    print("------------------第" + str(i) + "个视频------------------")
    i+=1; 
    os.system('adb shell input swipe 540 700 540 100 100')
    #os.system('adb shell input swipe 540 100  540 700 100')
    time.sleep(0.8)