package ffmpeg;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class testfolders {

	
	
	
	
	
	
	
	
	
	//https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f010000bjl3a53vtvgcf5n39mmg&line=0
	//mitmdump -s dyin_flow_dumpper.py
	//String urlStr = "http://v5-dy.ixigua.com/4e213c5aaea0b05f7f8c1a155f2155e2/5cebb15c/video/m/2200e979de759224e9d9db4b123056237ca115a1f0400002b21a0c928cc/?rc=anU5ZXlka3lsZzMzZmkzM0ApQHRAb0g8PDozNzkzNDo2OTo4PDNAKXUpQGczdSlAZjV2KUBmbGRqZXpoaGRmOzRAX21zbGVtaF9oXy0tMy0wc3MtbyNvIzMwMTQ1LS8tLTExLi8tLi9pOmIwcCM6YS1xIzpgL28jbWwrYitqdDojLy5";

	
	public static void main(String[] args) throws IOException {
		
		CMN.Log(""+new File("D:\\Downloads\\sze\\New folder\\芈盈家居生活馆\\CHIVO\\芈盈家居生活馆-快手直播_43.mp4").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return false;
				
			}}));
	}

}
