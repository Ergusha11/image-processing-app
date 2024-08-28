package com.PDI.controller.modelColor.planeExtractor;

import com.PDI.model.pixel;

public class ExtractPlanes {
	private InterfacePlane planes;

	public ExtractPlanes(InterfacePlane planes){
		this.planes = planes;
	}

	public void setPlanes(InterfacePlane planes) {
		this.planes = planes;
	}

	public int[][][] extractPlane(pixel[][] matrizPixel){
		return planes.extractPlanes(matrizPixel);
	}
}
