package com.example.imageprocessor.util;

import java.util.List;

public class CommonConstants {

	
	public static final Integer BUCKET_PART1_LENGTH = 4;
	public static final Integer BUCKET_PART2_LENGTH = 4;
	public static final char REPLACE_CHARACTER = '_' ;
	public static final Integer IMAGE_EXTENSION_LENGTH = 4 ;
	public static final String IMAGE_TYPE_ORIGINAL = "original";
	public static final String IMAGE_TYPE_THUMBNAIL = "thumbnail";
	public static final String IMAGE_TYPE_DETAIL_LARGE = "detail-large";
	public static final List<String> VALID_IMAGE_TYPE = List.of(IMAGE_TYPE_THUMBNAIL,IMAGE_TYPE_DETAIL_LARGE,IMAGE_TYPE_ORIGINAL);
	
}
