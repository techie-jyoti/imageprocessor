package com.example.imageprocessor.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.imageprocessor.exception.ImageDownloadException;
import com.example.imageprocessor.exception.ImageUploadException;
import com.example.imageprocessor.util.CommonConstants;

@Repository
public class S3Repo {

	@Value("{aws-s3-endpoint}")
	private String s3EndPoint;
	
	@Autowired
	public AmazonS3 awsS3Client;


	Logger logger = LoggerFactory.getLogger(S3Repo.class);
	
	public boolean isImageExists(String imageName, String imageType) {

		String bucketName = getBucketName(imageName, imageType);
		String keyName = imageName.replace('/', CommonConstants.REPLACE_CHARACTER);
		try {
			if (awsS3Client.doesObjectExist(bucketName, keyName))
				return true;
		} catch (Exception ex) {
			logger.error("Exception when checking optimized image in s3 " + ex.getMessage());
		}

		return false;
	}

	public void uploadImage(InputStream imageInputStream,String imageType,String fileName) {
		String bucketName = getBucketName(fileName, imageType);
		fileName = fileName.replace('/', CommonConstants.REPLACE_CHARACTER);
		byte[] contents;
		try {
			contents = IOUtils.toByteArray(imageInputStream);
		
		InputStream stream = new ByteArrayInputStream(contents);
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(contents.length);
		metadata.setContentType("image");
		awsS3Client.putObject(new PutObjectRequest(
		        bucketName, fileName, stream, metadata)
		        .withCannedAcl(CannedAccessControlList.Private));

		imageInputStream.close();
		
		} catch (Exception e) {
			logger.error("Exception when uploading file to S3 "+e.getMessage());
			throw new ImageUploadException(bucketName,fileName);
		}
	}

	public byte[] downloadImage(String imageType, String fileName) {
		String bucketName = getBucketName(fileName, imageType);
		fileName = fileName.replace('/', CommonConstants.REPLACE_CHARACTER);
		try {
			byte[] content;
			final S3Object s3Object = awsS3Client.getObject(bucketName, fileName);
			final S3ObjectInputStream stream = s3Object.getObjectContent();
			content = IOUtils.toByteArray(stream);
			s3Object.close();
			return content;
		} catch (IOException | AmazonClientException ex) {
			
			logger.error("Exception when downloading  image from S3 "+ex.getMessage());
			throw new ImageDownloadException(bucketName,fileName);
		}

	}
		
	public void deleteObjects(String imageType, String fileName) {
		String keyName =  fileName.replace('/', CommonConstants.REPLACE_CHARACTER);
		if(CommonConstants.IMAGE_TYPE_ORIGINAL.equalsIgnoreCase(imageType)) {
			// delete all 
			
			for (String imgType:CommonConstants.VALID_IMAGE_TYPE) {
				String bucketName = getBucketName(imageType,fileName);
				awsS3Client.deleteObject(bucketName, keyName);
			}
				
			
		}else {
			// delete specific
			String bucketName = getBucketName(imageType,fileName);
			awsS3Client.deleteObject(bucketName, keyName);
		}
		
		
	}
	

	private  String getBucketName(String imageType, String imageName) {
		StringBuilder bucketName = new StringBuilder();
		bucketName.append(s3EndPoint);
		Integer fileNameLength = imageName.length() - CommonConstants.IMAGE_EXTENSION_LENGTH;

		if (fileNameLength < 4) {
			bucketName.append(imageType + "/");

		} else if (fileNameLength >= 4 && fileNameLength < 8) {
			bucketName.append(imageType + "/");
			bucketName.append(imageName.substring(0, 4) + "/");

		} else {
			// length >8
			bucketName.append(imageType + "/");
			bucketName.append(imageName.substring(0, 4) + "/");
			bucketName.append(imageName.substring(4, 8) + "/");

		}

		return bucketName.toString();

	}
}
