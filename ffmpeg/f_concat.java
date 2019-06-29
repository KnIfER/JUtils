package ffmpeg;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.StringEscapeUtils;

public class f_concat {
	public static Charset defchar = Charset.forName("utf8");
	public static Pattern numeric_pattern = Pattern.compile("[0-9 ]{1,}");
	

	static boolean checkMd5=true;
	static boolean delete_when__checkMd5=true;
	static boolean output_concat_list=true;
	static boolean output_kuaijianjia_proj=true;
	static boolean recursive_folder=false;
	static String path = "D:\\Downloads\\sze\\New folder\\芈盈家居生活馆\\CHIVO";
	public static void main(String[] args) {
		boolean debugggingFileLister = true;
		CMN.Log(args);
		if(debugggingFileLister) {
			args = new String[] {"1 3 5"};
		}
		for(String sI:args) {
			if(numeric_pattern.matcher(sI).matches()) {
				String[] arr = sI.split(" ");
				for(String ssI:arr) {
					ssI=ssI.replace(" ", "");
					if(ssI.length()>0)
					switch(Integer.valueOf(ssI)) {
						case 1:
							checkMd5=false;
						break;
						case 2:
							delete_when__checkMd5=false;
						break;
						case 3:
							output_concat_list=false;
						break;
						case 4:
							output_kuaijianjia_proj=false;
						break;
						case 5:
							recursive_folder=true;
						break;
					}
				}
			}else {
				File fI = new File(sI);
				if(fI.exists()) {
					if(!fI.isDirectory())
						sI=fI.getParentFile().getAbsolutePath();
					path=sI;
				}
			}
		}
		
		ArrayList<File> files = fetch_file_arr(0, new File(path), null);
		
		
		if(files.size()==0) {
			CMN.Log("no media");
			return;
		}
		
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if(!o1.getParent().equals(o2.getParent()))
					return o1.compareTo(o2);
				String n1 = o1.getName();
				String n2 = o2.getName();
				int minLen = Math.min(n1.length(), n2.length());
				int touFenQiDian=0,weiFenQiDian=0;
				for(int i=0;i<minLen;i++){
					if(n1.charAt(i)!=n2.charAt(i)) {
						break;
					}
					touFenQiDian++;
				}
				if(touFenQiDian!=0) {
					for(int i=0;i<minLen;i++){
						if(n1.charAt(n1.length()-i-1)!=n2.charAt(n2.length()-i-1)) {
							break;
						}
						weiFenQiDian++;
					}
					if(weiFenQiDian!=0) {
						//System.out.println(n1+" "+n2);
						int begin = touFenQiDian,end=weiFenQiDian;
						for(int i=touFenQiDian-1;i>=0;i--){
							if(numeric_pattern.matcher(String.valueOf(n1.charAt(i))).matches())
								touFenQiDian--;
							else break;
						}
						for(int i=weiFenQiDian-1;i>=0;i--){
							if(numeric_pattern.matcher(String.valueOf(n1.charAt(n1.length()-1-i))).matches())
								weiFenQiDian--;
							else break;
						}
						
						if(touFenQiDian>n1.length()-weiFenQiDian && touFenQiDian>n2.length()-weiFenQiDian) {
							String nn1=n1.substring(touFenQiDian,n1.length()-weiFenQiDian);
							String nn2=n2.substring(touFenQiDian,n2.length()-weiFenQiDian);
							//System.out.println(nn1+" "+nn2);
							try {
								return Integer.valueOf(nn1.replace(" ", ""))-Integer.valueOf(nn2.replace(" ", ""));
							} catch (Exception e) {}
						}
					}
				}
				return n1.compareTo(n2);
			}});
		File[] arr = files.toArray(new File[] {});

		//初次写文件
		if (output_concat_list) {/* true false */
			try {
				FileOutputStream fout = new FileOutputStream(new File(path, "concatLib.txt"));
				for(File fI:arr) {
					fout.write(("file '"+fI.getAbsolutePath()+"'\r\n").getBytes(defchar));
					//fout.write(("file ------grey------.mp4\r\n").getBytes());
					
				}
				
				fout.flush();
				fout.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		
		List<File> inputs = Arrays.asList(arr);


		List<File> inputs_ajusted = new ArrayList<>();
		if(true)/* true false */
		try {
			FileInputStream fin = new FileInputStream(new File(path, "concatLib.txt"));
			byte[] buffer = new byte[fin.available()];
			int len = fin.read(buffer);
			String data = new String(buffer,0,len,defchar);
			String[] arr2 = data.split("\r\n");
			for(int i=0;i<arr2.length;i++) {
				if(arr2[i].startsWith("file "))
					inputs_ajusted.add(new File(arr2[i].substring(6,arr2[i].length()-1)));
			}
		} catch (Exception e) {}
		
		if(inputs_ajusted.size()!=0)
			inputs=inputs_ajusted;
		
		
		String CMD_ = "ffmpeg";
		for(File fI:inputs) {
			CMD_+=" -i \""+fI.getAbsolutePath()+"\"";
		}
		CMD_+=" -filter_complex \"";
		int cc=0;
		for(int i=0;i<inputs.size();i++) {
			CMD_+="["+i+":0]["+i+":1]";
		}
		CMD_+=" concat=n="+inputs.size()+":v=1:a=1:unsafe=1 [v] [a]\" -map \"[v]\" -map \"[a]\" _!_output.mp4 -y";
		
		System.out.println(CMD_);
		CMN.Log();
		CMN.Log();
		System.out.println("ffmpeg -f concat -safe 0 -i concatLib.txt -c copy output2.mp4 -y");
		CMN.Log();
		CMN.Log();
		

		if(output_kuaijianjia_proj) {/* true false */
			String projHeader = "{\r\n" + 
					"   \"TimelineMode\": 1,\r\n" + 
					"   \"VideoStretchMode\": 6,\r\n" + 
					"   \"playlist\": [ ";
			String projTail = "],\r\n" + 
					"   \"tracklist\": [ {\r\n" + 
					"      \"fadein\": false,\r\n" + 
					"      \"fadeout\": false,\r\n" + 
					"      \"index\": 0,\r\n" + 
					"      \"mute\": false,\r\n" + 
					"      \"volume\": 100\r\n" + 
					"   }, {\r\n" + 
					"      \"fadein\": false,\r\n" + 
					"      \"fadeout\": false,\r\n" + 
					"      \"index\": 1,\r\n" + 
					"      \"mute\": false,\r\n" + 
					"      \"volume\": 100\r\n" + 
					"   }, {\r\n" + 
					"      \"fadein\": false,\r\n" + 
					"      \"fadeout\": false,\r\n" + 
					"      \"index\": 2,\r\n" + 
					"      \"mute\": false,\r\n" + 
					"      \"volume\": 100\r\n" + 
					"   }, {\r\n" + 
					"      \"fadein\": false,\r\n" + 
					"      \"fadeout\": false,\r\n" + 
					"      \"index\": 3,\r\n" + 
					"      \"mute\": false,\r\n" + 
					"      \"volume\": 100\r\n" + 
					"   } ]\r\n" + 
					"}\r\n" + 
					"";
			String clipHeader="{\r\n" + 
					"      \"clip_speed\": 1.0,\r\n" + 
					"      \"fadein\": false,\r\n" + 
					"      \"fadeout\": false,\r\n" + 
					"      \"filter_intensity\": 0.0,\r\n" + 
					"      \"filter_name\": \"\",\r\n" + 
					"      \"in\": -1, \r\n" + 
					"      \"left_transition_frames\": 0,\r\n" + 
					"      \"mute\": false,\r\n" + 
					"      \"out\": -1,\r\n" + 
					"      \"path\": \"";
			String clipTail="\",\r\n" + 
					"      \"right_transition_frames\": 0,\r\n" + 
					"      \"separated_audio\": false,\r\n" + 
					"      \"smooth_grade\": 0,\r\n" + 
					"      \"time\": 0,\r\n" + 
					"      \"transition_type\": 0,\r\n" + 
					"      \"volume\": 100,\r\n" + 
					"      \"white_grade\": 0\r\n" + 
					"   }";
			try {
				inputs = new ArrayList<>();
				FileInputStream fout = new FileInputStream(new File(path, "concatLib.txt"));
				FileOutputStream projWriter = new FileOutputStream(new File(path, "合成.qme"));
				//projWriter.write(new byte[] {(byte) 0xef,(byte) 0xbb,(byte) 0xbf});
				projWriter.write((projHeader).getBytes(defchar));
				byte[] buffer = new byte[fout.available()];
				int len = fout.read(buffer);
				String data = new String(buffer,0,len,defchar);
				String[] arr2 = data.split("\r\n");
				cc=0;
				for(int i=0;i<arr2.length;i++) {
					if(cc>0)
						projWriter.write((", ").getBytes(defchar));
					if(arr2[i].startsWith("file ")) {
						 projWriter.write((clipHeader).getBytes(defchar));
						 //projWriter.write((StringEscapeUtils.escapeJava(arr2[i].substring(6,arr2[i].length()-1))).getBytes(defualt_charset));
						 projWriter.write((arr2[i].substring(6,arr2[i].length()-1).replace("\\", "\\\\")).getBytes(defchar));
						 projWriter.write((clipTail).getBytes(defchar));
						cc++;
					}
					//if(true) break;
				}
				projWriter.write((projTail).getBytes(defchar));
				projWriter.flush();
				projWriter.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		
		
		
		
	}
	
	

	private static FileFilter mFileFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			if(pathname.isDirectory()) {
				if(recursive_folder) {
					directorys.add(pathname);
				}
			}else {
				String sufix = pathname.getName().toLowerCase();
				if(sufix.endsWith(".mp4") || sufix.endsWith(".flv")) {
					if(!checkMd5) return true;
					if(!sufix.startsWith("_!_"))
					try {
						FileInputStream fio;
						String md5 = DigestUtils.md5Hex(fio = new FileInputStream(pathname));
						fio.close();
						//System.out.println(md5);
						File previous = ckecker.get(md5);
						if(previous==null) {
							ckecker.put(md5, pathname);
							return true;
						}else {
							if(previous.length()==pathname.length()) {
								System.out.println(pathname.getAbsolutePath()+" delete result... "+(delete_when__checkMd5?String.valueOf(pathname.delete()):"not deleting"));
							}
							return false;
						}
					} catch (Exception e) {e.printStackTrace();}
				}
			}
			return false;
		}};
		
	final static HashMap<String,File> ckecker = new HashMap<>();
	static ArrayList<File> directorys;
	
	private static ArrayList<File> fetch_file_arr(int depth, File path, ArrayList<File> container) {
		if(recursive_folder) {
			if(directorys==null)
				directorys = new ArrayList<>();
			else
				directorys.clear();
		}
		
		File[] arr = path.listFiles(mFileFilter);
		
		if(arr==null)
			return null;
		
		if(recursive_folder) {
			
			if(container==null) {
				container=new ArrayList<>(Arrays.asList(arr));
			}else
				container.addAll(Arrays.asList(arr));
			
			File files[] = directorys.toArray(new File[directorys.size()]);
			for(File dI:files) {
				fetch_file_arr(++depth, dI, container);
			}
			
			return container;
		}else
			return new ArrayList<>(Arrays.asList(arr));
	}
	
}
