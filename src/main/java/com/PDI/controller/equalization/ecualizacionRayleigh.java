package com.PDI.controller.equalization;

public class ecualizacionRayleigh implements  interfaceEquealization{
	@Override
	public double[] generarLUT(int fMin, int fMax, double[] frecuencias, double... params) {
		double alpha = params[0];
        double[] lut = new double[256];
        for (int i = 0; i < 256; i++) {
            double p = frecuencias[i];
            lut[i] = fMin + Math.sqrt(2 * alpha * alpha * Math.log(1.0 / (1.0 - p)));
        }
        return lut;
	}
}
