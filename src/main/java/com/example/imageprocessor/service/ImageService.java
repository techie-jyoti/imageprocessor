package com.example.imageprocessor.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.imageprocessor.exception.ImageDownloadException;
import com.example.imageprocessor.exception.InvalidImageTypeException;
import com.example.imageprocessor.model.DetailImageType;
import com.example.imageprocessor.model.ThumbnailImageType;
import com.example.imageprocessor.repository.S3Repo;
import com.example.imageprocessor.util.CommonConstants;

import org.slf4j.Logger;

@Service
public class ImageService {
	
	@Value("${source-root-url}")
	private String sourcePath;
	
	@Autowired
	private ThumbnailImageType thumbnailImageType;
	
	@Autowired
	private DetailImageType detailImageType;
	
	@Autowired
	private S3Repo s3Repo;

	
	Logger logger = LoggerFactory.getLogger(ImageService.class);
	
	public byte[] getOptimizedImage(String imageType,String imageName)  {
		
		if(isImageTypeValid(imageType)) {
			 if(s3Repo.isImageExists(imageName, imageType)) {
				 
				return  s3Repo.downloadImage(imageType,imageName);
			 }
			 else if(s3Repo.isImageExists(imageName, CommonConstants.IMAGE_TYPE_ORIGINAL)) {
				byte[] originalImg = s3Repo.downloadImage(CommonConstants.IMAGE_TYPE_ORIGINAL,imageName);
				
				return reSizeNStore(imageType, imageName, originalImg);
			 }
			 else {
				byte[] originalImg = downloadImage(imageName);
				s3Repo.uploadImage(new ByteArrayInputStream(originalImg), CommonConstants.IMAGE_TYPE_ORIGINAL, imageName);
				
				return reSizeNStore(imageType, imageName, originalImg); 
			 }
		}
		
		throw new InvalidImageTypeException(imageType);
	}

	public void deleteImage(String imageType, String imageName) {
		if (isImageTypeValid(imageType)) {
			s3Repo.deleteObjects(imageType, imageName);
		}
		throw new InvalidImageTypeException(imageType);
	}
	
	private byte[] reSizeNStore(String imageType, String imageName, byte[] originalImg) {
		byte[] reSizedImg = resizeImage(originalImg,imageType);
		InputStream imageStream = new ByteArrayInputStream(reSizedImg);
		s3Repo.uploadImage(imageStream, imageType, imageName);
		return reSizedImg;
	}

	private Boolean isImageTypeValid(String imageType) {

		if (imageType != null && CommonConstants.VALID_IMAGE_TYPE.contains(imageType))
			return true;

		return false;
	}
	
	public  byte[] resizeImage(byte[] originalImg,String imageType) {
		
		
		if(CommonConstants.IMAGE_TYPE_THUMBNAIL.equals(imageType)) {
			return thumbnailImageType.reSize(originalImg);	
		}else if(CommonConstants.IMAGE_TYPE_DETAIL_LARGE.equals(imageType)) {
			return detailImageType.reSize(originalImg);
		}else {
			return originalImg;
		}
		
	}
	
	
	private byte[] downloadImage(String fileName) {
		
		 try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			    
			    URL url = new URL(sourcePath+"/"+fileName);
		        URLConnection conn = url.openConnection();
		        conn.setConnectTimeout(5000);
		        conn.setReadTimeout(5000);
		        conn.connect(); 
		        
		        IOUtils.copy(conn.getInputStream(), baos);

		        return baos.toByteArray();
		    }
		    catch (IOException e)
		    {    	
		       throw new ImageDownloadException(fileName);
		    }
	}
	
	
}
