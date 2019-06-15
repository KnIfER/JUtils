package ffmpeg;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class asdasd {

	
	
	
	
	
	
	
	
	
	//https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f010000bjl3a53vtvgcf5n39mmg&line=0
	//mitmdump -s dyin_flow_dumpper.py
	//String urlStr = "http://v5-dy.ixigua.com/4e213c5aaea0b05f7f8c1a155f2155e2/5cebb15c/video/m/2200e979de759224e9d9db4b123056237ca115a1f0400002b21a0c928cc/?rc=anU5ZXlka3lsZzMzZmkzM0ApQHRAb0g8PDozNzkzNDo2OTo4PDNAKXUpQGczdSlAZjV2KUBmbGRqZXpoaGRmOzRAX21zbGVtaF9oXy0tMy0wc3MtbyNvIzMwMTQ1LS8tLTExLi8tLi9pOmIwcCM6YS1xIzpgL28jbWwrYitqdDojLy5";

	
	public static void main(String[] args) throws IOException {
		String downloadpath="F:\\htmldownload\\";
		String list="D:\\flowsheet.txt";
		
    	BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(list), "UTF-8"));
    	String line;
    	int count=0;
		while((line=in.readLine())!=null) {
			int try_count=0;
			boolean succ=false;
			while(try_count<5 && !succ) {
				CMN.Log(count,"...",try_count);
				try {
					URL url = new URL(line);
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setConnectTimeout(5*1000);
					conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
					InputStream inputStream = conn.getInputStream();

					byte[] buffer = new byte[1024];
					int len = 0;
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					while((len = inputStream.read(buffer)) != -1) {
					    bos.write(buffer, 0, len);
					}
					bos.close();
					byte[] data = bos.toByteArray();
					
					File new_file;
					while((new_file=new File(downloadpath+count+".mp4")).exists())
						count++;
					FileOutputStream fos = new FileOutputStream(new_file);
					fos.write(data);
					fos.flush();
					fos.close();
					count++;
					try_count++;
					succ=true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(!succ)
				try {
					Thread.sleep(500);
				} catch (Exception e) {}
			}
		}
	}

}
