package Controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.util.TagUtils;
import org.dcm4che2.util.UIDUtils;

import View.ConverterUI1;

public class DCMConverter 
{
	//autre definition pour la fonction qui liste les tags d'un fichier dicom 
	public static void listHeader(DicomObject object,JTextArea TEXTAREA) {
		   Iterator<DicomElement> iter = object.datasetIterator();
		   while(iter.hasNext()) {
		      DicomElement element = (DicomElement) iter.next();
		      int tag = element.tag();
		      try {
		         String tagName = object.nameOf(tag);
		         String tagAddr = TagUtils.toString(tag);
		         String tagVR = object.vrOf(tag).toString();
		         if (tagVR.equals("SQ")) {
		            if (element.hasItems()) {
		               TEXTAREA.append(tagAddr +" ["+  tagVR +"] "+ tagName+"\n");
		               listHeader(element.getDicomObject(),TEXTAREA);
		               continue;
		            }
		         }    
		         String tagValue = object.getString(tag);    
		         TEXTAREA.append(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]"+"\n");
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }  
		}
			
			
	//La methode qui convert une image vers un fichier dicom
public static void convert(File ISource,DicomObject dicom,String DestinationPath,File dcmDestination) {
		try { 
			   dcmDestination = new File(DestinationPath+"\\"+System.currentTimeMillis()+".dcm");
			   BufferedImage jpegImage = ImageIO.read(ISource);
			   if (jpegImage == null)
			      throw new Exception("Invalid file.");  
			   int colorComponents = jpegImage.getColorModel().getNumColorComponents();
			   int bitsPerPixel = jpegImage.getColorModel().getPixelSize();
			   int bitsAllocated = (bitsPerPixel / colorComponents);
			   int samplesPerPixel = colorComponents;

			
			   
			   dicom.putString(Tag.SpecificCharacterSet, VR.CS, "ISO_IR 100");
			   dicom.putString(Tag.PhotometricInterpretation, VR.CS, samplesPerPixel == 3 ? "YBR_FULL_422" : "MONOCHROME2");
			   
			 
			   dicom.putInt(Tag.SamplesPerPixel, VR.US, samplesPerPixel);         
			   dicom.putInt(Tag.Rows, VR.US, jpegImage.getHeight());
			   dicom.putInt(Tag.Columns, VR.US, jpegImage.getWidth());
			   dicom.putInt(Tag.BitsAllocated, VR.US, bitsAllocated);
			   dicom.putInt(Tag.BitsStored, VR.US, bitsAllocated);
			   dicom.putInt(Tag.HighBit, VR.US, bitsAllocated-1);
			   dicom.putInt(Tag.PixelRepresentation, VR.US, 0);  
			   
			   
			   dicom.putDate(Tag.InstanceCreationDate, VR.DA, new Date());
			   dicom.putDate(Tag.InstanceCreationTime, VR.TM, new Date());
			   
			   dicom.putString(Tag.StudyInstanceUID, VR.UI, UIDUtils.createUID());
			   dicom.putString(Tag.SeriesInstanceUID, VR.UI, UIDUtils.createUID());
			   dicom.putString(Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID());
			   
			   
			   
			   dicom.initFileMetaInformation(UID.JPEGBaseline1);
			   
			   FileOutputStream fos = new FileOutputStream(dcmDestination);
			   BufferedOutputStream bos = new BufferedOutputStream(fos);
			   DicomOutputStream dos = new DicomOutputStream(bos);
			   dos.writeDicomFile(dicom);
			   
			   
			   dos.writeHeader(Tag.PixelData, VR.OB, -1);  
			   

			   dos.writeHeader(Tag.Item, null, 0);  
			   
			   
			
			   int jpgLen = (int) ISource.length(); 
			   dos.writeHeader(Tag.Item, null, (jpgLen+1)&~1);
			   
			   
			   FileInputStream fits = new FileInputStream(ISource);
			   BufferedInputStream bis = new BufferedInputStream(fits);
			   DataInputStream dis = new DataInputStream(bis);

			   byte[] buffer = new byte[65536];       
			   int b;
			   while ((b = dis.read(buffer)) > 0) {
			      dos.write(buffer, 0, b);
			   }
			   		
			   if ((jpgLen&1) != 0) dos.write(0); 
			   dos.writeHeader(Tag.SequenceDelimitationItem, null, 0);
			   dos.close();
			  dis.close();
			  ConverterUI1.Visualiserf = dcmDestination;
			} catch(Exception e) {
			   System.out.println("ERROR: "+ e.getMessage());
			}
	}

}
