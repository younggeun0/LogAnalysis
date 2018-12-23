package kr.co.sist.log.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectLogEvt;


/////////////// 12-22-2018 Result JDialog����(����) //////////////////////
public class Result extends JDialog {
	
	private SelectLogEvt sle;
//	private JButton jbConfirm; 
	private JPanel jpn;

	public Result(SelectLogEvt sle, SelectLog sl) {
		super(sl, "��� ���",true);
		this.sle = sle;
		
		// ����� ����� ���â ����
		//������Ʈ �����
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		
		pnNo.add(new JButton("���ϸ�"));
		pnNo.add(new JLabel("file_result"));
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
		
		ResultEvt r = new ResultEvt(this);
		
		setBounds(400, 300, 600, 500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
/////////////// 12-22-2018 Result JDialog����(����) �� //////////////////////