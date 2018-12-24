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


/////////////// 12-22 Result JDialog구현, Event처리(정미) /////////////////////
// //////////// 12-24 Result 디자인 변경(영근), sle로부터 출력 결과 설정 //////
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
		super(sl, "결과 출력",true);
		this.sle = sle;
		
		// 결과를 출력할 결과창 구현
		//컴포넌트 만들기
		
		JPanel pnNo = new JPanel();
		JPanel pnCe= new JPanel();
		JButton jbConfirm = new JButton("확인"); 
		jbConfirm.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		File reportFile = new File(sle.getFilePath());
		
		reportFile.lastModified();
		pnNo.add(new JButton("파일명"));
		pnNo.add(new JLabel(reportFile.getName()));
		pnNo.add(new JButton("생성된 날짜"));
		// 날짜를 받아올 인스턴스 변수 필요(선의 구현 요청)
		pnNo.add(new JLabel(sle.getLogTxtCreationDate()));
		
		pnCe.add(new JButton("1.  최다 사용 key"));
		pnCe.add(new JTextArea(sle.getMostFrequentKey()));
		pnCe.add(new JButton("2. 브라우저별 접속횟수, 비율"));
		pnCe.add(browserInfo()); 
		pnCe.add(new JButton("3. 서비스 성공(200) 실패(404) 횟수"));
		pnCe.add(new JLabel("200 : "+sle.getCode200()+", 404 : "+sle.getCode404()));
		pnCe.add(new JButton("4. 요청이 가장 많은 시간"));
		pnCe.add(new JLabel(sle.getMostFrequentHour()+"시"));
		pnCe.add(new JButton("5. 비정상 요청 횟수와 비율"));
		pnCe.add(new JTextField(sle.getCode403()+" ("
				+ String.format("4.2f", 
						sle.getCode403()/(double)sle.getRequestNum()*100)
				+"%)"));
		pnCe.add(new JButton("6.  1000~1500 라인 가장 빈도수가 높은 key와 횟수"));
		try {
			pnCe.add(new JTextField("key "+sle.getMostFrequentKeyBetween1000And1500()+" : "
					+sle.getMapKeyBetween1000And1500().get(sle.getMostFrequentKeyBetween1000And1500())
					+"번"));
		} catch (NullPointerException e) {
			System.out.println("NullPointerException");
			System.out.println("에러처리 해야함");
		}
		
		pnNo.setLayout(new GridLayout(1, 4));
		pnCe.setLayout(new GridLayout(6, 2));
		setLayout(new BorderLayout());
		//이벤트발생
		
		
		//배치
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