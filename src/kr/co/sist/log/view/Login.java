package kr.co.sist.log.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import kr.co.sist.log.evt.LoginEvt;

/////////////// 12-22-2018 �α���  JFrame����(���) /////////////////
/////////////// 12-23-2018 �α��� ������ �߰�(����) /////////////////
// ���� ���� : ��ġ, ������Ʈ ������ ����/ �̹��� �߰�
@SuppressWarnings("serial")
public class Login extends JFrame {
	
	private JTextField jtId;
	private JPasswordField jpfPw;
	private JButton jbLogin;
	ImageIcon icon;

	public Login() {
		super("�α���");

		ImageIcon ii = new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\login.png");
	    icon = new ImageIcon("C:\\Users\\SIST\\Desktop\\aa.jpg");
	      JPanel background = new JPanel() {
	         public void paintComponent(Graphics g) {
	            g.drawImage(icon.getImage(), 0,0,null);
	            setOpaque(false);
	            super.paintComponent(g);
	         }
	      };
	      
	      background.setLayout(null);
	      
	      
		JLabel jlLoginImage = new JLabel(ii);
		jtId = new JTextField();
		jpfPw = new JPasswordField();
		jbLogin = new JButton("�α���");
		jbLogin.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		JLabel jlId= new JLabel("���̵�");
		jlId.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		JLabel jlPw = new JLabel("��й�ȣ");
		jlPw.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
//		setLayout(null);
		
		LoginEvt le = new LoginEvt(this);
		jbLogin.addActionListener(le);
		jbLogin.setActionCommand("login");
		jtId.registerKeyboardAction(le, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		jpfPw.registerKeyboardAction(le, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		
		jlLoginImage.setBounds(30, 30, 330, 214);
		jlId.setBounds(30, 300, 150, 30);
		jlPw.setBounds(30, 360, 150, 30);
		jtId.setBounds(180,300, 180, 30);
		jpfPw.setBounds(180,360, 180, 30);
		jbLogin.setBounds(50, 430, 300, 100);
		
		background.add(jlLoginImage);
		background.add(jlId);
		background.add(jlPw);
		background.add(jtId);
		background.add(jpfPw);
		background.add(jbLogin);
		
		add(background);
			
		setBounds(500,250,400, 600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JTextField getJtId() {
		return jtId;
	}
	public JPasswordField getJpfPw() {
		return jpfPw;
	}
}

