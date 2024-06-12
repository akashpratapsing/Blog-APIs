package com.blogster.blog.services.imple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogster.blog.services.FileService;

@Service
public class FileServiceImple implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
	
		//Fetching File Name
		String name = file.getOriginalFilename();
		
		//Generating random name for file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//file Path
		String filePath = path + File.pathSeparator + fileName1;
		
        File f = new File(path);
        if(!f.exists()) {
        	f.mkdir();
        }
        
        Files.copy(file.getInputStream(),Paths.get(filePath));
        
        return fileName1;
      
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.pathSeparator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
