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
import javax.swing.JTextField;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectMenuEvt;


/////////////// 12-22 Result JDialog����, Eventó��(����) /////////////////////
// //////////// 12-24 Result ������ ����(����), sme�κ��� ��� ��� ���� //////
@SuppressWarnings("serial")
public class Result extends JDialog {
	
	private SelectMenuEvt sme;
	
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
		pnNo.add(new JButton("���ϸ�"));
		pnNo.add(new JLabel(reportFile.getName()));
		pnNo.add(new JButton("������ ��¥"));
		pnNo.add(new JLabel(sme.getLogTxtCreationDate()));
		pnCe.add(new JButton("1. �ִ� ��� key�� �̸��� Ƚ��"));
		pnCe.add(new JLabel(sme.getMostFrequentKey()+" : "+sme.getMapKey().get(sme.getMostFrequentKey())+"��"));
		pnCe.add(new JButton("2. �������� ����Ƚ��, ����"));
		pnCe.add(browserInfo()); 
		pnCe.add(new JButton("3. ���� ����(200) ����(404) Ƚ��"));
		pnCe.add(new JLabel("����(200) : "+sme.getCode200()+", ����(404) : "+sme.getCode404()));
		pnCe.add(new JButton("4. ��û�� ���� ���� �ð�"));
		pnCe.add(new JLabel(sme.getMostFrequentHour()+"��"));
		pnCe.add(new JButton("5. ������ ��û Ƚ���� ����"));
		pnCe.add(new JLabel(sme.getCode403()+" ("
				+ String.format("%4.2f", 
						sme.getCode403()/(double)sme.getRequestNum()*100)
				+"%)"));
		if (sme.getStart() == 0 && sme.getEnd() == 0) {
			pnCe.add(new JButton("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" ���� ���� �󵵼��� ���� key�� Ƚ��"));
			pnCe.add(new JLabel(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"��"));
		} else {
			pnCe.add(new JButton("6. "+sme.getStart()+"~"+(sme.getEnd() > sme.getRequestNum() ? sme.getRequestNum() : sme.getEnd())
					+" ���� ���� �󵵼��� ���� key�� Ƚ��"));
			pnCe.add(new JLabel(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
					+sme.getMapKeyBetweenStartAndEnd().get(sme.getMostFrequentKeyBetweenStartAndEnd())+"��"));
		}
		pnNo.setLayout(new GridLayout(1, 4));
		pnCe.setLayout(new GridLayout(6, 2));
		setLayout(new BorderLayout());
		
		add("North",pnNo);
		add("Center",pnCe);
		add("South",jbConfirm);
		
		ResultEvt r = new ResultEvt(this);
		jbConfirm.addActionListener(r);
		
		setBounds(400, 300, 650, 500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}