package com.example.imageprocessor.exception;

public class ImageDownloadException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ImageDownloadException(String bucketName,String fileName){
		super("Unable to download image from bucket "+bucketName + " and fileName "+fileName);
	}

	public ImageDownloadException(String fileName){
		super("Unable to download "+fileName +" from source ");
	}
}

