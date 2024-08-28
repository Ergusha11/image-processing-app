package com.PDI.controller.equalization;

public class ecualizacionUniforme implements interfaceEquealization{
	@Override
	public double[] generarLUT(int fMin, int fMax, double[] frecuencias, double... params) {
		double[] lut = new double[256];
        for (int i = 0; i < 256; i++) {
            lut[i] = fMin + (fMax - fMin) * frecuencias[i];
        }
        return lut;
	}
}
