package com.fpoly.java5.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	public String saveImage(MultipartFile file){
	    
	    try {
	      Path path = Paths.get("images");
	      // Tao folder neu chua ton tai
	      Files.createDirectories(path);

	      String fileName = String.format("%s.%s", new Date().getTime(), file.getContentType().split("/")[1]);

	      // Luu file vao thu muc images
	      Files.copy(file.getInputStream(), path.resolve(fileName));

	      // Save success
	      return fileName;
	    }catch(Exception e){
	      // Save errror
	      return null;
	    }
	  }
}
