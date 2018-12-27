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
<<<<<<< HEAD
<<<<<<< HEAD
=======
	ImageIcon icon;//이미지 아이콘
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
=======
	ImageIcon icon;//이미지 아이콘
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
	
	public SelectMenu(JFrame login) {
		super(login, "Log Analysis App", true);
		
<<<<<<< HEAD
<<<<<<< HEAD
		JLabel jlMain = new JLabel(new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\bg.png"));
=======
=======
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
		//이미지 삽입
		icon = new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\aaa.jpg");
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0,0,null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		background.setLayout(null);
		
		///
		
<<<<<<< HEAD
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
=======
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
		JLabel jlStart = new JLabel("시작");
		JLabel jlEnd = new JLabel("끝");
		
		jbView = new JButton("View");
		jbReport = new JButton("Report");
		jbLineView = new JButton("LineView");
		jtStart = new JTextField();
		jtEnd = new JTextField();
		
<<<<<<< HEAD
		setLayout(null);
=======
//		setLayout(null);
		
		jbView.setIcon(new ImageIcon("C:\\Users\\owner\\Desktop\\design\\viewIMG.jpg"));
		jbReport.setIcon(new ImageIcon("C:\\Users\\owner\\Desktop\\design\\reviewIMG.jpg"));
>>>>>>> parent of cdbdae5... 理쒖쥌
		
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
=======
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
		background.add(jbView);
		jbView.setBounds(40, 90, 150, 50);
		background.add(jbReport);
		jbReport.setBounds(220, 90, 150, 50);
		background.add(jbLineView);
<<<<<<< HEAD
		jbLineView.setBounds(260, 160, 110, 70);
=======
		jbLineView.setBounds(260, 185, 112, 40);
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
		background.add(jtStart);
		jtStart.setBounds(95, 160, 150, 30);
		background.add(jtEnd);
		jtEnd.setBounds(95, 200, 150, 30);
		background.add(jlStart);
		jlStart.setBounds(55,160,50,30);
		background.add(jlEnd);
		jlEnd.setBounds(55,200,50,30);
		
<<<<<<< HEAD
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
=======
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
		
		SelectMenuEvt sme = new SelectMenuEvt(this);
		jbView.addActionListener(sme);
		jbReport.addActionListener(sme);
		jbLineView.addActionListener(sme);
		jbLineView.setActionCommand("lineView");
		jtStart.registerKeyboardAction(sme, "lineView", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		jtEnd.registerKeyboardAction(sme, "lineView", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		
<<<<<<< HEAD
<<<<<<< HEAD
		setBounds(400, 100, 420, 500);
=======
//		ScrollPane = new JScrollPane(background);
		setContentPane(background);
		setBounds(400, 300, 420, 300);
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
=======
//		ScrollPane = new JScrollPane(background);
		setContentPane(background);
		setBounds(400, 300, 420, 300);
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
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
