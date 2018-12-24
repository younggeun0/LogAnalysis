package kr.co.sist.log.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import kr.co.sist.log.evt.LoginEvt;

/////////////// 12-22-2018 �α���  JFrame����(���) /////////////////
/////////////// 12-23-2018 �α��� ������ �߰�(����) /////////////////
// ���� ���� : ��ġ, ������Ʈ ������ ����/ �̹��� �߰�
public class Login extends JFrame {
	
	private JTextField jtId;
	private JTextField jtPw;
	private JButton jbLogin;

	public Login() {
		super("�α���");

		ImageIcon ii = new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\login.png");
		JLabel jlLoginImage = new JLabel(ii);
		jtId = new JTextField();
		jtPw = new JTextField();
		jbLogin = new JButton("�α���");
		jbLogin.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		JLabel jlId= new JLabel("���̵�");
		jlId.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		JLabel jlPw = new JLabel("��й�ȣ");
		jlPw.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		LoginEvt le = new LoginEvt(this);
		jbLogin.addActionListener(le);
		
		setLayout(null);
		
		jlLoginImage.setBounds(30, 30, 330, 214);
		jlId.setBounds(30, 300, 150, 30);
		jlPw.setBounds(30, 360, 150, 30);
		jtId.setBounds(180,300, 180, 30);
		jtPw.setBounds(180,360, 180, 30);
		jbLogin.setBounds(50, 430, 300, 100);
		
		add(jlLoginImage);
		add(jlId);
		add(jlPw);
		add(jtId);
		add(jtPw);
		add(jbLogin);
			
		setBounds(500,250,400, 600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JTextField getJtId() {
		return jtId;
	}
	public JTextField getJtPw() {
		return jtPw;
	}
}

