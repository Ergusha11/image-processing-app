package com.PDI.controller.Convolucion;

public class applyFilter {
	private interfaceFiltro strategy;

	public applyFilter(interfaceFiltro strategy){
		this.strategy = strategy;
	}

	public void setStrategy(interfaceFiltro strategy) {
		this.strategy = strategy;
	}

	public int[][] filter(int[][] image,double[][] mask){
		return strategy.applyFilter(image,mask);
	}
}
