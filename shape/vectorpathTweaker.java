package shape;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ffmpeg.CMN;


public class vectorpathTweaker {
	public static void main(String[] args) {
		//改造vector path
		float viewportHeight=24, viewportWidth=24;
		float scaler =.8f;
		float scalerY = scaler;
		float transX=0f;
		float transY=0f;
		boolean tranverse = false;
		boolean flipX = false;
		boolean keep_rel_group = true;
		boolean shrink_orgs = true;
		//String pathdata = "M20,19v-4L8,7 12,4.5 20,9V5L12,0.5 4,5v4 l12.63,7.89L12,19.5 4,15v4l8,4.5z";
		//pathdata = "M20,19v-4L8,7 12,4.5 20,9V5L12,0.5 4,5v4 L16.63,16.98 12,19.5 4,15v4L12,23.5z";
		String pathdata = "M15.2,15l-0.56,-0.56L12.4,16.67V5.8h-0.8v10.87l-2.23,-2.24L8.8,15l3.2,3.2 3.2,-3.2z\r\n" + 
				"M7.2,15l-0.56,-0.56L4.4,16.67V5.8h-0.8v10.87l-2.23,-2.24L0.8,15l3.2,3.2 3.2,-3.2z\r\n" + 
				"M23.2,15l-0.56,-0.56L20.4,16.67V5.8h-0.8v10.87l-2.23,-2.24L16.8,15l3.2,3.2 3.2,-3.2z\r\n" + 
				"";
		StringBuilder pathbuilder = new StringBuilder();
		Pattern reg = Pattern.compile("M|l|L|z|s|c|C|S|V|v|h|H| ");
		Pattern regLower = Pattern.compile("[a-z]");
		Pattern regVertical = Pattern.compile("V|v");
		Matcher m = reg.matcher(pathdata);
		int idx=0;
		String lastCommand = null;
		Float[] firstOrg=null;
		Float[] Org=null;
		float[] deltaOrg=new float[2];
		while(m.find()) {
			int now =m.start();
			if(idx!=-1 && now>idx) {
				String command = pathdata.substring(idx,idx+1);
				if(!command.equals(" "))
					lastCommand=command;
				boolean xiaoxie = regLower.matcher(lastCommand).matches();
				boolean isOrg = lastCommand.equals("M");
				boolean isfirstOrg = isOrg?firstOrg==null:false;
				if(isfirstOrg)CMN.Log("1st.org#1="+pathdata.substring(idx+1,now));
				//CMN.Log("command: "+command+" "+lastCommand+" "+xiaoxie);
				//CMN.Log(pathdata.substring(idx+1,now));
				pathbuilder.append(pathdata.substring(idx,idx+1));
				String[] arr = pathdata.substring(idx+1,now).split(",");
				if(arr.length==2) {//x-y coordinates
					float x=Float.valueOf(arr[0]);
					if(isOrg) {
						Org=new Float[2];
						Org[0]=x;
						if(isfirstOrg) {
							if(shrink_orgs) {
								transX+=viewportWidth/2+(x-viewportWidth/2)*scaler-x;
							}
							firstOrg=Org;
							deltaOrg[0]=transX;
						}else if(keep_rel_group) {
							deltaOrg[0]=scaler*(x-firstOrg[0])+firstOrg[0]-x+transX;
						}
						if(flipX) x = viewportWidth-x;
					}else if(xiaoxie){
						x=x*scaler;
						if(flipX) x = -x;
					}else {
						x=scaler*(x-Org[0])+Org[0];
						if(flipX) x = viewportWidth-x;
					}
					if(!xiaoxie)x+=deltaOrg[0];
					pathbuilder.append(trimFloatString(String.format("%.2f", x)));
					pathbuilder.append(",");
					x=Float.valueOf(arr[1]);
					if(isOrg) {
						Org[1]=x;
						if(isfirstOrg) {
							if(shrink_orgs) {
								transY+=viewportHeight/2+(x-viewportHeight/2)*scalerY-x;
							}
							deltaOrg[1]=transY;
						}else if(keep_rel_group) {
							deltaOrg[1]=scalerY*(x-firstOrg[1])+firstOrg[1]-x+transY;
						}
					}else if(xiaoxie){
						x=x*scalerY;
						if(flipX) x = -x;
					}else {
						x=scalerY*(x-Org[1])+Org[1];
					}
					if(!xiaoxie)x+=deltaOrg[1];
					pathbuilder.append(trimFloatString(String.format("%.2f", x)));
				}
				else {//singleton coordinates
					String key = pathdata.substring(idx+1,now);
					if(lastCommand!=null)
					try {
						boolean isVertical = regVertical.matcher(lastCommand).matches();
						float val = Float.valueOf(key);
						if(xiaoxie)
							val*=(isVertical?scalerY:scaler);
						else {// 处理  absolute vertical or horizontal case
							if(isVertical) {//vertical
								val=scalerY*(val-Org[1])+Org[1]+deltaOrg[1];
							}else {//horizontal
								val=scalerY*(val-Org[0])+Org[0]+deltaOrg[0];
							}
						}
						pathbuilder.append(trimFloatString(String.format("%.2f", val)));
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
