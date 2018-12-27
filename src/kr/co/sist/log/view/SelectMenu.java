package kr.co.sist.log.view;

import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	JScrollPane scrollPane;
	ImageIcon icon;

	public SelectMenu(JFrame login) {
		super(login, "Log Analysis App", true);
		
		icon=new ImageIcon("C:\\Users\\owner\\Desktop\\결과창\\메뉴선택.jpg");
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(),0,0,null);
				super.paintComponents(g);
			}
		};
		scrollPane=new JScrollPane(background);setContentPane(scrollPane);

		JLabel jlStart = new JLabel("시작");
		JLabel jlEnd = new JLabel("끝");

		jbView = new JButton("View");
		jbReport = new JButton("Report");
		jbLineView = new JButton("LineView");
		jtStart = new JTextField();
		jtEnd = new JTextField();

		setLayout(null);

		jbView.setBounds(40, 30, 150, 50);
		add(jbView);
		jbReport.setBounds(220, 30, 150, 50);
		add(jbReport);
		jbLineView.setBounds(260, 100, 110, 70);
		add(jbLineView);
		jtStart.setBounds(95, 100, 150, 30);
		add(jtStart);
		jtEnd.setBounds(95, 140, 150, 30);
		add(jtEnd);
		jlStart.setBounds(55, 100, 50, 30);
		add(jlStart);
		jlEnd.setBounds(55, 140, 50, 30);
		add(jlEnd);

		SelectMenuEvt sme = new SelectMenuEvt(this);
		jbView.addActionListener(sme);
		jbReport.addActionListener(sme);
		jbLineView.addActionListener(sme);
		jbLineView.setActionCommand("lineView");
		jtStart.registerKeyboardAction(sme, "lineView", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);
		jtEnd.registerKeyboardAction(sme, "lineView", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);

		setBounds(400, 300, 420, 282);
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
