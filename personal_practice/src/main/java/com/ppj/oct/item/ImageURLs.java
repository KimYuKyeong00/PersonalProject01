package com.ppj.oct.item;

import java.util.ArrayList;

public class ImageURLs {
	
	private ArrayList<ImageURL> ImageURLs;
	private ArrayList<String> fileList;
	
	public ImageURLs() {
	}

	public ImageURLs(ArrayList<ImageURL> imageURLs, ArrayList<String> fileList) {
		super();
		ImageURLs = imageURLs;
		this.fileList = fileList;
	}

	public ArrayList<ImageURL> getImageURLs() {
		return ImageURLs;
	}

	public void setImageURLs(ArrayList<ImageURL> imageURLs) {
		ImageURLs = imageURLs;
	}

	public ArrayList<String> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<String> fileList) {
		this.fileList = fileList;
	}



	
	
	
	
}
