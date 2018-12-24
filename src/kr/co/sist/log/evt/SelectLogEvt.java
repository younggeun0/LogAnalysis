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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import kr.co.sist.log.view.Result;
import kr.co.sist.log.view.SelectLog;

public class SelectLogEvt implements ActionListener {

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
			System.out.println(sb.toString());			
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
		
		
		return null;
	}

	public void calMostFrequentKey() {
		int maxValue = (Collections.max(mapKey.values())); //
		for (Map.Entry<String, Integer> entry : mapKey.entrySet()) {
			if (entry.getValue() == maxValue) {
			}
		} 

	}

	public void calMostFrequentKeyBetween1000And1500() {
	}

	public void calMostFrequentHour() {
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

			if (temp.indexOf("key") != -1) {
				key = temp.substring(temp.indexOf("=") + 1, temp.indexOf("&"));
				mapKey.put(key, mapKey.get(key) != null ? mapKey.get(key) + 1 : 1);
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
		
	}


	public void countRequestHour(String temp) {
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
