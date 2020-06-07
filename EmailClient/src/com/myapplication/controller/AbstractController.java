package com.myapplication.controller;

public class AbstractController {

	private ModelAccess modelAccess;

	public AbstractController(ModelAccess modelAccess) {
		this.modelAccess = modelAccess;
	}
	
	public ModelAccess getModelAccess() {
		return modelAccess;
	}


	
}
