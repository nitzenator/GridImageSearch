package com.example.gridimagesearch.models;

import java.io.Serializable;

public class Settings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6464832919867521783L;
	private String imageSize = "small";
	private String colorFilter = "green";
	private String imageType = "face";
	private String siteFilter = "yahoo.com";
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getColorFilter() {
		return colorFilter;
	}
	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getSiteFilter() {
		return siteFilter;
	}
	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	
}
