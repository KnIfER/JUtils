package shape;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ffmpeg.CMN;


public class vectorpathTweaker {
	public static void main(String[] args) {
		//改造vector path
		float viewportHeight=24, viewportWidth=24;
		float scaler = 0.75f;
		float scalerY = scaler;
		float transX=0f;
		float transY=0f;
		boolean tranverse = false;
		boolean flipX = false;
		//String pathdata = "M20,19v-4L8,7 12,4.5 20,9V5L12,0.5 4,5v4 l12.63,7.89L12,19.5 4,15v4l8,4.5z";
		//pathdata = "M20,19v-4L8,7 12,4.5 20,9V5L12,0.5 4,5v4 L16.63,16.98 12,19.5 4,15v4L12,23.5z";
		String pathdata = "M0,12L0,24 24,24 24,12 0,12z";
		StringBuilder pathbuilder = new StringBuilder();
		Pattern reg = Pattern.compile("M|l|L|z|s|c|V|v|h|H| ");
		Matcher m = reg.matcher(pathdata);
		int idx=0;
		String lastCommand;
		while(m.find()) {
			int now =m.start();
			if(idx!=-1 && now>idx) {
				//CMN.Log(pathdata.substring(idx+1,now));
				pathbuilder.append(lastCommand = pathdata.substring(idx,idx+1));
				String[] arr = pathdata.substring(idx+1,now).split(",");
				if(arr.length==2) {
					float x=Float.valueOf(tranverse?arr[1]:arr[0]);
					x=(1-scaler)*viewportWidth/2 + x*scaler + transX;
					if(flipX) x = viewportWidth-x;
					pathbuilder.append(trimFloatString(String.format("%.2f", x)));
					pathbuilder.append(",");
					x=Float.valueOf(tranverse?arr[0]:arr[1]);
					x=(1-scalerY)*viewportHeight/2 + x*scalerY + transY;
					pathbuilder.append(trimFloatString(String.format("%.2f", x)));
				}
				else {
					String key = pathdata.substring(idx+1,now);
					if(lastCommand!=null)
					try {
						float val = Float.valueOf(key);
						pathbuilder.append(trimFloatString(String.format("%.2f", scaler*val)));
					} catch (NumberFormatException e) {
						//CMN.Log(key);
						pathbuilder.append(key);
					}
					else
					pathbuilder.append(key);
				}
			}else
				pathbuilder.append(pathdata.substring(idx,now));
			//CMN.Log(pathdata.substring(0,));
			idx=now;
		}
		pathbuilder.append(pathdata.substring(idx,pathdata.length()));
		CMN.Log(pathbuilder);
	}
	
    public static String trimFloatString(String input) {
    	//CMN.Log(input);
        int len = input.length();
        int st = 0;

        while ((st < len) && (input.charAt(len - 1) <= ' ' || input.charAt(len - 1) == '0')) {
            len--;
            if(input.charAt(len - 1) == '.') {
            	len--;
            	break;
            }
        }
        return ((st > 0) || (len < input.length())) ? input.substring(st, len) : input;
    }

}
