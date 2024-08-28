package com.PDI.controller.equalization;

public class ecualizacionExponencial implements interfaceEquealization{
	@Override
	public double[] generarLUT(int fMin, int fMax, double[] frecuencias, double... params) {
		double alpha = params[0];
        double[] lut = new double[256];
        for (int i = 0; i < 256; i++) {
            double p = frecuencias[i];
            lut[i] = (fMin - (1.0 / alpha) * Math.log(1 - p));
        }
        return lut;
	}
}
