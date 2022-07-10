package com.example.imageprocessor.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailImageType  implements ImageType{
 
	@Value("${thumbnail-image-height}")
	private Integer height;
	
	@Value("${thumbnail-image-width}")
    private Integer width;
	
	@Value("${thumbnail-image-quality}")
	private Integer quality;
	
	private Type returnImageType;
	
	private ScaleType scaleType;
	
	@Override
	public byte[] reSize(byte[] image,Type type,ScaleType scaleType) {
		// code for resizing needs to be placed here 
		return null;
	}


	@Override
	public byte[] reSize(byte[] image) {
		// code for resizing needs to be placed here 
		return null;
	}

}
