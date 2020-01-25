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
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import Controller.DCMConverter;
import Controller.Methods;
import net.coobird.thumbnailator.Thumbnails;

public class ConverterUI1 extends JFrame implements ActionListener

{


	private static final long serialVersionUID = 1L;
	//attributs partagable entre les classes
    public static File Visualiserf;
    static ImageIcon iclogo ;
    static BufferedImage bufflogo ;
    static Image imglogo ;
	static DicomObject dicom = new BasicDicomObject();
	public static boolean deleted = false , Dicom_Viewer = false , JustViewer = false,ispdf = false;
	ImageIcon noimage;
	File ISource;
	File dcmDestination;
	JRadioButton BMP, TIFF , PNG, JPG,GIF;
	JRadioButton  PDF;
	ButtonGroup extension;
	JPanel NPanel, WPanel, SPanel, CPanel, AreaPanel;
	JLabel LBL_Title, LBL_I,labelImageDisplay,LBL_Type,LBL_Type2,LBL_Type3,LBL_Type4;
	JButton BT_AddFile, BT_DeleteFile, BT_EditTags ,BT_SelectDest, BT_Convert ,BT_DCMConverter;
	JTextArea TXTA_File;
	JScrollPane S_S;	
	String SourcePath ,DestinationPath;
	
	
	
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
	case 1: new Visualiser("Visualiser le fichier");
			
