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

	// 1~6�� ó���� ������ instance ������ �����ؾ� ��
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
					// readLog�� �о���� log�� ������ ����, instance������ ����
					getLogTxtCreationDate();
					calMostFrequentKey();
					calMostFrequentKeyBetween1000And1500();
					calMostFrequentHour();
					calBrowserShare();
					calCode403Share();

					// ���â
					new Result(this, sl);
				}

			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}

		// jbView�� �ѹ� �̻� ���ȴٸ� JOptionPane.showMessageDialog�� �ٿ� ��� ���
		if (e.getSource() == sl.getJbReport()) {
			// jbView�� �ѹ��̻� ������ �� ����ǵ��� ����(boolean flag�� ����)
			if (reportFlag == true) {
				// "report ���"�� Component�� ��ü�ؾ� ��, Component�� ��ȯ�ϴ� method ���� ��

				// ���� ��� FileDialog ����
				try {
					mkLogReport();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(sl, "View�� ���� �����Ͽ� �ּ���.");
			}
		}
	}
	
	///////////////// 12-24 getLogTxtCreationDate method ���� ///////////////////////////////////
	///////////////// Result�� ���Ǳ����� Log���� ������¥�� ���� �����ϴ� method /////////////
	public void getLogTxtCreationDate() {
		// �о���� log������ ���� ��¥�� ���ϴ� method
		try {
			BasicFileAttributes attrs = Files.readAttributes(new File(filePath).toPath(), BasicFileAttributes.class);
			FileTime creationTime = attrs.creationTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss hh:mm");
			logTxtCreationDate = sdf.format(new Date(creationTime.toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	///////////////// 12-24 getLogTxtCreationDate method ���� �� ///////////////////////////////////
	

	public void mkLogReport() throws IOException {
		
		
	}

	public void calMostFrequentKey() {
		// ���� �󵵼� ���� key(mostFrequentKey)�� ���ϴ� method
	}

	public void calMostFrequentKeyBetween1000And1500() {
		// 1000~1500���ο� ���� �󵵼� ���� key(mostFrequentKey)�� ���ϴ� method
	}

	public void calMostFrequentHour() {
		// ���� �󵵼� ���� �ð�(mostFrequentHour) ���ϴ� method
	}

/////////////////////12.22 ���� �ڵ� �߰� (�������� �������ؼ� ��ȯ) ����//////////////////////////////
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
/////////////////////12.22 ���� �ڵ� �߰� (�������� �������ؼ� ��ȯ) ��//////////////////////////////

	public void calCode403Share() {
		code403Share = String.format("%3.2f", (code403 / (double) requestNum) * 100);
	}

	public void selectLog() {
		// �о���� log������ ��θ� �����ϴ� method
		FileDialog fd = new FileDialog(sl, "log ���� ����", FileDialog.LOAD);
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
				// ���õ� ������ ������ �� �پ� �о����
				// �о���̴� ������ ó���ϴ°� ���� method ���� ó���� ��
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
		// 1. �ִ� ��� Key�� �̸��� Ƚ���� ���ϴ� method,
		// mapKey�� instance�� ������ ä�쵵�� ����

		// 1000���� 1500�� ������ �� ����� ���� �����ؾ� �ϱ� ������ 
		// mapKeyBetween1000And1500�� ���� ���� �־���� ��.
		if (requestNum >= 1000 && requestNum <= 1500) {

		}
	}

//////////////////////12.22 ���� �߰� �ڵ�(������,ī���� mapBrowser�� �ֱ�) ���� ////////////
	private String[] browser = { "opera", "ie", "firefox", "Chrome", "Safari" };
	private int[] browserCnt = new int[browser.length];

	public void countBrowser(String temp) {
		// 2. �������� ���� Ƚ�� ���ϴ� method, ���� ���ϱ�(����)
		int count = 0;
		for (int i = 0; i < browser.length; i++) {
			if (temp.contains(browser[i])) {
				browserCnt[i]++;
			} // end for
			mapBrowser.put(browser[i], browserCnt[i]);
		} // end for
	}// countBrowser
/////////////////////12.22 ���� �߰� �ڵ�(������,ī���� mapBrowser�� �ֱ�) ��///////////////

	public void countHttpStatusCode(String temp) {
		// 3. ���񽺸� ���������� ������ Ƚ��, ����(404) Ƚ��
		// 6. ���������� ��û(403)�� �߻��� Ƚ�� ���ϴ� method, ���� ���ϱ�� calBrowserShare()�� ����
	}

	public void countRequestHour(String temp) {
		// 4. ��û �ð��� Ƚ���� ���ϴ� method, mapHour instance������ ���� �ִ� �޼ҵ� ����
		// 4-1. �ϼ��� mapHour ������ �̿�, mostFrequentHour�� ���ؾ� ��(calMostFrequentHour()����)
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
