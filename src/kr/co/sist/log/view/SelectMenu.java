package kr.co.sist.log.view;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import kr.co.sist.log.evt.SelectMenuEvt;

@SuppressWarnings("serial")
public class SelectMenu extends JDialog {

	private JButton jbView;
	private JButton jbReport;
	private JButton jbLineView;
	private JTextField jtStart;
	private JTextField jtEnd;
	
	public SelectMenu(JFrame login) {
		super(login, "Log Analysis App", true);
		
		JLabel jlMain = new JLabel(new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\bg.png"));
		JLabel jlStart = new JLabel("Ω√¿€");
		JLabel jlEnd = new JLabel("≥°");
		
		jbView = new JButton("View");
		jbReport = new JButton("Report");
		jbLineView = new JButton("LineView");
		jtStart = new JTextField();
		jtEnd = new JTextField();
		
		setLayout(null);
		
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
		jbLineView.setActionCommand("lineView");
		jtStart.registerKeyboardAction(sme, "lineView", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		jtEnd.registerKeyboardAction(sme, "lineView", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		
		setBounds(400, 100, 420, 500);
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
