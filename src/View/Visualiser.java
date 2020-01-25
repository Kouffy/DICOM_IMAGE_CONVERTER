package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dcm4che2.data.DicomObject;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;

import Controller.DCMConverter;

public class Visualiser extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JLabel labelImageDisplay, LBL_TITLE1 ,LBL_Tags;
	JPanel WPanel, CPanel, NPanel, SPanel;
	JButton BT_Cancel ;
	JTextArea TEXTAREA;
	JTextField SELECTFLD;
	JScrollPane scroll;
	
	void initButtons() {

		BT_Cancel = new JButton("CLOSE");
		BT_Cancel.setForeground(new Color(40,60,70));
		BT_Cancel.setFont(new Font("Candara", Font.BOLD, 18));
		BT_Cancel.addActionListener(this);
		
	}
	
	void initTextAreas() {
		scroll = new JScrollPane();
		TEXTAREA= new JTextArea();
		TEXTAREA.setBackground(Color.DARK_GRAY);
		TEXTAREA.setForeground(Color.white);
		TEXTAREA.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		TEXTAREA.setEnabled(false);
		scroll.getViewport().add(TEXTAREA);
	}
	
	
	
	void initLabels() {
		LBL_TITLE1=new JLabel("DICOM VIEWER");
		LBL_TITLE1.setForeground(Color.WHITE);
		LBL_TITLE1.setFont(new Font("Candara", Font.BOLD, 30));
		LBL_TITLE1.setHorizontalAlignment(JLabel.CENTER);
		
		
		LBL_Tags=new JLabel("                                                    TAGS                                                    ");
		LBL_Tags.setForeground(Color.WHITE);
		LBL_Tags.setFont(new Font("arial", Font.BOLD, 15));
		LBL_Tags.setHorizontalAlignment(JLabel.CENTER);
		
		
		labelImageDisplay= new JLabel();
	}
	void initPanels() {
		
		initLabels();
		NPanel = new JPanel();
		NPanel.setLayout(new GridLayout(1, 1));
		NPanel.setBackground(new Color(56,141,191));
		NPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		NPanel.add(LBL_TITLE1);

		initButtons();

		SPanel = new JPanel();
		SPanel.setLayout(new GridLayout(1, 2, 8, 8));
		SPanel.setBackground(new Color(200,200,200));
		SPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		SPanel.add(BT_Cancel);
		


		initTextAreas();
		CPanel = new JPanel();
		CPanel.setLayout(new BorderLayout());
		CPanel.setBackground(Color.gray);
		CPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		CPanel.add(labelImageDisplay, BorderLayout.CENTER);
		
		
		
		WPanel = new JPanel();
		WPanel.setSize(130, 400);
		WPanel.setLayout(new BorderLayout());
		WPanel.setBackground(Color.darkGray);
		WPanel.setBorder(new EmptyBorder(10,10,10,10));
		WPanel.add(LBL_Tags,BorderLayout.NORTH);
		WPanel.add(scroll,BorderLayout.CENTER);
		
		
	}

	

	

	public Visualiser(String T)  {
		try {
			this.setIconImage(ImageIO.read(new File("resource/dicom converter.png")));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		initPanels();
			DicomObject H = ConverterUI1.dicom;
			  DCMConverter.listHeader(H,TEXTAREA);			
		Container cp = getContentPane();
		cp.setBackground(new Color(90, 80, 90));
		cp.setLayout(new BorderLayout());
		cp.add(NPanel, BorderLayout.NORTH);
		cp.add(CPanel, BorderLayout.CENTER);
		cp.add(SPanel, BorderLayout.SOUTH);
		cp.add(WPanel, BorderLayout.WEST);

		setBackground(new Color(90, 80, 90));
		setSize(1100, 700);
		setTitle(T);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(false);
		setVisible(true);
		 try
	     
			{
			 File SelectedFile =ConverterUI1.Visualiserf;
		      SourceImage image = new SourceImage(SelectedFile.getAbsolutePath());	
		      Image img =  ConverterUI1.resize(image.getBufferedImage(), 500, 300);
		      ImageIcon icon  = new ImageIcon(img);
		      labelImageDisplay.setIcon(icon); 
		     } catch(IOException | DicomException ex)
		     {
		         System.out.println(ex.getMessage());	         
		     }
	}
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==BT_Cancel) {
			dispose();
		}
		
	}

}
