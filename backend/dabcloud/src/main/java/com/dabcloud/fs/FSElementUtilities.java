package com.dabcloud.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dabcloud.configuration.FSConfiguration;
import com.google.gson.Gson;

public class FSElementUtilities {
	public List<FSElement> browseDirectory(FSElement folder) {
		File[] files = new File(folder.getAbsolutePath()).listFiles();
		List<FSElement> fsList = new ArrayList<FSElement>();
        for (File file : files) {
        	if(!file.isHidden()){
        		fsList.add(new FSElement(file));
        	}
        }
        return fsList;
	}
	
}
