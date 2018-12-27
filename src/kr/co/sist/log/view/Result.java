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

import kr.co.sist.log.evt.ResultEvt;
import kr.co.sist.log.evt.SelectMenuEvt;


/////////////// 12-22 Result JDialog구현, Event처리(정미) /////////////////////
// //////////// 12-24 Result 디자인 변경(영근), sme로부터 출력 결과 설정 //////
@SuppressWarnings("serial")
public class Result extends JDialog {
	
	private SelectMenuEvt sme;
	
	public JPanel browserInfo() {
		
		JPanel jpBrowser = new JPanel();
		setLayout(new GridLayout(1, 5));
		
		JTextField[] jlBrowser = new JTextField[sme.getBrowser().length];
		
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
			jlBrowser[i] = new JTextField(content.toString());
			jpBrowser.add(jlBrowser[i]);
			i++;
			content.delete(0, content.length());
		}
		
		return jpBrowser;
	}

<<<<<<< HEAD
	public Result(SelectMenuEvt sme, SelectMenu sm) {
		super(sm, "결과 출력",true);
		this.sme = sme;
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		JButton jbConfirm = new JButton("확인"); 
		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		File reportFile = new File(sme.getFilePath());
		
		pnNo.add(new JButton("파일명"));
		pnNo.add(new JLabel(reportFile.getName()));
		pnNo.add(new JButton("생성된 날짜"));
		pnNo.add(new JLabel(sme.getLogTxtCreationDate()));
		pnCe.add(new JButton("1. 최다 사용 key의 이름과 횟수"));
		pnCe.add(new JLabel(sme.getMostFrequentKey()+" : "+sme.getMapKey().get(sme.getMostFrequentKey())+"번"));
		pnCe.add(new JButton("2. 브라우저별 접속횟수, 비율"));
		pnCe.add(browserInfo()); 
		pnCe.add(new JButton("3. 서비스 성공(200) 실패(404) 횟수"));
		pnCe.add(new JLabel("성공(200) : "+sme.getCode200()+", 실패(404) : "+sme.getCode404()));
		pnCe.add(new JButton("4. 요청이 가장 많은 시간"));
		pnCe.add(new JLabel(sme.getMostFrequentHour()+"시"));
		pnCe.add(new JButton("5. 비정상 요청 횟수와 비율"));
		pnCe.add(new JLabel(sme.getCode403()+" ("
=======
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
		bgr.add(new JTextField(reportFile.getName())).setBounds(125, 10, 100, 30);
		bgr.add(new JLabel("생성된 날짜")).setBounds(300, 10, 100, 30);
		bgr.add(new JTextField(sme.getLogTxtCreationDate())).setBounds(380, 10, 150, 20);
		bgr.add(new JLabel("1. 최다 사용 key의 이름과 횟수")).setBounds(80, 50, 200, 50);
		bgr.add(new JTextField(sme.getMostFrequentKey()+" : "+sme.getMapKey().get(sme.getMostFrequentKey())+"번")).setBounds(380, 60, 200, 30);
		bgr.add(new JLabel("2. 브라우저별 접속횟수, 비율")).setBounds(80, 130, 200, 50);
		bgr.add(browserInfo()).setBounds(380, 100, 200, 150); 
		bgr.add(new JLabel("3. 서비스 성공(200) 실패(404) 횟수")).setBounds(80, 230, 200, 50);
		bgr.add(new JTextField("성공(200) : "+sme.getCode200()+", 실패(404) : "+sme.getCode404())).setBounds(380, 230, 200, 50);
		bgr.add(new JLabel("4. 요청이 가장 많은 시간")).setBounds(80, 310, 200, 30);
		bgr.add(new JTextArea(sme.getMostFrequentHour()+"시")).setBounds(380	, 310, 200,30);
		bgr.add(new JLabel("5. 비정상 요청 횟수와 비율")).setBounds(80, 360, 200, 30);
<<<<<<< HEAD
		bgr.add(new JLabel(sme.getCode403()+" ("
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
=======
		bgr.add(new JTextField(sme.getCode403()+" ("
>>>>>>> parent of cdbdae5... 理쒖쥌
				+ String.format("%4.2f", 
						sme.getCode403()/(double)sme.getRequestNum()*100)
				+"%)"));
		if (sme.getStart() == 0 && sme.getEnd() == 0) {
<<<<<<< HEAD
<<<<<<< HEAD
			pnCe.add(new JButton("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" 라인 가장 빈도수가 높은 key와 횟수"));
			pnCe.add(new JLabel(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"번"));
		} else {
			pnCe.add(new JButton("6. "+sme.getStart()+"~"+(sme.getEnd() > sme.getRequestNum() ? sme.getRequestNum() : sme.getEnd())
					+" 라인 가장 빈도수가 높은 key와 횟수"));
			pnCe.add(new JLabel(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
=======
			bgr.add(new JLabel("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" 라인 가장 고빈도 key, 횟수")).setBounds(80, 410, 250, 30);
			bgr.add(new JLabel(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"번")).setBounds(380, 410, 200, 30);
=======
			bgr.add(new JTextField("6. "+(sme.getStart()+1)+"~"+sme.getRequestNum()+" 라인 가장 빈도수가 높은 key와 횟수"));
			bgr.add(new JTextField(sme.getMostFrequentKey()+" : "
					+sme.getMapKey().get(sme.getMostFrequentKey())+"번"));
>>>>>>> parent of cdbdae5... 理쒖쥌
		} else {
			bgr.add(new JTextField("6. "+sme.getStart()+"~"+(sme.getEnd() > sme.getRequestNum() ? sme.getRequestNum() : sme.getEnd())
					+" 라인 가장 빈도수가 높은 key와 횟수"));
<<<<<<< HEAD
			bgr.add(new JLabel(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
>>>>>>> parent of a23d0c3... Merge branch 'master' into temp
=======
			bgr.add(new JTextField(sme.getMostFrequentKeyBetweenStartAndEnd()+" : "
>>>>>>> parent of cdbdae5... 理쒖쥌
					+sme.getMapKeyBetweenStartAndEnd().get(sme.getMostFrequentKeyBetweenStartAndEnd())+"번"));
		}
		pnNo.setLayout(new GridLayout(1, 4));
		pnCe.setLayout(new GridLayout(6, 2));
		setLayout(new BorderLayout());
		
<<<<<<< HEAD
		add("North",pnNo);
		add("Center",pnCe);
		add("South",jbConfirm);
=======
		bgr.add(jbConfirm).setBounds(270, 410, 60, 30);
//		add("North",pnNo);
//		add("Center",pnCe);
//		add("South",jbConfirm);
>>>>>>> parent of cdbdae5... 理쒖쥌
		
		ResultEvt r = new ResultEvt(this);
		jbConfirm.addActionListener(r);
		
<<<<<<< HEAD
=======
		setContentPane(bgr);
>>>>>>> parent of cdbdae5... 理쒖쥌
		setBounds(400, 300, 650, 500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}