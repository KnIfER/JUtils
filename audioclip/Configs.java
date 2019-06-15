package audioclip;

import java.io.File;
import java.io.IOException;

import javafx.scene.input.KeyCode;

public class Configs {
	public static int getKeyMappingIndex(KeyCode keyCode) {
		int index=-1;
		switch(keyCode) {
			case Z:
				index=0;
			break;
			case X:
				index=1;
			break;
			case C:
				index=2;
			break;
			case V:
				index=3;
			break;
			case B:
				index=4;
			break;
			case N:
				index=5;
			break;
			case M:
				index=6;
			break;
			case COMMA:
				index=7;
			break;
			case PERIOD:
				index=8;
			break;
			case SLASH:
				index=9;
			break;
			case W:
				index=9;
			break;
		}
		return index;
	}



	public static void dumpLonelyInteger(File tmp, int val) {
		if(!tmp.exists()) tmp.mkdirs();
		File tmp0 = new File(tmp, String.valueOf(val));
		if(tmp.isDirectory()) {
			File[] lst = tmp.listFiles();
			if(lst!=null && lst.length>0) {
				if(lst.length>1) {
					for(int i=1;i<lst.length;i++) {
						lst[i].delete();
					}
				}
				lst[0].renameTo(tmp0);
			} else try {
					tmp0.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}		
	}
	public static int getLonelyInteger(File tmp) {
		if(tmp.exists()) {
			if(tmp.isDirectory()) {
				try {
					File[] lst = tmp.listFiles();
					if(lst!=null && lst.length>0)
						return Integer.valueOf(lst[0].getName());
				}catch(Exception e) {}
			}
		}		
		return -1;
	}
	public static boolean getLonelyBoolean(File tmp) {
		return tmp.exists();
	}
}
