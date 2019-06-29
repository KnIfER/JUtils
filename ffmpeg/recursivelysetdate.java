package ffmpeg;

import java.io.File;

public class recursivelysetdate {

	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		recurse(new File("F:\\UE_4.17\\worldengine_CyLand1.0\\"));
	}

	private static void recurse(File p) {
		File[] arr = p.listFiles();
		if(arr!=null)
		for(File f:arr) {
			f.setLastModified(0);
			if(f.isDirectory())
				recurse(f);
		}
	}
	
	
	

}
