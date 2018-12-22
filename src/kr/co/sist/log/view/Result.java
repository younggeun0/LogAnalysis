package kr.co.sist.log.view;

import javax.swing.JButton;
import javax.swing.JDialog;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectLogEvt;

public class Result extends JDialog {
	
	private SelectLogEvt sle;
	private JButton jbConfirm; 

	public Result(SelectLogEvt sle) {
		
		this.sle = sle;
		
		// 결과를 출력할 결과창 구현
		jbConfirm = new JButton("확인");

		add(jbConfirm);
		
		ResultEvt r = new ResultEvt(this);
		jbConfirm.addActionListener(r);
		
		setBounds(400, 300, 500, 800);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
