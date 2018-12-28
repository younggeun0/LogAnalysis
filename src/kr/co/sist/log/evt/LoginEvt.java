package kr.co.sist.log.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import kr.co.sist.log.view.Login;
import kr.co.sist.log.view.SelectMenu;

/////////////// 12-22-2018 로그인  Event구현(헤원) //////////////////////
public class LoginEvt implements ActionListener {

	private Login login;
	
	public LoginEvt(Login login) {
		this.login = login;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "login") {
			// 로그인 버튼 눌렸을때 이벤트 처리
			String id=login.getJtId().getText();
			String pw= new String(login.getJpfPw().getPassword());
			if (id.isEmpty()) {
				JOptionPane.showMessageDialog(login, "아이디를 입력해주세요.");
				login.getJtId().requestFocus();
				return;
			} else {
				if (pw.isEmpty()) {
					JOptionPane.showMessageDialog(login, "비밀번호를 입력해주세요.");
					login.getJpfPw().requestFocus();
					return;
				}
			}
			
			if(id.equals("admin")&& pw.equals("1234")) {
				JOptionPane.showMessageDialog(login, "로그인 성공");
				login.dispose();
				new SelectMenu(login);
			}else if(id.equals("root")&& pw.equals("1111")) {
				JOptionPane.showMessageDialog(login, "로그인 성공");
				login.dispose();
				new SelectMenu(login);
			}else {
				JOptionPane.showMessageDialog(login, "로그인 실패");	
			}
		}
	}
}
