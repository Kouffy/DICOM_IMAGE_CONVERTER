package Controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import View.ConverterUI1;




public class DCMViewer {
	public static File fileToConvert ;
	 static BufferedImage myJpegImage=null;	 
	 
	 // une methode pour extraire l'image depuis le fichier dicom 
	 public static  ImageIcon getImage(File SelectedFile)
	 {
		 try {
	      SourceImage image = new SourceImage(SelectedFile.getAbsolutePath());	
	      java.awt.Image img =ConverterUI1.resize(image.getBufferedImage(), 800, 500) ;
	      ImageIcon icon  = new ImageIcon(img);
	      return icon;
	      } 
	     catch(IOException | DicomException ex)
	     {
	         System.out.println(ex.getMessage());	         
	     }
		 return null;
	 }

	 //une methode qui ecrit les tags d'un fichier dicom dans un fichier text
		public static void WriteTXT(DicomObject object,String Destiation,long cur) {
			   Iterator<DicomElement> iter = object.datasetIterator();
			   FileWriter writer =null;
			   try {
				writer = new FileWriter(Destiation+"/"+System.currentTimeMillis()+".txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			   while(iter.hasNext()) {
			      DicomElement element = (DicomElement) iter.next();
			      int tag = element.tag();
			      try {
			         String tagName = object.nameOf(tag);
			         String tagAddr = TagUtils.toString(tag);
			         String tagVR = object.vrOf(tag).toString();
			         if (tagVR.equals("SQ")) {
			            if (element.hasItems()) {
			               writer.write(tagAddr +" ["+  tagVR +"] "+ tagName+"\n");
			               WriteTXT(element.getDicomObject(),Destiation,cur);
			               continue;
			            }
			         }    
			         try {
			        	    String tagValue = object.getString(tag);    
					         writer.write(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]"+"\n");
					} catch (Exception e) {
						break;					}
			     
			      } catch (Exception e) {
			         e.printStackTrace();
			      }
			   }
			    try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		
	 //une methode qui prend un fichier dicom en entré et retourne son DicomObject en sortie
	 public static DicomObject getDiomObject(File DCMFile) {
			DicomObject object = null;  
			   try {
			      DicomInputStream dis = new DicomInputStream(DCMFile);
			      object = dis.readDicomObject();
			      dis.close();
			      return object;
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   return null;
		   }

	 //une methode qui convert un fichier dicom en un fichier pdf est retourne l'image du ficher dicom  en tant que 
	 // ImageIcon pour la visualiser
	 public static  ImageIcon convertToPDF(String desc,File file)
	 {
		 try {
	            
	            Document doc = new Document();
	            
	            
	            PdfWriter.getInstance(doc, new FileOutputStream(desc+"/"+System.currentTimeMillis()+".pdf"));
	            
	            Rectangle r = PageSize.A4;
	            
	            BufferedImage orImg = ImageIO.read(file);
	         
	            int width = orImg.getWidth();
	            int height = orImg.getHeight();
	           
	            if (width > r.getWidth())
	                width = (int) r.getWidth();
	            if (height > r.getHeight())
	                height = (int) r.getHeight();
	            
	            BufferedImage bi = new BufferedImage(width, height,
	                    BufferedImage.TYPE_INT_RGB);
	            
	            Graphics2D g2d = bi.createGraphics();
	           
	            g2d.drawImage(orImg, 0, 0, width, height, null);
	            
	            ByteArrayOutputStream bas = new ByteArrayOutputStream();
	            ImageIO.write(bi, "png", bas);
	            
	            Image img = Image.getInstance(bas.toByteArray());
	            
	            img.setAlignment(Element.ALIGN_CENTER);
	            
	            doc.open();
	            
	            doc.add(img);
	            WriteTXT(getDiomObject(file),desc,System.currentTimeMillis());

	            doc.close();
	            	          
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return getImage(file);
	 }
	 
	 //une methode qui convert un fichier dicom en une image PNG est retourne l'image du ficher dicom 
	  public static ImageIcon convertToPNG(File file,String destination) 
	  {
		  try
		  {
			  BufferedImage image = ImageIO.read(file);
			  ImageIO.write(image, "png", new File(destination+"/"+System.currentTimeMillis()+".png"));  
			  WriteTXT(getDiomObject(file),destination,System.currentTimeMillis());
			  System.out.println("Bien Convertie");
		  }catch(IOException ex)
		  {
			  System.out.println(ex.getMessage());
		  }
		  return getImage(file);
	  }
	  
	  //une methode qui convert un fichier dicom en une image GIF est La retourne en tant que 
		 // ImageIcon pour la visualiser
	  public static ImageIcon convertToGIF(File file,String destination)
	  {
		  try
		  {
			  BufferedImage image = ImageIO.read(file);
			  ImageIO.write(image, "gif", new File(destination+"/"+System.currentTimeMillis()+".gif"));
			  WriteTXT(getDiomObject(file),destination,System.currentTimeMillis());
			  System.out.println("Bien Convertie");
			  
		  }catch(IOException ex)
		  {
			  System.out.println(ex.getMessage());
		  }
		  return getImage(file);
	  }
	  
	  //une methode qui convert un fichier dicom en une image JPEG est La retourne en tant que 
		 // ImageIcon pour la visualiser
	  public static ImageIcon convertDicomToJPG(File file,String destination)
	  {
		  Iterator<ImageReader> iterator =ImageIO.getImageReadersByFormatName("DICOM");
	        while (iterator.hasNext()) {
	            ImageReader imageReader = (ImageReader) iterator.next();
	            DicomImageReadParam dicomImageReadParam = (DicomImageReadParam) imageReader.getDefaultReadParam();
	            try {
	                ImageInputStream iis = ImageIO.createImageInputStream(file);
	                imageReader.setInput(iis,false);
	                myJpegImage = imageReader.read(0, dicomImageReadParam);
	                iis.close();
	                if(myJpegImage == null){
	                    System.out.println("Could not read image!!");
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            File file2 = new File(destination+"/"+System.currentTimeMillis()+".jpg");
	            try {
	                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file2));
	                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
	                encoder.encode(myJpegImage);
	                fileToConvert = file2;
	                outputStream.close();
	                WriteTXT(getDiomObject(file),destination,System.currentTimeMillis());
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            System.out.println("Completed");
	        }
	        return getImage(file);

	  }
	  
	  
	  //une methode qui convert un fichier dicom en une image BMP est La retourne en tant que 
		 // ImageIcon pour la visualiser
	  public static ImageIcon convertDicomToBmp(File file,String destination)
	  {

		  try
		  {
			  BufferedImage image = ImageIO.read(file);
			  ImageIO.write(image, "bmp", new File(destination+"/"+System.currentTimeMillis()+".bmp"));  
			  WriteTXT(getDiomObject(file),destination,System.currentTimeMillis());
			  System.out.println("Bien Convertie");
		  }catch(IOException ex)
		  {
			  System.out.println(ex.getMessage());
		  }
		  return getImage(file);
      }
	  
	  //une methode qui convert un fichier dicom en une image TIFF est La retourne en tant que 
		 // ImageIcon pour la visualiser
	  public static ImageIcon convertDicomToTIFF(File file,String destination)
	  {

		  try
		  {
			  BufferedImage image = ImageIO.read(file);
			  ImageIO.write(image, "tif", new File(destination+"/"+System.currentTimeMillis()+".tif"));  
			  WriteTXT(getDiomObject(file),destination,System.currentTimeMillis());
			  System.out.println("Bien Convertie");
		  }catch(IOException ex)
		  {
			  System.out.println(ex.getMessage());
		  }
		  return getImage(file);
		  
      }
	  
	  
	  //une methode qui convert un fichier dicom en TEXT 
	  public static void convertDicomToTEXT(File file,String destination)
	  {
		  FileWriter writer = null;
		  try {
			  		writer = new FileWriter(destination+"/"+System.currentTimeMillis()+".txt", true);
			  	  FileReader reader = new FileReader(file);
		            int character;
		            while ((character = reader.read()) != -1) {
		            	writer.write((char) character);
		            }
		            reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		 
	  
	  }
}
