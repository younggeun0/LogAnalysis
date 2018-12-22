package kr.co.sist.log.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import kr.co.sist.log.evt.LoginEvt;

public class Login extends JFrame {
	
	private JTextField jtId;
	private JTextField jtPw;
	private JButton jbLogin;

	public Login() {
		
		jtId = new JTextField();
		jtPw = new JTextField();
		jbLogin = new JButton("Login");
		
		LoginEvt le = new LoginEvt();
		jbLogin.addActionListener(le);
		
		
		// Login조건을 만족시켰을 때 아래 SelectDialog가 호출되도록 구현
		 new SelectLog(this);
	}
}