			break ;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choix);
		}
	}
	
	 private void loadAndDisplay(BufferedImage jpegImage)
	  { 
	      ImageIcon icon  = new ImageIcon(jpegImage);
	       labelImageDisplay.setIcon(icon);
	  }
	 public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		  try {
			return Thumbnails.of(img).forceSize(newW, newH).asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  return null;
		}

		void initRadioButtons() {
			BMP= new JRadioButton("BMP extension");
			BMP.setBackground(new Color(200,200,200));
			BMP.addActionListener(this);
			
			JPG= new JRadioButton("JPG extension");
			JPG.addActionListener(this);
			JPG.setBackground(new Color(200,200,200));
			
			PNG= new JRadioButton("PNG extension");
			PNG.addActionListener(this);
			PNG.setBackground(new Color(200,200,200));
			
			TIFF= new JRadioButton("TIFF extension");
			TIFF.addActionListener(this);
			TIFF.setBackground(new Color(200,200,200));
			
			PDF= new JRadioButton("PDF extension");
			PDF.addActionListener(this);
			PDF.setBackground(new Color(200,200,200));
			
			GIF= new JRadioButton("GIF extension");
			GIF.addActionListener(this);
			GIF.setBackground(new Color(200,200,200));
			
			
		}
		
		void initButtonGroup() {
			extension= new ButtonGroup();
			extension.add(JPG);
			extension.add(PNG);
			extension.add(TIFF);
			extension.add(BMP);
			extension.add(PDF);
			extension.add(GIF);			
		}
	void initAreas() 
	{	
		TXTA_File = new JTextArea();
		TXTA_File.setBackground(Color.DARK_GRAY);
		TXTA_File.setForeground(Color.white);
		TXTA_File.setFont(new Font("arial", Font.BOLD | Font.ITALIC, 15));
		TXTA_File.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 3));
		TXTA_File.setEnabled(false);
	}
	
	void initPanels() {
		NPanel = new JPanel();
		NPanel.setBackground(new Color(56,141,191));
		NPanel.setLayout(new GridLayout(1,2));
		NPanel.setBorder(new EmptyBorder(15, 15, 5, 290));
		JLabel limg = new JLabel();
		limg.setIcon(new ImageIcon(ConverterUI1.resize(bufflogo, 50, 50)));
		NPanel.add(limg);
		NPanel.add(LBL_Title);
		
		WPanel = new JPanel();
		WPanel.setBackground(new Color(200,200,200));
		WPanel.setLayout(new GridLayout(12,1, 0,2));
		WPanel.setBorder(new EmptyBorder(20, 10, 10, 10));
		WPanel.add(BT_AddFile);
		WPanel.add(BT_DeleteFile);
		WPanel.add(BT_EditTags);
		WPanel.add(BT_DCMConverter);
		WPanel.add(LBL_Type);
		WPanel.add(LBL_Type2);
		WPanel.add(JPG);
		WPanel.add(PNG);
		WPanel.add(TIFF);
		WPanel.add(BMP);
		WPanel.add(PDF);
		WPanel.add(GIF);

		
		
		SPanel = new JPanel();
		SPanel.setBackground(Color.white);
		SPanel.setLayout(new GridLayout(1,2, 15,15));
		SPanel.setBorder(new EmptyBorder(5, 310, 5, 40));

		
		

		
		
		AreaPanel = new JPanel();
		AreaPanel.setBackground(Color.white);
		AreaPanel.setLayout(new BorderLayout());
        AreaPanel.setBorder(new EmptyBorder(0,100, 0, 0));
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
		SPanel.setBorder(new EmptyBorder(5, 160, 5, 20));
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
		
		LBL_Type = new JLabel("please select your");
		LBL_Type.setForeground(new Color(40,60,70));  // Text Color
		LBL_Type.setHorizontalAlignment(JLabel.CENTER);  
		LBL_Type.setFont(new Font("Candara", Font.BOLD, 18));
		
		LBL_Type2 = new JLabel("image type first");
		LBL_Type2.setForeground(new Color(40,60,70));  // Text Color
		LBL_Type2.setHorizontalAlignment(JLabel.CENTER);  
		LBL_Type2.setFont(new Font("Candara", Font.BOLD, 18));
		

		
	}
	
	void initButtons() {
		BT_AddFile = new JButton("Add Image");
		BT_AddFile.setForeground(new Color(40,60,70));  // Text Color
		BT_AddFile.setHorizontalAlignment(JLabel.CENTER);  
		BT_AddFile.setFont(new Font("Candara", Font.BOLD, 14));
		BT_AddFile.addActionListener(this);
		BT_AddFile.setEnabled(false);
		
		BT_DeleteFile = new JButton("Delete Image");
		BT_DeleteFile.setForeground(new Color(40,60,70));  // Text Color
		BT_DeleteFile.setHorizontalAlignment(JLabel.CENTER);  
		BT_DeleteFile.setFont(new Font("Candara", Font.BOLD, 14));	
		BT_DeleteFile.addActionListener(this);
		BT_DeleteFile.setEnabled(false);
		
		BT_EditTags = new JButton("Edit Tags");
		BT_EditTags.setForeground(new Color(40,60,70));  // Text Color
		BT_EditTags.setHorizontalAlignment(JLabel.CENTER);  
		BT_EditTags.setFont(new Font("Candara", Font.BOLD, 14));
		BT_EditTags.addActionListener(this);
		BT_EditTags.setEnabled(false);
		
		BT_DCMConverter = new JButton("DCM Converter");
		BT_DCMConverter.setForeground(new Color(40,60,70));  // Text Color
		BT_DCMConverter.setHorizontalAlignment(JLabel.CENTER);  
		BT_DCMConverter.setFont(new Font("Candara", Font.BOLD, 14));
		BT_DCMConverter.addActionListener(this);
		
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
	public ConverterUI1(String T) 
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
		ct.add(SPanel, BorderLayout.SOUTH);
		
		
		setTitle("DICOM-CONVERTER");
		setLocation(100, 100);
		setSize(1000,670);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	


@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==PDF)
	{
		ispdf=true;
		BT_AddFile.setEnabled(true);
	}
	else if(e.getSource()==JPG || e.getSource()==PNG || e.getSource()==GIF || e.getSource()==TIFF || e.getSource()==BMP)
	{
		BT_AddFile.setEnabled(true);
	}
	if(e.getSource()==BT_AddFile) {
		try{
		JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         fileChooser.showOpenDialog(this);
         File selectedFile = fileChooser.getSelectedFile();        
         BufferedImage jpegImage; 
         if(ispdf)
         {
        	 
        	 jpegImage = Methods.PdfExtract(selectedFile);
         }
         else
         {
        	 jpegImage = ImageIO.read(selectedFile);
         }
        
         jpegImage = resize(jpegImage,600,400);       
         loadAndDisplay(jpegImage);
         ISource = selectedFile;
         deleted=false;
		}
		catch(Exception e1) 
		{
			e1.printStackTrace();
		}
		if(this.ISource != null) {BT_DeleteFile.setEnabled(true);BT_EditTags.setEnabled(true);}
	}
	
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
	
	if(e.getSource()==BT_Convert) 
	{
		
	if(this.JPG.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		
		DCMConverter.convert(ISource, dicom, DestinationPath, dcmDestination);
		Succes();
	}
	else if(this.BMP.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ISource = Methods.bmpTojpg(ISource);
		DCMConverter.convert(ISource, dicom, DestinationPath, dcmDestination);
		Succes();	
	}
	else if(this.PNG.isSelected() && TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ISource = Methods.png2jpg(ISource);
		DCMConverter.convert(ISource, dicom, DestinationPath, dcmDestination);
		
	}
	else if(this.TIFF.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ISource = Methods.TIFFTojpg(ISource);
		DCMConverter.convert(ISource, dicom, DestinationPath, dcmDestination);
		Succes();
	}
	else if(this.PDF.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ISource = Methods.Pdf2jpg(ISource);
		ISource = Methods.png2jpg(ISource);
		DCMConverter.convert(ISource, dicom, DestinationPath, dcmDestination);
		Succes();
	}
	else if(this.GIF.isSelected()&& TXTA_File.getText().trim().length()!=0&&!deleted)
	{
		ISource = Methods.gif2jpg(ISource);
		DCMConverter.convert(ISource, dicom, DestinationPath, dcmDestination);
		Succes();
	}
	else {
		showMessageDialog(null, "Veillez entrer les champs obligatoires ");
		 }
		
	}	
	if(e.getSource()==BT_EditTags)
	{
		new EditTags("Dicom tags");		
	}
	if(e.getSource()==BT_DeleteFile)
	{		
		try 
		{
			ImageIcon imgic = new ImageIcon(ImageIO.read(new File("resource/no.png")));
			labelImageDisplay.setIcon(imgic);
			TXTA_File.setText("");
			deleted=true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	if(e.getSource()==BT_DCMConverter)
	{
		new DCMViewerUI2("DICOM CONVERTER");
		dispose();
	}
		
}
}

	
	
	
	