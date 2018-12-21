package kr.co.sist.log.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.co.sist.log.evt.SelectDialogEvt;

@SuppressWarnings("serial")
public class SelectDialog extends JDialog {

	private JButton jbView;
	private JButton jbReport;
	
	public SelectDialog(JFrame login) {
		super(login, "Log Analysis App");
		
		jbView = new JButton("View");
		jbReport = new JButton("Report");
		
		setLayout(null);
		
		jbView.setBounds(40, 30, 150, 50);
		add(jbView);
		jbReport.setBounds(220, 30, 150, 50);
		add(jbReport);
		
		SelectDialogEvt sde = new SelectDialogEvt(this);
		jbView.addActionListener(sde);
		jbReport.addActionListener(sde);
		
		setBounds(400, 300, 430, 160);
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
