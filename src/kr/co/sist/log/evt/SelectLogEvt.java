package kr.co.sist.log.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

	// 1~6 처리한 내용을 instance 변수에 저장
	private SelectLog sl;
	private String filePath;
	private String logTxtCreationDate;
	private Map<String, Integer> mapKey;
	private Map<String, Integer> mapKeyBetween1000And1500;
	private Map<String, Integer> mapBrowser;
	private String[] browser = { "opera", "ie", "firefox", "Chrome", "Safari" };
	private int[] browserCnt = new int[browser.length];
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
				// readLog로 log파일을 읽어들인다.
				readLog();

				if (requestNum != 0) {
					// 읽어들인 내용을 가공, instance변수에 저장
					calLogTxtCreationDate();
					calMostFrequentKey();
					calMostFrequentKeyBetween1000And1500();
					calMostFrequentHour();
					calBrowserShare();
					calCode403Share();

					try {
						new Result(this, sl);
					} catch (NullPointerException npe) {
						System.out.println("에러발생");
						npe.printStackTrace();
					}
				}

			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}

		// jbView가 한번 이상 눌렸다면 실행되도록 구현
		if (e.getSource() == sl.getJbReport()) {
			// reportFlag로 View 실행여부를 판단 후 실행
			if (reportFlag == true) {

				try {
					mkLogReport();
					JOptionPane.showMessageDialog(sl, "파일 생성 성공!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(sl, "View를 먼저 수행해주세요.");
			}
		}
	}
////////////////////////////////12.24 Report 폴더 생성, report_현재날짜.dat 파일생성 시작(선의)///////////////////////////////////////////////////
	public void mkLogReport() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d= new Date();
		String s =sdf.format(d);
		StringBuilder sb = new StringBuilder();
		sb.append("report_").append(s).append(".dat");
		File file = new File("C:/dev/Report/");		
		file.mkdirs();
		
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter("C:/dev/Report/"+sb.toString()));
			bw.write("test");
			bw.flush();
			System.out.println(sb.toString());			
		}finally{
			if(bw!=null) {bw.close();}
		}//end finally
	}
////////////////////////////////12.24 Report 폴더 생성 끝///////////////////////////////////////////////////
	
///////////////// 12-24 getLogTxtCreationDate method 구현(영근) ///////////////////////////////////
///////////////// Result에 사용되기 위한 Log파일 생성 날짜를 구해 저장하는 method /////////////
	public void calLogTxtCreationDate() {
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
	

	public void calMostFrequentKey() {
		// 가장 빈도수 높은 key(mostFrequentKey)를 구하는 method
	}

	public void calMostFrequentKeyBetween1000And1500() {
		// 1000~1500라인에 가장 빈도수 높은 key(mostFrequentKey)를 구하는 method)
	}

	public void calMostFrequentHour() {
		// 가장 요청 빈도수가 높은 시간(mostFrequentHour)을 구하는 method
	}

/////////////////////12.22 브라우저 비율 구해 반환 구현 시작 (선의)//////////////////////////////
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
/////////////////////12.22 브라우저 비율 구해 반환 구현 끝//////////////////////////////

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
			while ((temp = br.readLine()) != null) { // 선택된 파일의 내용을 한줄씩 읽음

				requestNum++;
				// 읽어들이는 내용을 처리하는건 따로 method들로 처리
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
		// 1. 최다 사용 Key의 이름과 횟수를 구하는 method
		// mapKey의 key값, cnt값 저장

		// 1000~1500번 사이일 때 결과만 따로 저장해야 함
		// mapKeyBetween1000And1500 key값, cnt값 저장
		if (requestNum >= 1000 && requestNum <= 1500) {

		}
	}

//////////////////////12.22 브라우저 카운터, mapBrowser에 저장 구현 시작 (선의)////////////
	public void countBrowser(String temp) {
		// 2. 브라우저별 접속 횟수를 구하는 method
		int count = 0;
		for (int i = 0; i < browser.length; i++) {
			if (temp.contains(browser[i])) {
				browserCnt[i]++;
			} // end for
			mapBrowser.put(browser[i], browserCnt[i]);
		} // end for
	}// countBrowser
//////////////////////12.22 브라우저 카운터, mapBrowser에 저장 구현 끝 (선의)////////////

	public void countHttpStatusCode(String temp) {
		// 3과 5번 서비스를 성공수행(200)한 횟수, 실패 횟수(404), 비정상을 누적합시킴(code200, code404, code403)
		// 비율은 calCode403Share()에 구현
//		if() {
//			
//		}
		
	}

	public void countRequestHour(String temp) {
		// 4. 요청 시간별 횟수를 구하는 method, mapHour에 <시간,cnt>를 저장
		// 4-1.완성한 mapHour변수를 이용, mostFrequentHour를 구해야 함(calMostFrequentHour()구현)
		String hour = temp.substring(
				temp.lastIndexOf("[")+1, temp.lastIndexOf("]"))
				.substring(11, 13);
		
		mapHour.put(hour, 0);
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

	public String getLogTxtCreationDate() {
		return logTxtCreationDate;
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
