package kr.co.sist.log.view;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.ScrollPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import kr.co.sist.log.evt.SelectMenuEvt;

@SuppressWarnings("serial")
public class SelectMenu extends JDialog {

	private JButton jbView;
	private JButton jbReport;
	private JButton jbLineView;
	private JTextField jtStart;
	private JTextField jtEnd;
	ImageIcon icon;//ÀÌ¹ÌÁö ¾ÆÀÌÄÜ
	
	public SelectMenu(JFrame login) {
		super(login, "Log Analysis App", true);
		

		//ÀÌ¹ÌÁö »ðÀÔ
		icon = new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\aaa.jpg");
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0,0,null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		background.setLayout(null);
		
		
		JLabel jlStart = new JLabel("½ÃÀÛ");
		JLabel jlEnd = new JLabel("³¡");
		
		jbView = new JButton("View");
		jbReport = new JButton("Report");
		jbLineView = new JButton("LineView");
		jtStart = new JTextField();
		jtEnd = new JTextField();
		
//		setLayout(null);
		
		jbView.setIcon(new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\viewIMG.jpg"));
		jbReport.setIcon(new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\reviewIMG.jpg"));
		jbLineView.setIcon(new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\LineView.jpg"));
		
		background.add(jbView);
		jbView.setBounds(40, 90, 150, 50);
		background.add(jbReport);
		jbReport.setBounds(220, 90, 150, 50);
		background.add(jbLineView);
		jbLineView.setBounds(260, 185, 112, 40);
		background.add(jtStart);
		jtStart.setBounds(95, 160, 150, 30);
		background.add(jtEnd);
		jtEnd.setBounds(95, 200, 150, 30);
		background.add(jlStart);
		jlStart.setBounds(55,160,50,30);
		background.add(jlEnd);
		jlEnd.setBounds(55,200,50,30);
		

		jlMain.setBounds(40, 20, 330, 250);
		add(jlMain);
		jbView.setBounds(40, 290, 150, 50);
		add(jbView);
		jbReport.setBounds(220, 290, 150, 50);
		add(jbReport);
		jbLineView.setBounds(260, 360, 110, 70);
		add(jbLineView);
		jtStart.setBounds(95, 360, 150, 30);
		add(jtStart);
		jtEnd.setBounds(95, 400, 150, 30);
		add(jtEnd);
		jlStart.setBounds(55,360,50,30);
		add(jlStart);
		jlEnd.setBounds(55,400,50,30);
		add(jlEnd);

		
		SelectMenuEvt sme = new SelectMenuEvt(this);
		jbView.addActionListener(sme);
		jbReport.addActionListener(sme);
		jbLineView.addActionListener(sme);
		

//		ScrollPane = new JScrollPane(background);
		setContentPane(background);
		setBounds(400, 300, 420, 300);

		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public JButton getJbView() {
		return jbView;
	}
	public JButton getJbReport() {
		return jbReport;
	}
	public JButton getJbLineView() {
		return jbLineView;
	}
	public JTextField getJtStart() {
		return jtStart;
	}
	public JTextField getJtEnd() {
		return jtEnd;
	}
}
