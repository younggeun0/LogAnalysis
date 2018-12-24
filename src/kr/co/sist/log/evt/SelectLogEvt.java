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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.text.html.parser.Entity;

import kr.co.sist.log.view.Result;
import kr.co.sist.log.view.SelectLog;

public class SelectLogEvt implements ActionListener {

	private SelectLog sl;
	private String filePath;
	private String fName;
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
		mapKeyBetween1000And1500 = new HashMap<String, Integer>();
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
					calLogTxtCreationDate();
					calMostFrequentKey();
					calMostFrequentKeyBetween1000And1500();
					calMostFrequentHour();
					calBrowserShare();
					calCode403Share();

					try {
						new Result(this, sl);
					} catch (NullPointerException npe) {
						System.err.println(npe.getMessage());
					}
				}

			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}

		if (e.getSource() == sl.getJbReport()) {
			if (reportFlag == true) {

				try {
					mkLogReport();
					JOptionPane.showMessageDialog(sl, "report file created!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(sl, "Please Press 'View' before Report");
			}
		}
	}
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
			bw.write(printReport());
			bw.flush();
		}finally{
			if(bw!=null) {bw.close();}
		}
	}
	
	public void calLogTxtCreationDate() {
		try {
			BasicFileAttributes attrs = Files.readAttributes(new File(filePath).toPath(), BasicFileAttributes.class);
			FileTime creationTime = attrs.creationTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss hh:mm");
			logTxtCreationDate = sdf.format(new Date(creationTime.toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String printReport() {
//		private SelectLog sl;
//		private String filePath;
//		private String fName;
//		private String logTxtCreationDate;
//		private Map<String, Integer> mapKey;
//		private Map<String, Integer> mapKeyBetween1000And1500;
//		private Map<String, Integer> mapBrowser;
//		private String[] browser = { "opera", "ie", "firefox", "Chrome", "Safari" };
//		private int[] browserCnt = new int[browser.length];
//		private Map<String, Integer> mapHour;
//		private int code200, code404, code403;
//		private int requestNum;
//		private String code403Share;
//		private Map<String, String> mapBrowserShare;
//		private String mostFrequentHour;
//		private String mostFrequentKey;
//		private String mostFrequentKeyBetween1000And1500;
//		private boolean reportFlag;
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String s = sdf.format(d);
		Set<String> set = mapBrowser.keySet();
		Iterator<String> it = set.iterator();
		String key = "";
		sb.append("-------------------------------------------------------------\n");
		sb.append("파일명(").append(fName).append(") log (").append(s).append(")\n");
		sb.append("-------------------------------------------------------------\n");
		sb.append("1. 최다 사용키: ").append(mostFrequentKey).append(" ").append(mapKey.get(mostFrequentKey)).append("회\n");
		sb.append("2. 브라우저별 접속 횟수와 비율 : \n");
		while(it.hasNext()) {
			key = it.next();
			sb.append("\t").append(key).append(" :").append(mapBrowser.get(key)).append("번(")
			.append(mapBrowserShare.get(key)).append("%)\n");
		}
		sb.append("3. 서비스를 성공적수행(200) 횟수, 실패(404)횟수: \n")
		.append("\t200: ").append(code200).append("번 404 :").append(code404).append("번\n");
		sb.append("4. 요청이 가장 많은 시간: [").append(mostFrequentHour).append("시]\n");
		sb.append("5.비정상적인 요청(403)이 발생한 횟수, 비율: ").append(code403).append("번(")
		.append(code403Share).append("%)\n");
		sb.append("6. 1000~1500번째 정보 최다 사용 키의 이름과 횟수: \n")
		.append("\t").append("키명: ").append(mostFrequentKeyBetween1000And1500);
		
		
		return sb.toString();
	}

	public void calMostFrequentKey() {
		int maxValue = (Collections.max(mapKey.values())); //
		for (Map.Entry<String, Integer> entry : mapKey.entrySet()) {
			if (entry.getValue() == maxValue) {
				mostFrequentKey = entry.getKey();
			}
		} 

	}

	public void calMostFrequentKeyBetween1000And1500() {
		int Value = (Collections.max(mapKeyBetween1000And1500.values())); //
		for (Map.Entry<String, Integer> entry : mapKeyBetween1000And1500.entrySet()) {
			if (entry.getValue() == Value) {
				mostFrequentKeyBetween1000And1500 = entry.getKey();
			}
		} 
				
	}

	public void calMostFrequentHour() {
		Set<String> setHour = mapHour.keySet();
		Iterator<String> it = setHour.iterator(); 
		
		int max = 0;
		String hour = "";
		
		while(it.hasNext()) {
			hour = it.next();
			if (mapHour.get(hour) > max) {
				mostFrequentHour = hour;
				max = mapHour.get(hour);
			}
		}
	}

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

	public void calCode403Share() {
		code403Share = String.format("%3.2f", (code403 / (double) requestNum) * 100);
	}

	public void selectLog() {
		FileDialog fd = new FileDialog(sl, "log 파일 선택", FileDialog.LOAD);
		fd.setVisible(true);

		String dirPath = fd.getDirectory();
		fName = fd.getFile();
		filePath = dirPath + fName;
	}

	public void readLog() throws IOException, FileNotFoundException {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));

			String temp = "";
			while ((temp = br.readLine()) != null) { 

				requestNum++;
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
		String key = null;
		if (temp.contains("key")) {
			key = temp.substring(temp.indexOf("=") + 1, temp.indexOf("&"));
			mapKey.put(key, mapKey.get(key) != null ? mapKey.get(key) + 1 : 1);
			if (requestNum >= 1000 && requestNum <= 1500) {
				mapKeyBetween1000And1500.put(key,
						mapKeyBetween1000And1500.get(key) != null ? mapKeyBetween1000And1500.get(key) + 1 : 1);
			}
		}
	}

	public void countBrowser(String temp) {
		int count = 0;
		for (int i = 0; i < browser.length; i++) {
			if (temp.contains(browser[i])) {
				browserCnt[i]++;
			}
			mapBrowser.put(browser[i], browserCnt[i]);
		} 
	}

	public void countHttpStatusCode(String temp) {

		int serviceCode  = Integer.parseInt(temp.substring(temp.indexOf("[")+1, temp.indexOf("]")));
				
		if(serviceCode ==200) {
			code200++;
		}else if(serviceCode==404){
			code404++;
		}else if(serviceCode ==403){
			code403++;
		}

	}


	public void countRequestHour(String temp) {
		String hour = temp.substring(
				temp.lastIndexOf("[")+1, temp.lastIndexOf("]"))
				.substring(11, 13);

		mapHour.put(hour, mapHour.get(hour) != null ? mapHour.get(hour)+1 : 1);
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
