package com.PDI.controller.filtroNoLineal;

public class applyFilter {
	private interfaceNoLi strategy;

	public void setStrategy(interfaceNoLi strategy) {
		this.strategy = strategy;
	}

	public int[][] aplicarFiltro(int[][] imagen, int tamanoMascara) {
        return strategy.aplicarFiltro(imagen, tamanoMascara);
    }
} 
