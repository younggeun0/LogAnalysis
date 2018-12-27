package kr.co.sist.log.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectMenuEvt;


/////////////// 12-22 Result JDialog구현, Event처리(정미) /////////////////////
// //////////// 12-24 Result 디자인 변경(영근), sme로부터 출력 결과 설정 //////
@SuppressWarnings("serial")
public class Result extends JDialog {
	
	ImageIcon icon;
	private SelectMenuEvt sme;
	
//	ImageIcon icon = new ImageIcon("C:\\Users\\owner\\Desktop\\design\\reviewIMG.jpg");
	public JPanel browserInfo() {
		
		
		JPanel jpBrowser = new JPanel();
		jpBrowser.setOpaque(false);
//		setLayout(new GridLayout(1, 5));
		
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
		icon = new ImageIcon("C:\\dev\\workspace\\logAnalysisApp\\img\\resultchang2.jpg");
		JPanel bgr = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(),0,0,null);
				setOpaque(false);
			}
		};
		
		bgr.setLayout(null);
		this.sme = sme;
		
		JPanel bg = new JPanel();
		JButton jbConfirm = new JButton("확인");
//		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD));
	
//		Icon = ImageIcon("");
		File reportFile = new File(sme.getFilePath());
		
		bgr.add(new JLabel("파일명")).setBounds(40, 5, 100, 50);
		bgr.add(new JLabel(reportFile.getName())).setBounds(125, 10, 100, 30);
		bgr.add(new JLabel("생성된 날짜")).setBounds(300, 10, 100, 30);
		bgr.add(new JLabel(sme.getLogTxtCreationDate())).setBounds(380, 10, 150, 20);
		bgr.add(new JLabel("1. 최다 사용 key의 이름과 횟수")).setBounds(80, 50, 200, 50);
		bgr.add(new JLabel(sme.getMostFrequentKey()+" : "+sme.getMapKey().get(sme.getMostFrequentKey())+"번")).setBounds(380, 60, 200, 30);
		bgr.add(new JLabel("2. 브라우저별 접속횟수, 비율")).setBounds(80, 130, 200, 50);
		bgr.add(browserInfo()).setBounds(380, 100, 200, 150); 
		bgr.add(new JLabel("3. 서비스 성공(200) 실패(404) 횟수")).setBounds(80, 230, 200, 50);
		bgr.add(new JLabel("성공(200) : "+sme.getCode200()+", 실패(404) : "+sme.getCode404())).setBounds(380, 230, 200, 50);
		bgr.add(new JLabel("4. 요청이 가장 많은 시간")).setBounds(80, 310, 200, 30);
		bgr.add(new JLabel(sme.getMostFrequentHour()+"시")).setBounds(380	, 310, 200,30);
		bgr.add(new JLabel("5. 비정상 요청 횟수와 비율")).setBounds(80, 360, 200, 30);
		bgr.add(new JLabel(sme.getCode403()+" ("
				+ String.format("%4.2f", 
						sme.getCode403()/(double)sme.getRequestNum()*100)
				+"%)")).setBounds(380, 360, 200, 30);
		if (sme.getStart() == 0 && sme.getEnd() == 0) {
			bgr.add(new JLabel("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" 라인 가장 고빈도 key, 횟수")).setBounds(80, 410, 250, 30);
			bgr.add(new JLabel(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"번")).setBounds(380, 410, 200, 30);
		} else {
			bgr.add(new JLabel("6. "+sme.getStart()+"~"+(sme.getEnd() > sme.getRequestNum() ? sme.getRequestNum() : sme.getEnd())
					+" 라인 가장 빈도수가 높은 key와 횟수"));
			bgr.add(new JLabel(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
					+sme.getMapKeyBetweenStartAndEnd().get(sme.getMostFrequentKeyBetweenStartAndEnd())+"번"));
		}
//		pnNo.setLayout(new GridLayout(1, 4));
//		pnCe.setLayout(new GridLayout(6, 2));
//		setLayout(new BorderLayout());
		setLayout(null);
		
		
		bgr.add(jbConfirm).setBounds(270, 450, 60, 30);
//		add("North",pnNo);
//		add("Center",pnCe);
//		add("South",jbConfirm);
		
		ResultEvt r = new ResultEvt(this);
		jbConfirm.addActionListener(r);
		
		setContentPane(bgr);
		setBounds(400, 300, 650, 530);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}