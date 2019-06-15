package ffmpeg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//common
public class CMN{
	public static void Log(Object... o) {
		String msg="";
		if(o!=null)
		for(int i=0;i<o.length;i++) {
			if(Exception.class.isInstance(o[i])) {
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				PrintStream p = new PrintStream(s);
				((Exception)o[i]).printStackTrace(p);
				msg+=s.toString();
			}
			msg+=o[i]+" ";
		}
		System.out.print(msg);
		System.out.println();
	}
	public static void Log2(Object... o) {
		String msg="";
		if(o!=null)
		for(int i=0;i<o.length;i++) {
			if(Exception.class.isInstance(o[i])) {
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				PrintStream p = new PrintStream(s);
				((Exception)o[i]).printStackTrace(p);
				msg+=s.toString();
			}
			msg+=o[i];
		}
		System.out.print(msg);
	}
	
}