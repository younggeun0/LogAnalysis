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


/////////////// 12-22 Result JDialog구현, Event처리(정미) /////////////////////
// //////////// 12-24 Result 디자인 변경(영근), sme로부터 출력 결과 설정 //////
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
		super(sl, "결과 출력",true);
		this.sme = sme;
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		JButton jbConfirm = new JButton("확인");
		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
	
		
		File reportFile = new File(sme.getFilePath());
		
		reportFile.lastModified();
		add(new JLabel(icon)).setBounds(30, 10, 100, 50);
		add(new JTextField(reportFile.getName())).setBounds(125, 10, 100, 30);
		add(new JLabel("생성된 날짜")).setBounds(320, 10, 100, 30);
		add(new JTextField(sme.getLogTxtCreationDate())).setBounds(430, 10, 150, 30);
		add(new JLabel("1. 최다 사용 key의 이름과 횟수")).setBounds(30, 50, 200, 50);
		add(new JTextField(sme.getMostFrequentKey()+" : "+sme.getMapKey().get(sme.getMostFrequentKey())+"번")).setBounds(320, 60, 200, 30);
		add(new JLabel("2. 브라우저별 접속횟수, 비율")).setBounds(30, 130, 200, 50);
		add(browserInfo()).setBounds(300, 100, 200, 70); 
		add(new JLabel("3. 서비스 성공(200) 실패(404) 횟수")).setBounds(30, 230, 200, 50);
		add(new JTextField("성공(200) : "+sme.getCode200()+", 실패(404) : "+sme.getCode404())).setBounds(300, 230, 200, 50);
		add(new JLabel("4. 요청이 가장 많은 시간")).setBounds(30, 310, 200, 30);
		add(new JTextArea(sme.getMostFrequentHour()+"시")).setBounds(300	, 310, 200,30);
		add(new JLabel("5. 비정상 요청 횟수와 비율")).setBounds(30, 360, 200, 30);
		add(new JLabel(sme.getCode403()+" ("
				+ String.format("%4.2f", 
						sme.getCode403()/(double)sme.getRequestNum()*100)
				+"%)")).setBounds(300, 360, 200, 30);
		if (sme.getStart() == 0 && sme.getEnd() == 0) {
			pnCe.add(new JTextField("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" 라인 가장 빈도수가 높은 key와 횟수"));
			pnCe.add(new JTextField(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"번"));
		} else {
			pnCe.add(new JTextField("6. "+sme.getStart()+"~"+(sme.getEnd() > sme.getRequestNum() ? sme.getRequestNum() : sme.getEnd())
					+" 라인 가장 빈도수가 높은 key와 횟수"));
			pnCe.add(new JTextField(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
					+sme.getMapKeyBetweenStartAndEnd().get(sme.getMostFrequentKeyBetweenStartAndEnd())+"번"));
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