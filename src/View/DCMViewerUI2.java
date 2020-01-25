package View;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.util.TagUtils;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;

import Controller.DCMViewer;
import net.coobird.thumbnailator.Thumbnails;

public class DCMViewerUI2 extends JFrame implements ActionListener{


	private static final long serialVersionUID = 1L;
	//attributs partagable entre les classes
    static ImageIcon iclogo , ImageView;
    static BufferedImage bufflogo ;
    static Image imglogo ;
	public static boolean deleted = false , Dicom_Viewer = false , JustViewer = false,ispdf = false;
	ImageIcon noimage;
	File ISource;
	JRadioButton PNG, JPG,GIF,PDF,BMP,TIFF,TXT;
	ButtonGroup extension;
	JPanel NPanel, WPanel, SPanel, CPanel, AreaPanel,EPanel;
	JLabel LBL_Title, LBL_I,labelImageDisplay,LBL_Type,LBL_tags;
	JButton BT_AddFile, BT_DeleteFile, BT_SelectDest, BT_Convert;
	JTextArea TXTA_File ,TXTA_tags;
	JScrollPane Scroll;	
	String SourcePath ,DestinationPath;
	
	
	//initialiser le logo de l'application pour tous ses utilisaions
	public void initLOGOS()
	{
		try {
			iclogo = new ImageIcon( ImageIO.read(new File("resource/dicom converter.png")));
			imglogo = ImageIO.read(new File("resource/dicom converter.png"));
			bufflogo = ImageIO.read(new File("resource/dicom converter.png"));
			noimage = new ImageIcon(ImageIO.read(new File("resource/no.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	//message box de succès de convertion 
	private void Succes()
	{
		Object[] options = {"Ok",
                "Visualiser",
                };
		int choix =JOptionPane.showOptionDialog(null,
				"Image convertie avec succès", 
				"Resultat", JOptionPane.CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, new ImageIcon(ConverterUI1.resize(bufflogo, 30, 30)),  options,options[1]);
		switch (choix) {
		case 0: 
			
			break ;
	case 1: new ImageView("Visualiser le fichier");
			
			break ;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choix);
		}
	}
	
	//afficher l'image ajoutée au sein de l'interface
	 private void loadAndDisplay(File selectedFile)
	  { 
		 try
	      {
	       SourceImage image = new SourceImage(selectedFile.getAbsolutePath());
	       BufferedImage bfimage = image.getBufferedImage(); 
	       bfimage = resize(bfimage,600,400);    
	       ImageIcon icon  = new ImageIcon(bfimage);
	       labelImageDisplay.setIcon(icon); 
	      } catch(IOException | DicomException ex)
	      {
	          System.out.println(ex.getMessage());
	          
	      }
	      	      
	  }
	 
	 //une function pour redimontionner les images (on est besoin d'elle pour adapter les images a notre interface)
	 public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		  try {
			return Thumbnails.of(img).forceSize(newW, newH).asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  return null;
		}

		void initRadioButtons() {
			
			JPG= new JRadioButton("JPG extension");
			JPG.addActionListener(this);
			JPG.setBackground(new Color(200,200,200));
			
			PNG= new JRadioButton("PNG extension");
			PNG.addActionListener(this);
			PNG.setBackground(new Color(200,200,200));
			
			
			PDF= new JRadioButton("PDF extension");
			PDF.addActionListener(this);
			PDF.setBackground(new Color(200,200,200));
			
			GIF= new JRadioButton("GIF extension");
			GIF.addActionListener(this);
			GIF.setBackground(new Color(200,200,200));
			
			BMP= new JRadioButton("BMP extension");
			BMP.addActionListener(this);
			BMP.setBackground(new Color(200,200,200));
			
			TIFF= new JRadioButton("TIFF extension");
			TIFF.addActionListener(this);
			TIFF.setBackground(new Color(200,200,200));
			
			TXT= new JRadioButton("TXT extension");
			TXT.addActionListener(this);
			TXT.setBackground(new Color(200,200,200));
		}
		
		void initButtonGroup() {
			extension= new ButtonGroup();
			extension.add(JPG);
			extension.add(PNG);
			extension.add(PDF);
			extension.add(GIF);		
			extension.add(BMP);	
			extension.add(TIFF);	
			extension.add(TXT);
		}
	void initAreas() 
	{	
		TXTA_File = new JTextArea();
		TXTA_File.setBackground(Color.DARK_GRAY);
		TXTA_File.setForeground(Color.white);
		TXTA_File.setFont(new Font("arial", Font.BOLD | Font.ITALIC, 15));
		TXTA_File.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 3));
		TXTA_File.setEnabled(false);
		
		Scroll = new JScrollPane();
		TXTA_tags = new JTextArea();
		TXTA_tags.setBackground(Color.DARK_GRAY);
		TXTA_tags.setForeground(Color.white);
		TXTA_tags.setFont(new Font("arial", Font.BOLD | Font.ITALIC, 15));
		TXTA_tags.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 3));
		TXTA_tags.setEnabled(false);
		Scroll.getViewport().add(TXTA_tags);
	}
	
