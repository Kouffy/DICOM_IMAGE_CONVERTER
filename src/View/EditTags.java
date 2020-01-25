package View;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class EditTags extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	JPanel NPanel, CPanel,SPanel;
	JLabel LBL_Title,LBL1,LBL2,LBL3,LBL4,LBL5,LBL6,LBL7,LBL8,LBL9,LBL10,LBL11,LBL12,LBL13;
	JButton BT_Confirm, BT_Cancel;
	JTextField TXT1, TXT2,TXT3,TXT4,TXT5,TXT6,TXT7,TXT8,TXT9,TXT10 , TXT11 ,TXT12, TXT13;
	
	
	void initTextFields() {

		TXT1 = new JTextField(5);
		TXT1.putClientProperty("emptyTextHint", "Patient ID");
		TXT1.setForeground(new Color(90, 80, 200));
		TXT1.setFont(new Font("arial", Font.BOLD, 13));
		TXT1.setHorizontalAlignment(JTextField.CENTER);

		TXT2 = new JTextField(5);
		TXT2.putClientProperty("emptyTextHint", "Patient Name");
		TXT2.setForeground(new Color(90, 80, 200));
		TXT2.setFont(new Font("arial", Font.BOLD, 13));
		TXT2.setHorizontalAlignment(JTextField.CENTER);
		
		TXT3 = new JTextField(5);
		TXT3.putClientProperty("emptyTextHint", "Patient Birth Date");
		TXT3.setForeground(new Color(90, 80, 200));
		TXT3.setFont(new Font("arial", Font.BOLD, 13));
		TXT3.setHorizontalAlignment(JTextField.CENTER);
		
		TXT4 = new JTextField(5);
		TXT4.putClientProperty("emptyTextHint", "Patient Sex");
		TXT4.setForeground(new Color(90, 80, 200));
		TXT4.setFont(new Font("arial", Font.BOLD, 13));
		TXT4.setHorizontalAlignment(JTextField.CENTER);
		
		TXT5 = new JTextField(5);
		TXT5.putClientProperty("emptyTextHint", "Patient Age");
		TXT5.setForeground(new Color(90, 80, 200));
		TXT5.setFont(new Font("arial", Font.BOLD, 13));
		TXT5.setHorizontalAlignment(JTextField.CENTER);
		
		TXT6 = new JTextField(5);
		TXT6.putClientProperty("emptyTextHint", "Study ID");
		TXT6.setForeground(new Color(90, 80, 200));
		TXT6.setFont(new Font("arial", Font.BOLD, 13));
		TXT6.setHorizontalAlignment(JTextField.CENTER);
		
		TXT7 = new JTextField(5);
		TXT7.putClientProperty("emptyTextHint", "Series Date");
		TXT7.setForeground(new Color(90, 80, 200));
		TXT7.setFont(new Font("arial", Font.BOLD, 13));
		TXT7.setHorizontalAlignment(JTextField.CENTER);
		
		TXT8 = new JTextField(5);
		TXT8.putClientProperty("emptyTextHint", "Study Date");
		TXT8.setForeground(new Color(90, 80, 200));
		TXT8.setFont(new Font("arial", Font.BOLD, 13));
		TXT8.setHorizontalAlignment(JTextField.CENTER);
		
		TXT9 = new JTextField(5);
		TXT9.putClientProperty("emptyTextHint", "Study Time");
		TXT9.setForeground(new Color(90, 80, 200));
		TXT9.setFont(new Font("arial", Font.BOLD, 13));
		TXT9.setHorizontalAlignment(JTextField.CENTER);
		
		TXT10 = new JTextField(5);
		TXT10.putClientProperty("emptyTextHint", "Institution Name");
		TXT10.setForeground(new Color(90, 80, 200));
		TXT10.setFont(new Font("arial", Font.BOLD, 13));
		TXT10.setHorizontalAlignment(JTextField.CENTER);
		
		TXT11 = new JTextField(5);
		TXT11.putClientProperty("emptyTextHint", "Study Description");
		TXT11.setForeground(new Color(90, 80, 200));
		TXT11.setFont(new Font("arial", Font.BOLD, 13));
		TXT11.setHorizontalAlignment(JTextField.CENTER);
		
		TXT12= new JTextField(5);
		TXT12.putClientProperty("emptyTextHint", "Series Number");
		TXT12.setForeground(new Color(90, 80, 200));
		TXT12.setFont(new Font("arial", Font.BOLD, 13));
		TXT12.setHorizontalAlignment(JTextField.CENTER);
		
		TXT13 = new JTextField(5);
		TXT13.putClientProperty("emptyTextHint", "Patient Orientation");
		TXT13.setForeground(new Color(90, 80, 200));
		TXT13.setFont(new Font("arial", Font.BOLD, 13));
		TXT13.setHorizontalAlignment(JTextField.CENTER);

	}
	
	
	void initLabels() {

		LBL_Title = new JLabel("DICOM TAGS");
		LBL_Title.setForeground(Color.WHITE);
		LBL_Title.setFont(new Font("Candara", Font.BOLD, 30));
		LBL_Title.setHorizontalAlignment(JLabel.CENTER);
		
		
		LBL1 = new JLabel("Patient ID");
		LBL1.setForeground(new Color(90, 80, 90));
		LBL1.setFont(new Font("Candara", Font.BOLD, 15));
		LBL1.setHorizontalAlignment(JLabel.CENTER);
		
		
		LBL2 = new JLabel("Patient Name");
		LBL2.setForeground(new Color(90, 80, 90));
		LBL2.setFont(new Font("Candara", Font.BOLD, 15));
		LBL2.setHorizontalAlignment(JLabel.CENTER);

		LBL3 = new JLabel("Patient Birth Date");
		LBL3.setForeground(new Color(90, 80, 90));
		LBL3.setFont(new Font("Candara", Font.BOLD, 15));
		LBL3.setHorizontalAlignment(JLabel.CENTER);

		LBL4 = new JLabel("Patient Sex");
		LBL4.setForeground(new Color(90, 80, 90));
		LBL4.setFont(new Font("Candara", Font.BOLD, 15));
		LBL4.setHorizontalAlignment(JLabel.CENTER);

		LBL5 = new JLabel("Patient Age");
		LBL5.setForeground(new Color(90, 80, 90));
		LBL5.setFont(new Font("Candara", Font.BOLD, 15));
		LBL5.setHorizontalAlignment(JLabel.CENTER);

		LBL6 = new JLabel("Study ID");
		LBL6.setForeground(new Color(90, 80, 90));
		LBL6.setFont(new Font("Candara", Font.BOLD, 15));
		LBL6.setHorizontalAlignment(JLabel.CENTER);

		LBL7 = new JLabel("Series Date");
		LBL7.setForeground(new Color(90, 80, 90));
		LBL7.setFont(new Font("Candara", Font.BOLD, 15));
		LBL7.setHorizontalAlignment(JLabel.CENTER);

		LBL8 = new JLabel("Study Date");
		LBL8.setForeground(new Color(90, 80, 90));
		LBL8.setFont(new Font("Candara", Font.BOLD, 15));
		LBL8.setHorizontalAlignment(JLabel.CENTER);

		LBL9 = new JLabel("Study Time");
		LBL9.setForeground(new Color(90, 80, 90));
		LBL9.setFont(new Font("Candara", Font.BOLD, 15));
		LBL9.setHorizontalAlignment(JLabel.CENTER);

		LBL10 = new JLabel("Institution Name");
		LBL10.setForeground(new Color(90, 80, 90));
		LBL10.setFont(new Font("Candara", Font.BOLD, 15));
		LBL10.setHorizontalAlignment(JLabel.CENTER);

		LBL11 = new JLabel("Study Description");
		LBL11.setForeground(new Color(90, 80, 90));
		LBL11.setFont(new Font("Candara", Font.BOLD, 15));
		LBL11.setHorizontalAlignment(JLabel.CENTER);
		

		LBL12 = new JLabel("Series Number");
		LBL12.setForeground(new Color(90, 80, 90));
		LBL12.setFont(new Font("Candara", Font.BOLD, 15));
		LBL12.setHorizontalAlignment(JLabel.CENTER);

		LBL13 = new JLabel("Patient Orientation");
		LBL13.setForeground(new Color(90, 80, 90));
		LBL13.setFont(new Font("Candara", Font.BOLD, 15));
		LBL13.setHorizontalAlignment(JLabel.CENTER);


	}
	
	
	void initButtons() {

		BT_Confirm = new JButton("Confirm");
		BT_Confirm.setForeground(new Color(40,60,70));
		BT_Confirm.setFont(new Font("Candara", Font.BOLD, 18));
		BT_Confirm.setPreferredSize(new Dimension(150, 35));
		BT_Confirm.addActionListener(this);

		BT_Cancel = new JButton("Cancel");
		BT_Cancel.setForeground(new Color(40,60,70));
		BT_Cancel.setFont(new Font("Candara", Font.BOLD, 18));
		BT_Cancel.addActionListener(this);
		
	}
	
	
	void initPanels() {

		initLabels();
		NPanel = new JPanel();
		NPanel.setLayout(new GridLayout(1, 1));
		NPanel.setBackground(new Color(56,141,191));
		NPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		NPanel.add(LBL_Title);

		initButtons();

		SPanel = new JPanel();
		SPanel.setLayout(new GridLayout(1, 2, 8, 8));
		SPanel.setBackground(Color.white);
		SPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		SPanel.add(BT_Cancel);
		SPanel.add(BT_Confirm);
		


		initTextFields();
		CPanel = new JPanel();
		CPanel.setLayout(new GridLayout(13, 2, 10, 10));
		CPanel.setBackground(Color.WHITE);
		CPanel.setBorder(new EmptyBorder(10, 60, 10, 60));
		CPanel.add(LBL1);CPanel.add(TXT1);
		CPanel.add(LBL2);CPanel.add(TXT2);
		CPanel.add(LBL3);CPanel.add(TXT3);
		CPanel.add(LBL4);CPanel.add(TXT4);
		CPanel.add(LBL5);CPanel.add(TXT5);
		CPanel.add(LBL6);CPanel.add(TXT6);
		CPanel.add(LBL7);CPanel.add(TXT7);
		CPanel.add(LBL8);CPanel.add(TXT8);
		CPanel.add(LBL9);CPanel.add(TXT9);
		CPanel.add(LBL10);CPanel.add(TXT10);
		CPanel.add(LBL11);CPanel.add(TXT11);
		CPanel.add(LBL12);CPanel.add(TXT12);
		CPanel.add(LBL13);CPanel.add(TXT13);
	}
	
	
	public EditTags(String T) {
		try {
			this.setIconImage(ImageIO.read(new File("resource/dicom converter.png")));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		initPanels();
		Container cp = getContentPane();
		cp.setBackground(new Color(90, 80, 90));
		cp.setLayout(new BorderLayout());
		cp.add(NPanel, BorderLayout.NORTH);
		cp.add(CPanel, BorderLayout.CENTER);
		cp.add(SPanel, BorderLayout.SOUTH);

		setBackground(new Color(90, 80, 90));
		setSize(700, 600);
		setTitle(T);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==BT_Cancel) {
				dispose();
			}
		EditTags ed = this;
		
		if(e.getSource()==BT_Confirm) {
			if(ed.TXT1.getText().trim().length()!=0)
			{
				String PATIENTID = ed.TXT1.getText();
				ConverterUI1.dicom.putString(Tag.PatientID, VR.UI,PATIENTID);	
			}
			if(ed.TXT2.getText().trim().length()!=0)
			{
				String PATIENTNAME = ed.TXT2.getText();
				ConverterUI1.dicom.putString(Tag.PatientName, VR.UI,PATIENTNAME);	
			}
			if(ed.TXT3.getText().trim().length()!=0)
			{
				String PATIENTBD = ed.TXT3.getText();
				ConverterUI1.dicom.putString(Tag.PatientBirthDate, VR.UI,PATIENTBD);
			}
			if(ed.TXT4.getText().trim().length()!=0)
			{
				String PATIENTSEX = ed.TXT4.getText();
				ConverterUI1.dicom.putString(Tag.PatientSex, VR.UI,PATIENTSEX);
			}
			if(ed.TXT5.getText().trim().length()!=0)
			{
				String PATIENTAGE = ed.TXT5.getText();
				ConverterUI1.dicom.putString(Tag.PatientAge, VR.UI,PATIENTAGE);
			}
			if(ed.TXT6.getText().trim().length()!=0)
			{
				String STUDYID = ed.TXT6.getText();
				ConverterUI1.dicom.putString(Tag.StudyID, VR.UI,STUDYID);
			}
			if(ed.TXT7.getText().trim().length()!=0)
			{
				String SERIESDATE = ed.TXT7.getText();
				ConverterUI1.dicom.putString(Tag.SeriesDate, VR.UI,SERIESDATE);
			}
			if(ed.TXT8.getText().trim().length()!=0)
			{
				String STUDYDATE = ed.TXT8.getText();
				ConverterUI1.dicom.putString(Tag.StudyDate, VR.UI,STUDYDATE);
			}
			if(ed.TXT9.getText().trim().length()!=0)
			{
				String STUDYTIME = ed.TXT9.getText();
				ConverterUI1.dicom.putString(Tag.StudyTime, VR.UI,STUDYTIME);
			}
			if(ed.TXT10.getText().trim().length()!=0)
			{
				String INSTITUTIONNAME = ed.TXT10.getText();
				ConverterUI1.dicom.putString(Tag.InstitutionName, VR.UI,INSTITUTIONNAME);
			}
			if(ed.TXT11.getText().trim().length()!=0)
			{
				String STUDYDESC = ed.TXT11.getText();
				ConverterUI1.dicom.putString(Tag.StudyDescription, VR.UI,STUDYDESC);
			}
			if(ed.TXT12.getText().trim().length()!=0)
			{
				String SERIESNUMBER = ed.TXT12.getText();
				ConverterUI1.dicom.putString(Tag.SeriesNumber, VR.UI,SERIESNUMBER);
			}
			if(ed.TXT13.getText().trim().length()!=0)
			{
				String PATIENTORIENTATION = ed.TXT13.getText();
				ConverterUI1.dicom.putString(Tag.PatientOrientation, VR.UI,PATIENTORIENTATION);
			}
			dispose();
		}
		
		
		}
	
		
		
}
	


