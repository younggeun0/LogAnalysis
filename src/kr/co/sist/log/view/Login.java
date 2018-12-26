package kr.co.sist.log.view;

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import kr.co.sist.log.evt.LoginEvt;

/////////////// 12-22-2018 로그인  JFrame구현(헤원) /////////////////
/////////////// 12-23-2018 로그인 디자인 추가(영근) /////////////////
// 변경 내용 : 배치, 컴포넌트 사이즈 수정/ 이미지 추가
@SuppressWarnings("serial")
public class Login extends JFrame {
	
	private JTextField jtId;
	private JPasswordField jpfPw;
	private JButton jbLogin;

	public Login() {
		super("로그인");

		ImageIcon ii = new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\login.png");
		JLabel jlLoginImage = new JLabel(ii);
		jtId = new JTextField();
		jpfPw = new JPasswordField();
		jbLogin = new JButton("로그인");
		jbLogin.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		JLabel jlId= new JLabel("아이디");
		jlId.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		JLabel jlPw = new JLabel("비밀번호");
		jlPw.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		LoginEvt le = new LoginEvt(this);
		jbLogin.addActionListener(le);
		// enter로 로그인처리 
		jbLogin.setActionCommand("login");
		jtId.registerKeyboardAction(le, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		jpfPw.registerKeyboardAction(le, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		
		setLayout(null);
		
		jlLoginImage.setBounds(30, 30, 330, 214);
		jlId.setBounds(30, 300, 150, 30);
		jlPw.setBounds(30, 360, 150, 30);
		jtId.setBounds(180,300, 180, 30);
		jpfPw.setBounds(180,360, 180, 30);
		jbLogin.setBounds(50, 430, 300, 100);
		
		add(jlLoginImage);
		add(jlId);
		add(jlPw);
		add(jtId);
		add(jpfPw);
		add(jbLogin);
			
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

