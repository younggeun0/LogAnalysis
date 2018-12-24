package kr.co.sist.log.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectLogEvt;


/////////////// 12-22 Result JDialog����, Eventó��(����) /////////////////////
// //////////// 12-24 Result ������ ����(����), sle�κ��� ��� ��� ���� //////
@SuppressWarnings("serial")
public class Result extends JDialog {
	
	private SelectLogEvt sle;
	
	public JPanel browserInfo() {
		
		JPanel jpBrowser = new JPanel();
		setLayout(new GridLayout(1, 4));
		
		JLabel[] jlBrowser = new JLabel[sle.getBrowser().length];
		
		Map<String, Integer> mapBrowser = sle.getMapBrowser();
		Map<String, String> mapBrowserShare = sle.getMapBrowserShare();
		
		StringBuilder content = new StringBuilder();
		
		Set<String> key = mapBrowser.keySet();
		Iterator<String> itKey = key.iterator();
		String bName = "";
		int i = 0;
		while(itKey.hasNext()) {
			bName = itKey.next();
			
			content.append(bName).append(" : ").append(mapBrowser.get(bName)).append(" (")
			.append(mapBrowserShare.get(bName)).append("%)");
			jlBrowser[i] = new JLabel(content.toString());
			jpBrowser.add(jlBrowser[i]);
			i++;
		}
		
		return jpBrowser;
	}

	public Result(SelectLogEvt sle, SelectLog sl) {
		super(sl, "��� ���",true);
		this.sle = sle;
		
		// ����� ����� ���â ����
		//������Ʈ �����
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		JButton jbConfirm = new JButton("Ȯ��"); 
		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		File reportFile = new File(sle.getFilePath());
		
		reportFile.lastModified();
		pnNo.add(new JButton("���ϸ�"));
		pnNo.add(new JLabel(reportFile.getName()));
		pnNo.add(new JButton("������ ��¥"));
		// ��¥�� �޾ƿ� �ν��Ͻ� ���� �ʿ�(���� ���� ��û)
		pnNo.add(new JLabel(sle.getLogTxtCreationDate()));
		
		pnCe.add(new JButton("1.  �ִ� ��� key"));
		pnCe.add(new JTextArea(sle.getMostFrequentKey()));
		pnCe.add(new JButton("2. �������� ����Ƚ��, ����"));
		pnCe.add(browserInfo()); 
		pnCe.add(new JButton("3. ���� ����(200) ����(404) Ƚ��"));
		pnCe.add(new JLabel("200 : "+sle.getCode200()+", 404 : "+sle.getCode404()));
		pnCe.add(new JButton("4. ��û�� ���� ���� �ð�"));
		pnCe.add(new JLabel(sle.getMostFrequentHour()+"��"));
		pnCe.add(new JButton("5. ������ ��û Ƚ���� ����"));
		pnCe.add(new JTextField(sle.getCode403()+" ("
				+ String.format("4.2f", 
						sle.getCode403()/(double)sle.getRequestNum()*100)
				+"%)"));
		pnCe.add(new JButton("6.  1000~1500 ���� ���� �󵵼��� ���� key�� Ƚ��"));
		try {
			pnCe.add(new JTextField("key "+sle.getMostFrequentKeyBetween1000And1500()+" : "
					+sle.getMapKeyBetween1000And1500().get(sle.getMostFrequentKeyBetween1000And1500())
					+"��"));
		} catch (NullPointerException e) {
			System.out.println("NullPointerException");
			System.out.println("����ó�� �ؾ���");
		}
		
		pnNo.setLayout(new GridLayout(1, 4));
		pnCe.setLayout(new GridLayout(6, 2));
		setLayout(new BorderLayout());
		//�̺�Ʈ�߻�
		
		
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