package com.PDI.view;

import javax.swing.JFrame;

import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.model.referenciaImagen;
import com.PDI.view.menu.actionMenu;
import com.PDI.view.menu.settingMenu;

import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.JInternalFrame;
import java.awt.Color;

public class FrameMain extends JFrame{
    private JDesktopPane desktopPane;
	private FrameImagen frameActivo;
	private String path;

    public FrameMain(){
		//Dimension dimension = getToolkit().getScreenSize();
        setSize(1200,900);
        setLocationRelativeTo(this);
        setTitle("Procesamiento de Imagenes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Inicializar el JDesktopPane
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.DARK_GRAY);
        add(desktopPane);
        
        //Llamada de las funciones
        //initComponents();
        configuracionMenu();
        setVisible(true);
		this.path = null;
    }

    private void configuracionMenu(){
		actionMenu action = new actionMenu(this);
		JMenuBar menuBar = new JMenuBar();
	
		settingMenu menuBuilder = new settingMenu(action);
		menuBuilder.buildMenu(menuBar);

		setJMenuBar(menuBar);
    } 
	
	/*
	 * Metodo para agregar los frame al desktop
	*/
	public void addInternalFrame(JInternalFrame internalFrame) {
		int[] pos = FuntionStatic.position();
		internalFrame.setLocation(pos[0], pos[1]);

        this.desktopPane.add(internalFrame);
        internalFrame.setVisible(true);
    }
	
	//Cambiar esto y agregarlo a la clase del FrameImage
	public void seleccionarFrameImagen(FrameImagen frame) {
        frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                frameActivo = frame; // Actualiza la referencia al frame activo
				String modelColor = frameActivo.getModelColor();
				//System.out.println("Esta activo: " + modelColor);
            }

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				referenciaImagen refImg = referenciaImagen.getInstance();
				greyImagesReferences refGrey = greyImagesReferences.getInstance();
				int id = frame.getId();
				String modelColor = frame.getModelColor();
				System.out.println(modelColor);
				if (modelColor.equals("Gris")){
					refGrey.removeImage(id);
					System.out.println("Era gris");
				}else{
					refImg.removeImage(id);
				}
				frameActivo = null;
				System.out.println("Me cerre");
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent e){
				System.out.println("Estos desactivado");
			}

        });
    }

	public FrameImagen getFrameActivo() {
		return frameActivo;
	}

	public void setFrameActivo(FrameImagen frameActivo) {
		this.frameActivo = frameActivo;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
