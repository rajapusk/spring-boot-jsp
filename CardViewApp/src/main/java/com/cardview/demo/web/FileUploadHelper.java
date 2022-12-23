package com.cardview.demo.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.cardview.demo.model.EmpDocEntity;
import com.cardview.demo.service.PFLoanService;

class FileUploadHelper{
	private static String rootDocPath = "C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/static/images/";
	
	public static EmpDocEntity uploadFile(MultipartFile file, String pageId, String empCode, String pageName, PFLoanService pfService) {		
		try {
			if (pageId != null) {
				if (!file.isEmpty()) {
					byte[] bytes = file.getBytes();
					String filename = file.getOriginalFilename();
					String folderPath = rootDocPath + pageName + "/";
					
					File newFile = new File(folderPath);

					System.out.println("folderPath " + folderPath);
					
					if (!newFile.exists()) {
						newFile.mkdirs();
					}

					String fullName = new Date().getTime() + "_" +  pageId + "_" + filename;
					String filePath = folderPath + fullName;

					System.out.println("filePath " + filePath);
					
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					System.out.println("File saved successfully. " + filePath);
					stream.flush();
					stream.close();
					
					EmpDocEntity entiry = new EmpDocEntity();

   					entiry.setpageId(pageId);
   					entiry.setfileName(fullName);
   					entiry.setpage(pageName);
   					entiry.setempcode(empCode);
   					
   					return pfService.updateDocs(entiry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}