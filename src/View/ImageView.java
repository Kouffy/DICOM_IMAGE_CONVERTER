package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public class ImageView extends JFrame implements ActionListener {


	private static final long serialVersionUID = 1L;

	JLabel labelImageDisplay, LBL_TITLE1 ;
	JPanel  CPanel,SPanel;
	JButton BT_Cancel ;


	
	void initButtons() {

		BT_Cancel = new JButton("CLOSE");
		BT_Cancel.setForeground(new Color(40,60,70));
		BT_Cancel.setFont(new Font("Candara", Font.BOLD, 18));
		BT_Cancel.addActionListener(this);
		
	}
	

	
	void initLabels() {
		LBL_TITLE1=new JLabel("DICOM VIEWER");
		LBL_TITLE1.setForeground(Color.WHITE);
		LBL_TITLE1.setFont(new Font("Candara", Font.BOLD, 30));
		LBL_TITLE1.setHorizontalAlignment(JLabel.CENTER);
		labelImageDisplay= new JLabel();
	}
	void initPanels() {
		initLabels();
		initButtons();
		CPanel = new JPanel();
		CPanel.setLayout(new BorderLayout());
		CPanel.setBackground(Color.gray);
		CPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		CPanel.add(labelImageDisplay, BorderLayout.CENTER);
		
		SPanel = new JPanel();
		SPanel.setLayout(new BorderLayout());
		SPanel.setBackground(Color.gray);
		SPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		SPanel.add(BT_Cancel, BorderLayout.CENTER);
		

	}

	

	

	public ImageView(String T)  {
		try {
			this.setIconImage(ImageIO.read(new File("resource/dicom converter.png")));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Container cp = getContentPane();
		cp.setBackground(new Color(90, 80, 90));
		cp.setLayout(new BorderLayout());
		initPanels();
		cp.add(CPanel, BorderLayout.CENTER);
		cp.add(SPanel, BorderLayout.SOUTH);
		setBackground(new Color(90, 80, 90));
		setSize(800, 700);
		setTitle(T);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(false);
		setVisible(true);
		labelImageDisplay.setIcon(DCMViewerUI2.ImageView); 

	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==BT_Cancel) {
			dispose();
		}
		
	}
}
