package com.PDI.controller.equalization;

public class ecualizacionHiperbolicaRaices implements interfaceEquealization {
    @Override
    public double[] generarLUT(int fMin, int fMax, double[] frecuencias, double... params) {
        double pot = params[0];
		double fMaxPot = Math.pow(fMax, 1 / pot);
        double fMinPot = Math.pow(fMin, 1 / pot);

        double[] lut = new double[256];
        for (int i = 0; i < 256; i++) {
			double Pi = frecuencias[i];
            lut[i] = (int) Math.pow((Pi * (fMaxPot - fMinPot) + fMinPot), pot);
		}
        return lut;
    }
}