	void initPanels() {
		NPanel = new JPanel();
		NPanel.setBackground(new Color(56,141,191));
		NPanel.setLayout(new GridLayout(1,2));
		NPanel.setBorder(new EmptyBorder(15, 15, 5, 500));
		JLabel limg = new JLabel();
		limg.setIcon(new ImageIcon(ConverterUI1.resize(bufflogo, 50, 50)));
		NPanel.add(limg);
		NPanel.add(LBL_Title);
		
		WPanel = new JPanel();
		WPanel.setBackground(new Color(200,200,200));
		WPanel.setLayout(new GridLayout(10,1, 0,2));
		WPanel.setBorder(new EmptyBorder(20, 10, 100, 10));
		WPanel.add(BT_AddFile);
		WPanel.add(BT_DeleteFile);
		WPanel.add(LBL_Type);
		WPanel.add(JPG);
		WPanel.add(PNG);
		WPanel.add(PDF);
		WPanel.add(GIF);
		WPanel.add(BMP);
		WPanel.add(TIFF);
		WPanel.add(TXT);
		
		
		SPanel = new JPanel();
		SPanel.setBackground(Color.white);
		SPanel.setLayout(new GridLayout(1,2, 15,15));
		SPanel.setBorder(new EmptyBorder(5, 310, 5, 40));

		
		EPanel = new JPanel();
		EPanel.setBackground(new Color(200,200,200));
		EPanel.setLayout(new BorderLayout());
		EPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
		EPanel.add(Scroll, BorderLayout.CENTER);
		EPanel.add(LBL_tags, BorderLayout.NORTH);
		
		
		AreaPanel = new JPanel();
		AreaPanel.setBackground(Color.white);
		AreaPanel.setLayout(new BorderLayout());
        AreaPanel.setBorder(new EmptyBorder(0,60, 0, 0));
		labelImageDisplay=new JLabel();		
		labelImageDisplay.setIcon(noimage);
		AreaPanel.add(labelImageDisplay,BorderLayout.CENTER);
		
		

		CPanel = new JPanel();
		CPanel.setBackground(Color.white);
		CPanel.setLayout(new BorderLayout());
		CPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
		CPanel.add(AreaPanel, BorderLayout.CENTER);
		CPanel.add(BT_Convert, BorderLayout.SOUTH);
		
		
		SPanel = new JPanel();
		SPanel.setBackground(new Color(200,200,200));
		SPanel.setLayout(new BorderLayout());
		SPanel.setBorder(new EmptyBorder(5, 220, 5, 20));
		SPanel.add(BT_SelectDest, BorderLayout.EAST);
		SPanel.add(TXTA_File, BorderLayout.CENTER);
	}
	
	void initLabels() {
		LBL_Title = new JLabel("DICOM CONVERTER");
		LBL_Title.setForeground(Color.white);  // Text Color
		LBL_Title.setHorizontalAlignment(JLabel.CENTER);  
		LBL_Title.setFont(new Font("Candara", Font.BOLD, 30));
		
		LBL_I = new JLabel("Image");
		LBL_I.setForeground(new Color(90,90,90));  // Text Color
		LBL_I.setHorizontalAlignment(JLabel.CENTER);  
		LBL_I.setFont(new Font("Candara", Font.BOLD, 18));
		
		LBL_Type = new JLabel("please select conversion type");
		LBL_Type.setForeground(new Color(40,60,70));  // Text Color
		LBL_Type.setHorizontalAlignment(JLabel.CENTER);  
		LBL_Type.setFont(new Font("Candara", Font.BOLD, 16));
		
		
		LBL_tags = new JLabel("                                                                                  DICOM TAGS                                                                           ");
		LBL_tags.setForeground(new Color(40,60,70));  // Text Color
		LBL_tags.setHorizontalAlignment(JLabel.CENTER);  
		LBL_tags.setFont(new Font("Candara", Font.BOLD, 16));
		
		

		
		
	}
	
