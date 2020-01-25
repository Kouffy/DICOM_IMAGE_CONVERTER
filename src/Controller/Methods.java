package Controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;



public class Methods {

	
			//une methode pour convertir une image JPEG en image GIF
	        public static File gif2jpg(File file)
	        {
	        	FileOutputStream fos;
	        	BufferedImage image;
	        	File output = new File("outputs/gif"+System.currentTimeMillis()+".jpg");
				try {
					image =  ImageIO.read(file);
					fos = new FileOutputStream(output);
					 JPEGEncodeParam param = new JPEGEncodeParam();
				        param.setQuality((float) 0.90);
				        ImageEncoder encoder = ImageCodec.createImageEncoder("JPEG", fos, param);
							encoder.encode(image);
							return output;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		       return null;
	        }
	        
	      //une methode pour extraire l'image depuis un fichier PDF
	@SuppressWarnings("unchecked")
	public static BufferedImage PdfExtract(File pdf) {
		try {
			if (pdf.exists()) {
				PDDocument document = PDDocument.load(pdf);
				List<PDPage> list = document.getDocumentCatalog().getAllPages();
				String fileName = pdf.getName().replace(".pdf", "");
				PDPage page = list.get(0);
				BufferedImage image = page.convertToImage();
				File outputfile = new File("outputs/" + fileName + "_" + 1 + ".png");
				ImageIO.write(image, "png", outputfile);
				document.close();
				System.out.println("Image saved at" );
				return image;
			} else {
				System.err.println(pdf.getName() + " File does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
return null;
	}
	
	 //une methode pour extraire l'image depuis un fichier PDF
	public static File Pdf2jpg(File pdf) {
		try {
			if (pdf.exists()) {
				PDDocument document = PDDocument.load(pdf);
				@SuppressWarnings("unchecked")
				List<PDPage> list = document.getDocumentCatalog().getAllPages();
				String fileName = pdf.getName().replace(".pdf", "");
				int pageNumber = 1;
				PDPage page = list.get(0);
				BufferedImage image = page.convertToImage();
				File outputfile = new File("outputs/" + fileName + "_" + pageNumber + ".png");
				ImageIO.write(image, "png", outputfile);
				document.close();
				System.out.println("Image saved at" );
				return outputfile;
			} else {
				System.err.println(pdf.getName() + " File does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
return null;
	}
	
	//une methode utilisée par une la methode TIIFtoJPG
	public static BufferedImage convert(BufferedImage src, int bufImgType) {
	    BufferedImage img= new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
	    Graphics2D g2d= img.createGraphics();
	    g2d.drawImage(src, 0, 0, null);
	    g2d.dispose();
	    return img;
	}
	
	//une methode qui convert une image TIFF vers une image JPG
	public  static File TIFFTojpg(File tiffSource) {
	    	 File output = new File("outputs/output"+System.currentTimeMillis()+".jpg");
	    	 try
	    	    {
	    	        BufferedImage image = ImageIO.read(tiffSource);
	    	        image = convert(image, BufferedImage.TYPE_INT_RGB);
	    	        ImageIO.write(image, "jpg",output);
	    	        System.out.println("done.");
	    	        return output;
	    	    }
	    	    catch(Exception e)
	    	    {
	    	        e.printStackTrace();
	    	    }
	    	 return tiffSource;
	}

	//une methode qui convert une image BMP vers une image JPG
	public  static File bmpTojpg(File bmpSource) {
	   
	     try {
				File output = new File("outputs/output"+System.currentTimeMillis()+".jpg");
				  BufferedImage input_image = null; 
				     input_image = ImageIO.read( bmpSource);
				     ImageIO.write(input_image, "jpg", output); 
		         return output;
	        }  catch (IOException e) {
	            e.printStackTrace();
	        }
		  return bmpSource;
	}
	
	//une methode qui convert une image PNG vers une image JPG
	public static File png2jpg(File pngSource) {
		  try {
				File output = new File("outputs/output"+System.currentTimeMillis()+".jpg");;
				 BufferedImage image = ImageIO.read(pngSource);
		         BufferedImage result = new BufferedImage(
		                 image.getWidth(),
		                 image.getHeight(),
		                 BufferedImage.TYPE_INT_RGB);
		         result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		         ImageIO.write(result, "jpg", output);
		         return output;
	        }  catch (IOException e) {
	            e.printStackTrace();
	        }
		  return pngSource;
	}
	
	//une methode qui convert une image JPG vers une image BMP
	public  static File jpgTobmp(File jpgSource) {
		   
		 try{  

			  BufferedImage image = ImageIO.read(jpgSource);

			  File output = new File("outputs/output"+System.currentTimeMillis()+".jpg");
			  ImageIO.write(image, "bmp", output);
			 return  output;
			}catch(FileNotFoundException e){
			  System.out.println("Error:"+e.getMessage());
			}catch(IOException e)
			  {
			  System.out.println("Error:"+e.getMessage());
			  }
			  catch(Exception e){
			  System.out.println(e.getMessage());
			  }
			 

		  return jpgSource;
	}


}
