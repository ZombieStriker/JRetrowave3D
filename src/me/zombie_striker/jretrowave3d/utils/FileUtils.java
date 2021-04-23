package me.zombie_striker.jretrowave3d.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

	private FileUtils(){

	}

	public static String loadAsString(String path)  {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = FileUtils.class.getResourceAsStream(path);
			if(is==null){
				System.out.println("Failed to find "+path);
			}
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s+"\n");
			}
			br.close();
		}catch(Exception e3){
			e3.printStackTrace();
		}
		return sb.toString();
	}
}
