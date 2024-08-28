package com.PDI.controller.equalization;

public class ecualizacionHiperbolicaLog implements interfaceEquealization {
    @Override
    public double[] generarLUT(int fMin, int fMax, double[] frecuencias, double... params) {
        double[] lut = new double[256];
        double logFmax = Math.log(fMax);
        double logFmin = Math.log(fMin);
        for (int i = 0; i < 256; i++) {
            double Pi = frecuencias[i];
            lut[i] = fMin * Math.exp(Pi * (logFmax - logFmin));
        }
        return lut;
    }
}

