package kr.co.sist.log.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import kr.co.sist.log.view.Login;
import kr.co.sist.log.view.SelectMenu;

/////////////// 12-22-2018 �α���  Event����(���) //////////////////////
public class LoginEvt implements ActionListener {

	private Login login;
	
	public LoginEvt(Login login) {
		this.login = login;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// �α��� ��ư �������� �̺�Ʈ ó��
		String id=login.getJtId().getText();
		String pw=login.getJtPw().getText();
		
		if(id.equals("admin")&& pw.equals("1234")) {
			JOptionPane.showMessageDialog(login, "�α��� ����");
			new SelectMenu(login);
		}else if(id.equals("root")&& pw.equals("1111")) {
			JOptionPane.showMessageDialog(login, "�α��� ����");
			new SelectMenu(login);
		}else {
			JOptionPane.showMessageDialog(login, "�α��� ����");	
		}
	}
}
