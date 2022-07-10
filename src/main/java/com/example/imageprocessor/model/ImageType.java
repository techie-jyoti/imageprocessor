package com.example.imageprocessor.model;

public interface ImageType {
    
	
	 public byte[] reSize(byte[] image,Type type,ScaleType scaleType);
	 public byte[] reSize(byte[] image);
	 
}
