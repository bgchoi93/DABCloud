package com.dabcloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dabcloud.configuration.*;
import com.dabcloud.fs.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplicationController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public @ResponseBody String root() {
		return "Hello world! This is DABCloud";
	}
    @RequestMapping(method = RequestMethod.GET, value = "/healthy")
    public @ResponseBody String healthCheck () {
    	log.info("Incoming request");
        return "Service Running";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
    public @ResponseBody void uploadFile (@RequestParam String path, @RequestParam MultipartFile file) throws Exception {
    	File convFile = new File(FSConfiguration.BASE_DIRECTORY.concat(path).concat(file.getOriginalFilename()));
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile); 
        fos.write(file.getBytes());
        fos.close(); 
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/createFolder")
    public @ResponseBody void createFolder (@RequestParam String path, @RequestParam String folderName) throws Exception {
    	File newFolder = new File(FSConfiguration.BASE_DIRECTORY.concat(path).concat(folderName));
    	newFolder.mkdirs();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public @ResponseBody void delete (@RequestParam String path) throws Exception {
    	File file = new File(FSConfiguration.BASE_DIRECTORY.concat(path));
    	file.delete();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/rename")
    public @ResponseBody void rename (@RequestParam String path, @RequestParam String newName) throws Exception {
    	File file = new File(FSConfiguration.BASE_DIRECTORY.concat(path));
    	File newFile = new File(FSConfiguration.BASE_DIRECTORY.concat(newName));
    	file.renameTo(newFile);
    	
    }

    @RequestMapping(method = RequestMethod.GET, value = "/browseFolder")
    public @ResponseBody String getFolder (@RequestParam String path) throws Exception {
        File[] files = new File(FSConfiguration.BASE_DIRECTORY.concat(path)).listFiles();
        List<FSElement> fsList = new ArrayList<FSElement>();
        for (File file : files) {
        	if(!file.isHidden()){
        		fsList.add(new FSElement(file));
        	}
        }
        return new Gson().toJson(fsList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/downloadFile")
    public @ResponseBody void downloadFile (@RequestParam String path) throws Exception {
    	
    }
}
