package uetools;



import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import ffmpeg.CMN;

public class deloldhotloads {
	public static Charset defchar = Charset.forName("utf8");
	public static Pattern numeric_pattern = Pattern.compile(".*-[0-9]{1,}\\.(dll|pdb)+");
	
	//deleting all "Binaries/Win64/UE4Editor-ModuleName-0001.(dll|pdb)"
	public static void main(String[] args) {
		String projectPath = PU.getProjectPath();
		CMN.Log("projectPath : "+projectPath);
		//CMN.Log(numeric_pattern.matcher("UE4Editor-ModuleName-0001.dll").matches());
		
		File win641 = new File(projectPath, "Binaries\\Win64");
		File[] arr = win641.listFiles();
		if(arr!=null)
		for(File fI:arr) {
			if(numeric_pattern.matcher(fI.getName()).matches())
				CMN.Log("deleting..."+fI.delete()+ "...  "+fI.getPath());
		}
		arr = new File(projectPath, "Plugins").listFiles();
		if(arr!=null)
		for(File fI:arr) {
			File win64I = new File(fI, "Binaries\\Win64");
			if(win64I.exists()) {
				File[] arrI = win64I.listFiles();
				if(arrI!=null)
				for(File ffI:arrI) {
					if(numeric_pattern.matcher(ffI.getName()).matches())
						CMN.Log("deleting..."+ffI.delete()+ "...  "+ffI.getPath());
				}
			}
		}
		
	}
	
}
