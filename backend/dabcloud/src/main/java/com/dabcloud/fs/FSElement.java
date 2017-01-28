package com.dabcloud.fs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dabcloud.configuration.FSConfiguration;

public class FSElement {
	private String name;
	private boolean isDirectory;
	private boolean isHidden;
	private String size;
	private String lastModified;
	private String absolutePath;
	private String parentPath;
	
	private static String BYTE = "B";
	private static String KILOBYTE = "KB";
	private static String MEGABYTE = "MB";
	private static String GIGABYTE = "GB";
	
	public FSElement(File file) {
		this.name = file.getName();
		this.isDirectory = file.isDirectory();
		this.isHidden = file.isHidden();
		this.size = computeSize(file.length());
		this.lastModified = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date(file.lastModified()));
		this.absolutePath = file.getAbsolutePath().substring(FSConfiguration.BASE_DIRECTORY.length());
		this.parentPath = file.getParent().concat("/").substring(FSConfiguration.BASE_DIRECTORY.length()).concat("/");
	}
	private static String computeSize(long size) {
		return Long.toString(size).concat(BYTE);
	}
	
	public String getAbsolutePath() {
		return this.absolutePath;
	}
	public String getParentPath() {
		return this.parentPath;
	}
}
