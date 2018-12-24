package kr.co.sist.log.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import kr.co.sist.log.view.Result;
import kr.co.sist.log.view.SelectLog;

public class SelectLogEvt implements ActionListener {

	// 1~6을 처리한 내용을 instance 변수에 저장해야 함
	private SelectLog sl;
	private String filePath;
	private String logTxtCreationDate;
	private Map<String, Integer> mapKey;
	private Map<String, Integer> mapKeyBetween1000And1500;
	private Map<String, Integer> mapBrowser;
	private Map<String, Integer> mapHour;
	private int code200, code404, code403;
	private int requestNum;
	private String code403Share;
	private Map<String, String> mapBrowserShare;
	private String mostFrequentHour;
	private String mostFrequentKey;
	private String mostFrequentKeyBetween1000And1500;
	private boolean reportFlag;

	public SelectLogEvt(SelectLog sl) {
		this.sl = sl;
		mapKey = new HashMap<String, Integer>();
		mapBrowser = new HashMap<String, Integer>();
		mapHour = new HashMap<String, Integer>();
		mapBrowserShare = new HashMap<String, String>();
		reportFlag = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sl.getJbView()) {

			selectLog();

			try {
				readLog();

				if (requestNum != 0) {
					// readLog로 읽어들인 log의 내용을 가공, instance변수에 저장
					getLogTxtCreationDate();
					calMostFrequentKey();
					calMostFrequentKeyBetween1000And1500();
					calMostFrequentHour();
					calBrowserShare();
					calCode403Share();

					// 결과창
					new Result(this, sl);
				}

			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}

		// jbView가 한번 이상 눌렸다면 JOptionPane.showMessageDialog에 붙여 결과 출력
		if (e.getSource() == sl.getJbReport()) {
			// jbView가 한번이상 눌렸을 때 수행되도록 구현(boolean flag로 구현)
			if (reportFlag == true) {
				// "report 출력"을 Component로 대체해야 함, Component를 반환하는 method 만들 것

				// 파일 출력 FileDialog 구현
				try {
					mkLogReport();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(sl, "View를 먼저 수행하여 주세요.");
			}
		}
	}
	
	///////////////// 12-24 getLogTxtCreationDate method 구현 ///////////////////////////////////
	///////////////// Result에 사용되기위한 Log파일 생성날짜를 구해 저장하는 method /////////////
	public void getLogTxtCreationDate() {
		// 읽어들인 log파일의 생성 날짜를 구하는 method
		try {
			BasicFileAttributes attrs = Files.readAttributes(new File(filePath).toPath(), BasicFileAttributes.class);
			FileTime creationTime = attrs.creationTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss hh:mm");
			logTxtCreationDate = sdf.format(new Date(creationTime.toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	///////////////// 12-24 getLogTxtCreationDate method 구현 끝 ///////////////////////////////////
	

	public void mkLogReport() throws IOException {
		
		
	}

	public void calMostFrequentKey() {
		// 가장 빈도수 높은 key(mostFrequentKey)를 구하는 method
	}

	public void calMostFrequentKeyBetween1000And1500() {
		// 1000~1500라인에 가장 빈도수 높은 key(mostFrequentKey)를 구하는 method
	}

	public void calMostFrequentHour() {
		// 가장 빈도수 높은 시간(mostFrequentHour) 구하는 method
	}

/////////////////////12.22 선의 코드 추가 (브라우저의 비율구해서 반환) 시작//////////////////////////////
	public void calBrowserShare() {
		ArrayList<String> al = new ArrayList<String>();
		Set<String> set = mapBrowser.keySet();
		Iterator<String> ita = set.iterator();
		Iterator<String> ita2 = set.iterator();

		for (int i = 0; i < browser.length; i++) {
			mapBrowserShare.put(ita2.next(),
					String.format("%4.2f", ((mapBrowser.get(ita.next()) / (double) requestNum) * 100)));
		}
	}
/////////////////////12.22 선의 코드 추가 (브라우저의 비율구해서 반환) 끝//////////////////////////////

	public void calCode403Share() {
		code403Share = String.format("%3.2f", (code403 / (double) requestNum) * 100);
	}

	public void selectLog() {
		// 읽어들인 log파일의 경로를 저장하는 method
		FileDialog fd = new FileDialog(sl, "log 파일 선택", FileDialog.LOAD);
		fd.setVisible(true);

		String dirPath = fd.getDirectory();
		String fName = fd.getFile();
		filePath = dirPath + fName;
	}

	public void readLog() throws IOException, FileNotFoundException {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));

			String temp = "";
			while ((temp = br.readLine()) != null) {

				requestNum++;
				// 선택된 파일의 내용을 한 줄씩 읽어들임
				// 읽어들이는 내용을 처리하는건 따로 method 만들어서 처리할 것
				countKey(temp);
				countBrowser(temp);
				countHttpStatusCode(temp);
				countRequestHour(temp);
			}

			reportFlag = true;
		} finally {
			if (br != null)
				br.close();
		}
	}

	public void countKey(String temp) {
		// 1. 최다 사용 Key의 이름과 횟수를 구하는 method,
		// mapKey를 instance의 내용을 채우도록 구현

		// 1000에서 1500번 사이일 때 결과만 따로 저장해야 하기 때문에 
		// mapKeyBetween1000And1500에 따로 값을 넣어줘야 함.
		if (requestNum >= 1000 && requestNum <= 1500) {

		}
	}

//////////////////////12.22 선의 추가 코드(브라우저,카운터 mapBrowser에 넣기) 시작 ////////////
	private String[] browser = { "opera", "ie", "firefox", "Chrome", "Safari" };
	private int[] browserCnt = new int[browser.length];

	public void countBrowser(String temp) {
		// 2. 브라우저별 접속 횟수 구하는 method, 비율 구하기(아직)
		int count = 0;
		for (int i = 0; i < browser.length; i++) {
			if (temp.contains(browser[i])) {
				browserCnt[i]++;
			} // end for
			mapBrowser.put(browser[i], browserCnt[i]);
		} // end for
	}// countBrowser
/////////////////////12.22 선의 추가 코드(브라우저,카운터 mapBrowser에 넣기) 끝///////////////

	public void countHttpStatusCode(String temp) {
		// 3. 서비스를 성공적으로 수행한 횟수, 실패(404) 횟수
		// 6. 비정상적인 요청(403)이 발생한 횟수 구하는 method, 비율 구하기는 calBrowserShare()에 구현
	}

	public void countRequestHour(String temp) {
		// 4. 요청 시간별 횟수를 구하는 method, mapHour instance변수에 값을 넣는 메소드 구현
		// 4-1. 완성한 mapHour 변수를 이용, mostFrequentHour를 구해야 함(calMostFrequentHour()구현)
	}

	
	public Map<String, Integer> getMapKeyBetween1000And1500() {
		return mapKeyBetween1000And1500;
	}

	public String getMostFrequentHour() {
		return mostFrequentHour;
	}

	public String getMostFrequentKeyBetween1000And1500() {
		return mostFrequentKeyBetween1000And1500;
	}

	public String[] getBrowser() {
		return browser;
	}

	public int[] getBrowserCnt() {
		return browserCnt;
	}

	public SelectLog getSl() {
		return sl;
	}

	public String getFilePath() {
		return filePath;
	}

	public Map<String, Integer> getMapKey() {
		return mapKey;
	}

	public Map<String, Integer> getMapBrowser() {
		return mapBrowser;
	}

	public Map<String, Integer> getMapHour() {
		return mapHour;
	}

	public int getCode200() {
		return code200;
	}

	public int getCode404() {
		return code404;
	}

	public int getCode403() {
		return code403;
	}

	public int getRequestNum() {
		return requestNum;
	}

	public String getCode403Share() {
		return code403Share;
	}

	public Map<String, String> getMapBrowserShare() {
		return mapBrowserShare;
	}

	public String getMostFrequentKey() {
		return mostFrequentKey;
	}

	public boolean isReportFlag() {
		return reportFlag;
	}
}
