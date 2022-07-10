package com.example.imageprocessor.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DetailImageType  implements ImageType{


	@Value("${detailed-image-height}")
	private Integer height;
	
	@Value("${detailed-image-width}")
    private Integer width;
	
	@Value("${detailed-image-quality}")
	private Integer quality;
	
	private Type returnImageType;
	
	private ScaleType scaleType;
	
	
	@Override
	public byte[] reSize(byte[] image,Type type,ScaleType scaleType) {
		// code for resizing needs to be placed here 
		return image;
	}


	@Override
	public byte[] reSize(byte[] image) {
		// code for resizing needs to be placed here 
		return null;
	}

}
