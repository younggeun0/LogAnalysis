package kr.co.sist.log.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import kr.co.sist.log.evt.SelectLogEvt;

@SuppressWarnings("serial")
public class SelectLog extends JDialog {

	private JButton jbView;
	private JButton jbReport;
	
	public SelectLog(JFrame login) {
		super(login, "Log Analysis App", true);
		
		jbView = new JButton("View");
		jbReport = new JButton("Report");
		
		setLayout(null);
		
		jbView.setBounds(40, 30, 150, 50);
		add(jbView);
		jbReport.setBounds(220, 30, 150, 50);
		add(jbReport);
		
		SelectLogEvt sde = new SelectLogEvt(this);
		jbView.addActionListener(sde);
		jbReport.addActionListener(sde);
		
		setBounds(400, 300, 420, 150);
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
}