	void initButtons() {
		BT_AddFile = new JButton("Add DICOM File");
		BT_AddFile.setForeground(new Color(40,60,70));  // Text Color
		BT_AddFile.setHorizontalAlignment(JLabel.CENTER);  
		BT_AddFile.setFont(new Font("Candara", Font.BOLD, 14));
		BT_AddFile.addActionListener(this);

		
		BT_DeleteFile = new JButton("Delete Image");
		BT_DeleteFile.setForeground(new Color(40,60,70));  // Text Color
		BT_DeleteFile.setHorizontalAlignment(JLabel.CENTER);  
		BT_DeleteFile.setFont(new Font("Candara", Font.BOLD, 14));	
		BT_DeleteFile.addActionListener(this);
		BT_DeleteFile.setEnabled(false);
		
		
		BT_SelectDest = new JButton("Select Destination");
		BT_SelectDest.setForeground(new Color(40,60,70));  // Text Color
		BT_SelectDest.setHorizontalAlignment(JLabel.CENTER);  
		BT_SelectDest.setFont(new Font("Candara", Font.BOLD, 18));
		BT_SelectDest.addActionListener(this);
		
		BT_Convert = new JButton("Convert");
		BT_Convert.setForeground(new Color(40,60,70));  // Text Color
		BT_Convert.setHorizontalAlignment(JLabel.CENTER);  
		BT_Convert.setFont(new Font("Candara", Font.BOLD, 18));
		BT_Convert.addActionListener(this);
		
	}
	public DCMViewerUI2(String T) 
	{
		initLOGOS();
		this.setIconImage(imglogo);
		initAreas();
		initButtons();
		initRadioButtons();
		initButtonGroup();
		initLabels();
		initPanels();
		
		Container ct = getContentPane();
		setBackground(new Color(255,200,90));
		ct.setBackground(new Color(90,90,90));
		ct.setLayout(new BorderLayout());
		ct.add(NPanel , BorderLayout.NORTH);
		ct.add(CPanel, BorderLayout.CENTER);
		ct.add(WPanel, BorderLayout.WEST);
		ct.add(EPanel, BorderLayout.EAST);
		ct.add(SPanel, BorderLayout.SOUTH);
		
		
		setTitle("DICOM-CONVERTER");
		setLocation(100, 100);
		setSize(1550,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	
	//une fonction qui lister les tags du ficher dicom importé 
	public  void listHeader(DicomObject object) {
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
		               TXTA_tags.append(tagAddr +" ["+  tagVR +"] "+ tagName+"\n");
		               listHeader(element.getDicomObject());
		               continue;
		            }
		         }    try {
		        	 String tagValue = object.getString(tag);    
			         TXTA_tags.append(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]"+"\n");
				} catch (Exception e) {
					break;
				}
		        
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }  
		}
     
@Override
public void actionPerformed(ActionEvent e) {
	//ajouter un fichier dicom
	if(e.getSource()==BT_AddFile) {
		try{
		JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         fileChooser.showOpenDialog(this);
         File selectedFile = fileChooser.getSelectedFile();                      
         loadAndDisplay(selectedFile);
         ISource = selectedFile;
         deleted=false;
         listHeader(DCMViewer.getDiomObject(selectedFile));
		}
		catch(Exception e1) 
		{
			e1.printStackTrace();
		}
		if(this.ISource != null) {BT_DeleteFile.setEnabled(true);BT_AddFile.setEnabled(true);}
	}
	//selectionner la destination
	if(e.getSource()==BT_SelectDest) {
		try
		{
				JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            fileChooser.showOpenDialog(this);
	            DestinationPath = fileChooser.getSelectedFile().getAbsolutePath();    
	            TXTA_File.setText(DestinationPath);

		}
		catch(Exception e2) 
		{
			e2.printStackTrace();
		}
		
	}
	// convertir le fichier dicom
	if(e.getSource()==BT_Convert) 
	{
		
	if(this.JPG.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ImageView = DCMViewer.convertDicomToJPG(ISource,DestinationPath);
		Succes();
	}
	
	else if(this.PNG.isSelected() && TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		DCMViewer.convertToPNG(ISource,DestinationPath);
		Succes();
	}
	
	else if(this.PDF.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ImageView = DCMViewer.convertToPDF(DestinationPath, ISource);
		Succes();
	}
	else if(this.GIF.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ImageView = DCMViewer.convertToGIF(ISource,DestinationPath);
		Succes();
	}
	else if(this.BMP.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ImageView = DCMViewer.convertDicomToBmp(ISource,DestinationPath);
		Succes();
	}
	else if(this.TIFF.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ImageView = DCMViewer.convertDicomToTIFF(ISource,DestinationPath);
		Succes();
	}
	else if(this.TXT.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		DCMViewer.convertDicomToTEXT(ISource,DestinationPath);
		showMessageDialog(null, "Convertion reuissite consulter le fichier dans : "+DestinationPath);
	}
	else {
		showMessageDialog(null, "Veillez entrer les champs obligatoires ");
		 }
		
	}	
	//supprimer lr fichier selectionné
	if(e.getSource()==BT_DeleteFile)
	{		
		try 
		{
			ImageIcon imgic = new ImageIcon(ImageIO.read(new File("resource/no.png")));
			labelImageDisplay.setIcon(imgic);
			TXTA_File.setText("");
			TXTA_tags.setText("");
			deleted=true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

		
}
}
