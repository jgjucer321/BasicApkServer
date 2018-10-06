package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Base64;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.Icon;

public class ApkHandler extends Thread{

	private String filepath;
	
	
	public ApkHandler(String path) {
		this.filepath = path;
		
	}
	public void run() {
		handleApk();
	}
	private void handleApk() {
		
		try {
		ApkFile apkFile = new ApkFile (new File(filepath.toString()));
		ApkMeta apkMeta = apkFile.getApkMeta();
		
		String iconData = getIcon(apkFile);
		String Packagename = apkMeta.getPackageName();
		int NrOfPermissions = apkMeta.getUsesPermissions().size();
		
		ApkMetadata apkMetaObj = new ApkMetadata(Packagename, NrOfPermissions,iconData);
		System.out.println(apkMetaObj.toJson());
		
		}
		catch(IOException e) {
			System.out.println("file is no valid .apk or is corrupted. interrupting thread");
			this.interrupt();
		}
		
		

	}
	private String getIcon(ApkFile apkFile) {
	
	Icon iconFile = null;	
	String encodedData = "";
	try {
	 iconFile = apkFile.getIconFile();
	 }
	 catch(IOException e) {
		 e.printStackTrace();
	 }
	 byte[] iconData = iconFile.getData();
	 if(iconData != null) {
		  encodedData = Base64.getEncoder().encodeToString(iconData);
	 }
	 
	 return encodedData;
		    
	}
}
		    


		

