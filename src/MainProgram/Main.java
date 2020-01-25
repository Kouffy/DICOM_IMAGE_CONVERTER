package MainProgram;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import View.ConverterUI1;
import View.Splashscreen;

public class Main {

	public static void main(String[] args) {
		BufferedImage im = null;
		ConverterUI1 instance = new ConverterUI1("Dicom");
		int i=0;
		try {
			 im = ImageIO.read(new File("resource/Splash.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Splashscreen sc = new Splashscreen();
		sc.jLabel1.setIcon(new ImageIcon(im));
		sc.setVisible(true);
		try {
			for (i = 0; i <=100; i++) {
				Thread.sleep(40);
				sc.jProgressBar1.setValue(i);
				if (i==100) {
					sc.setVisible(false);
					instance.setVisible(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
