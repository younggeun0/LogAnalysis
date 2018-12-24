package kr.co.sist.log.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectLogEvt;


/////////////// 12-22 Result JDialog����(����) //////////////////////
// //////////// 12-24 Result ������ ����(����), ��� ���� ���� //////
@SuppressWarnings("serial")
public class Result extends JDialog {
	
	private SelectLogEvt sle;

	public Result(SelectLogEvt sle, SelectLog sl) {
		super(sl, "��� ���",true);
		this.sle = sle;
		
		// ����� ����� ���â ����
		//������Ʈ �����
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		JButton jbConfirm = new JButton("Ȯ��"); 
		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		pnNo.add(new JButton("���ϸ�"));
		pnNo.add(new JLabel(new File(sle.getFilePath()).getName()));
		pnNo.add(new JButton("������ ��¥"));
		pnNo.add(new JLabel("Date_result"));
		
		pnCe.add(new JButton("1  �ִ� ���Ű"));
		pnCe.add(new JTextArea("��� "));
		pnCe.add(new JButton("2 �������� ����Ƚ��, ����"));
		pnCe.add(new JTextField(" "));
		pnCe.add(new JButton("3 ���񽺸� ���� ���� Ƚ��"));
		pnCe.add(new JLabel(" "));
		pnCe.add(new JButton("4  ��û�� ���� ���� �ð�"));
		pnCe.add(new JLabel(" "));
		pnCe.add(new JButton("5 ������ ��û Ƚ�� ����"));
		pnCe.add(new JTextField(" "));
		pnCe.add(new JButton("6  �Է� ���� �������"));
		pnCe.add(new JTextField(" "));
		
		pnNo.setLayout(new GridLayout(1, 4));
		pnCe.setLayout(new GridLayout(6, 2));
		setLayout(new BorderLayout());
		
		//��ġ
		add("North",pnNo);
		add("Center",pnCe);
		add("South",jbConfirm);
		
		ResultEvt r = new ResultEvt(this);
		jbConfirm.addActionListener(r);
		
		setBounds(400, 300, 600, 500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}