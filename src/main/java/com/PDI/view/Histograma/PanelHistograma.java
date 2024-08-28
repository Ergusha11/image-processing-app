package com.PDI.view.Histograma;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class PanelHistograma extends JPanel{
	private final double[] datos; // Frecuencias de los datos
    private final double max;
	private Color barColor;

	public PanelHistograma(double[] datos, double max,Color barColor) {
        this.datos = datos;
        this.max = max;
		this.barColor = barColor;
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBar(g);
    }
    
    
    private void drawBar(Graphics g){
        // Eliminamos la comprobación del tamaño de datos para hacerlo más flexible
        int width = getWidth();
        int height = getHeight();
        int margin = 48;

        // Ajustamos el ancho de las barras para llenar el espacio disponible
        int barWidth = Math.max(1, (width - 2 * margin) / datos.length); // Asegura al menos 1px de ancho
        int maxHeight = height - 2 * margin;
        double yScale = maxHeight / max;

        // Llamada a dibujar los ejes
        drawAxes(g, width, height, margin, barWidth,maxHeight);
        
		// Dibujar las barras del histograma
        g.setColor(barColor);
        for (int i = 0; i < datos.length; i++) {
            int x = margin + i * barWidth;
            int barHeight = (int) (datos[i] * yScale);
            g.fillRect(x, height - margin - barHeight, barWidth, barHeight);
        }
        
    }
    
    
    private void drawAxes(Graphics g, int width, int height, int margin, int barWidth,int maxHeight) {
        g.setColor(Color.BLACK);

        // Eje Y
        g.drawLine(margin, height - margin, margin, margin);
        
        // Eje X
        g.drawLine(margin, height - margin, width - margin, height - margin);

        // Marcas en el eje X cada 50 barras
        int yMark = height - margin;
        for (int i = 0; i < datos.length; i += 51) {
            int x = margin + i * barWidth;
            g.drawLine(x, yMark, x, yMark - 5);
            String markLabel = String.valueOf(i);
            g.drawString(markLabel, x - g.getFontMetrics().stringWidth(markLabel) / 2, yMark + g.getFontMetrics().getHeight());
        }

        // Marca del final (para el último valor, que debería ser 255 si el array es de esa longitud)
        int lastX = margin + (datos.length - 1) * barWidth;
        g.drawLine(lastX, yMark, lastX, yMark - 5);
        String lastLabel = String.valueOf(datos.length - 1);
        g.drawString(lastLabel, lastX - g.getFontMetrics().stringWidth(lastLabel) / 2,
                yMark + g.getFontMetrics().getHeight());

        // Marcas en el eje Y
        int numberOfYMarks = 5; // 5 marcas en el eje Y
		for (int i = 1; i <= numberOfYMarks; i++) {
            int y = (height) - (i * (maxHeight / numberOfYMarks)) - margin;
            g.drawLine(margin - 5, y - 5, margin, y - 5); // Marca hacia afuera del eje Y
            String yLabel = String.format("%.3f", (double) (this.max * i) / numberOfYMarks);
			g.drawString(yLabel, margin - g.getFontMetrics().stringWidth(yLabel) - 10, y + (g.getFontMetrics().getHeight() / 4));

        }

	}

}
