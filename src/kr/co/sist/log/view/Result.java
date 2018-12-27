package kr.co.sist.log.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectMenuEvt;


/////////////// 12-22 Result JDialog����, Eventó��(����) /////////////////////
// //////////// 12-24 Result ������ ����(����), sme�κ��� ��� ��� ���� //////
@SuppressWarnings("serial")
public class Result extends JDialog {
	
	private SelectMenuEvt sme;
	
	ImageIcon icon = new ImageIcon("C:\\Users\\owner\\Desktop\\design\\reviewIMG.jpg");
	public JPanel browserInfo() {
		
		JPanel jpBrowser = new JPanel();
		setLayout(new GridLayout(1, 5));
		
		JLabel[] jlBrowser = new JLabel[sme.getBrowser().length];
		
		Map<String, Integer> mapBrowser = sme.getMapBrowser();
		Map<String, String> mapBrowserShare = sme.getMapBrowserShare();
		
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
			content.delete(0, content.length());
		}
		
		return jpBrowser;
	}

	public Result(SelectMenuEvt sme, SelectMenu sl) {
		super(sl, "��� ���",true);
		this.sme = sme;
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		JButton jbConfirm = new JButton("Ȯ��");
		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
	
		
		File reportFile = new File(sme.getFilePath());
		
		reportFile.lastModified();
		add(new JLabel(icon)).setBounds(30, 10, 100, 50);
		add(new JTextField(reportFile.getName())).setBounds(125, 10, 100, 30);
		add(new JLabel("������ ��¥")).setBounds(320, 10, 100, 30);
		add(new JTextField(sme.getLogTxtCreationDate())).setBounds(430, 10, 150, 30);
		add(new JLabel("1. �ִ� ��� key�� �̸��� Ƚ��")).setBounds(30, 50, 200, 50);
		add(new JTextField(sme.getMostFrequentKey()+" : "+sme.getMapKey().get(sme.getMostFrequentKey())+"��")).setBounds(320, 60, 200, 30);
		add(new JLabel("2. �������� ����Ƚ��, ����")).setBounds(30, 130, 200, 50);
		add(browserInfo()).setBounds(300, 100, 200, 70); 
		add(new JLabel("3. ���� ����(200) ����(404) Ƚ��")).setBounds(30, 230, 200, 50);
		add(new JTextField("����(200) : "+sme.getCode200()+", ����(404) : "+sme.getCode404())).setBounds(300, 230, 200, 50);
		add(new JLabel("4. ��û�� ���� ���� �ð�")).setBounds(30, 310, 200, 30);
		add(new JTextArea(sme.getMostFrequentHour()+"��")).setBounds(300	, 310, 200,30);
		add(new JLabel("5. ������ ��û Ƚ���� ����")).setBounds(30, 360, 200, 30);
		add(new JLabel(sme.getCode403()+" ("
				+ String.format("%4.2f", 
						sme.getCode403()/(double)sme.getRequestNum()*100)
				+"%)")).setBounds(300, 360, 200, 30);
		if (sme.getStart() == 0 && sme.getEnd() == 0) {
			pnCe.add(new JTextField("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" ���� ���� �󵵼��� ���� key�� Ƚ��"));
			pnCe.add(new JTextField(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"��"));
		} else {
			pnCe.add(new JTextField("6. "+sme.getStart()+"~"+(sme.getEnd() > sme.getRequestNum() ? sme.getRequestNum() : sme.getEnd())
					+" ���� ���� �󵵼��� ���� key�� Ƚ��"));
			pnCe.add(new JTextField(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
					+sme.getMapKeyBetweenStartAndEnd().get(sme.getMostFrequentKeyBetweenStartAndEnd())+"��"));
		}
		add(jbConfirm).setBounds(325, 480, 70, 50);
//		pnNo.setLayout(new GridLayout(1, 4));
//		pnCe.setLayout(new GridLayout(6, 2));
//		setLayout(new BorderLayout());
		setLayout(null);
		
		
//		add("North",pnNo);
//		add("Center",pnCe);
//		add("South",jbConfirm);
		
		ResultEvt r = new ResultEvt(this);
		jbConfirm.addActionListener(r);
		
		setBounds(400, 300, 650, 500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}