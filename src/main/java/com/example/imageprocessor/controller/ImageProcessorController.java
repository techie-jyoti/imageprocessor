package com.example.imageprocessor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.imageprocessor.service.ImageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageProcessorController {

	Logger logger = LoggerFactory.getLogger(ImageProcessorController.class);
	
	@Autowired
	ImageService imageService;
	
	
	@GetMapping("/show/{imageType}/{dummyName}")
	@ApiOperation(value = "Get an optimized image for specified imageType", response = ResponseEntity.class)
	public ResponseEntity<byte[]> optimizeImage(@PathVariable(name = "imageType",required = true) String imageType,
			@PathVariable(name = "dummyName",required =false) String dummyName,
			@RequestParam(name = "reference",required = true) String imageName) {
	
		logger.debug("fetching optimized image");
		byte[] image = imageService.getOptimizedImage(imageType, imageName);
		
		return new ResponseEntity<byte[]>(image,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/flush/{imageType}")
	@ApiOperation(value = "Removes  optimized/original image from S3", response = ResponseEntity.class)
	public ResponseEntity<String> flushImage(@PathVariable(name = "imageType",required = true) String imageType,
			@RequestParam(name = "reference",required = true) String imageName) { 
		
		logger.debug("Flushing images from s3 storage");
		imageService.deleteImage(imageType,imageName);
		
		return new ResponseEntity<String>("Images flushed ",HttpStatus.OK);
	}
	
	
}
