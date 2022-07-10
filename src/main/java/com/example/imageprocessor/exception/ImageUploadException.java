package com.example.imageprocessor.exception;

public class ImageUploadException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ImageUploadException(String bucketName,String fileName){
		super("Unable to upload image to bucket "+bucketName + " and fileName "+fileName);
	}

}
