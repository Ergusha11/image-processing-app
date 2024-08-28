package com.PDI.controller.OperaImage;

public class operationImage{
	private interOperaImage operations;

	public operationImage(interOperaImage operations) {
		this.operations = operations;
	}
	
	public void setStrategy(interOperaImage strategy) {
		this.operations = strategy;
	}

	public int[][] applyOperation(int[][] image1,int[][] image2){
		return operations.operationImage(image1,image2);
	}
}
