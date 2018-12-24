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

	// 1~6 泥섎━�븳 �궡�슜�쓣 instance 蹂��닔�뿉 ���옣
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
				// readLog濡� log�뙆�씪�쓣 �씫�뼱�뱾�씤�떎.
				readLog();

				if (requestNum != 0) {
					// �씫�뼱�뱾�씤 �궡�슜�쓣 媛�怨�, instance蹂��닔�뿉 ���옣
					calLogTxtCreationDate();
					calMostFrequentKey();
					calMostFrequentKeyBetween1000And1500();
					calMostFrequentHour();
					calBrowserShare();
					calCode403Share();

					try {
						new Result(this, sl);
					} catch (NullPointerException npe) {
						System.out.println("�뿉�윭諛쒖깮");
						npe.printStackTrace();
					}
				}

			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}

		// jbView媛� �븳踰� �씠�긽 �닃�졇�떎硫� �떎�뻾�릺�룄濡� 援ы쁽
		if (e.getSource() == sl.getJbReport()) {
			// reportFlag濡� View �떎�뻾�뿬遺�瑜� �뙋�떒 �썑 �떎�뻾
			if (reportFlag == true) {

				try {
					mkLogReport();
					JOptionPane.showMessageDialog(sl, "�뙆�씪 �깮�꽦 �꽦怨�!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(sl, "View瑜� 癒쇱� �닔�뻾�빐二쇱꽭�슂.");
			}
		}
	}
////////////////////////////////12.24 Report �뤃�뜑 �깮�꽦, report_�쁽�옱�궇吏�.dat �뙆�씪�깮�꽦 �떆�옉(�꽑�쓽)///////////////////////////////////////////////////
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
////////////////////////////////12.24 Report �뤃�뜑 �깮�꽦 �걹///////////////////////////////////////////////////
	
///////////////// 12-24 getLogTxtCreationDate method 援ы쁽(�쁺洹�) ///////////////////////////////////
///////////////// Result�뿉 �궗�슜�릺湲� �쐞�븳 Log�뙆�씪 �깮�꽦 �궇吏쒕�� 援ы빐 ���옣�븯�뒗 method /////////////
	public void calLogTxtCreationDate() {
		// �씫�뼱�뱾�씤 log�뙆�씪�쓽 �깮�꽦 �궇吏쒕�� 援ы븯�뒗 method
		try {
			BasicFileAttributes attrs = Files.readAttributes(new File(filePath).toPath(), BasicFileAttributes.class);
			FileTime creationTime = attrs.creationTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss hh:mm");
			logTxtCreationDate = sdf.format(new Date(creationTime.toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
///////////////// 12-24 getLogTxtCreationDate method 援ы쁽 �걹 ///////////////////////////////////
	

	public void calMostFrequentKey() {
		// 媛��옣 鍮덈룄�닔 �넂�� key(mostFrequentKey)瑜� 援ы븯�뒗 method
		int maxValue = (Collections.max(mapKey.values())); //
		for (Map.Entry<String, Integer> entry : mapKey.entrySet()) {
			if (entry.getValue() == maxValue) {
//				System.out.println("理쒕떎 �궗�슜�궎  : " + entry.getKey() + "\n�슏�닔 : " + entry.getValue());
			} // end if
		} // end for

	}
//calMostFrequentKey

	public void calMostFrequentKeyBetween1000And1500() {
		// 1000~1500�씪�씤�뿉 媛��옣 鍮덈룄�닔 �넂�� key(mostFrequentKey)瑜� 援ы븯�뒗 method)
	}

	public void calMostFrequentHour() {
		// 媛��옣 �슂泥� 鍮덈룄�닔媛� �넂�� �떆媛�(mostFrequentHour)�쓣 援ы븯�뒗 method
	}

/////////////////////12.22 釉뚮씪�슦�� 鍮꾩쑉 援ы빐 諛섑솚 援ы쁽 �떆�옉 (�꽑�쓽)//////////////////////////////
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
/////////////////////12.22 釉뚮씪�슦�� 鍮꾩쑉 援ы빐 諛섑솚 援ы쁽 �걹//////////////////////////////

	public void calCode403Share() {
		code403Share = String.format("%3.2f", (code403 / (double) requestNum) * 100);
	}

	public void selectLog() {
		// �씫�뼱�뱾�씤 log�뙆�씪�쓽 寃쎈줈瑜� ���옣�븯�뒗 method
		FileDialog fd = new FileDialog(sl, "log �뙆�씪 �꽑�깮", FileDialog.LOAD);
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
			while ((temp = br.readLine()) != null) { // �꽑�깮�맂 �뙆�씪�쓽 �궡�슜�쓣 �븳以꾩뵫 �씫�쓬

				requestNum++;
				// �씫�뼱�뱾�씠�뒗 �궡�슜�쓣 泥섎━�븯�뒗嫄� �뵲濡� method�뱾濡� 泥섎━
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
		// 1. 理쒕떎 �궗�슜 Key�쓽 �씠由꾧낵 �슏�닔瑜� 援ы븯�뒗 method,
		// mapKey瑜� instance�쓽 �궡�슜�쓣 梨꾩슦�룄濡� 援ы쁽
		String key = null;
		if (temp.contains("key")) {

			if (temp.indexOf("key") != -1) {
				key = temp.substring(temp.indexOf("=") + 1, temp.indexOf("&"));
				mapKey.put(key, mapKey.get(key) != null ? mapKey.get(key) + 1 : 1);
			} // end if
		} // end if
		// 1000�뿉�꽌 1500踰� �궗�씠�씪 �븣 寃곌낵留� �뵲濡� ���옣�빐�빞 �븯湲� �븣臾몄뿉
		// mapKeyBetween1000And1500�뿉 �뵲濡� 媛믪쓣 �꽔�뼱以섏빞 �븿.
	}// countKey

//////////////////////12.22 釉뚮씪�슦�� 移댁슫�꽣, mapBrowser�뿉 ���옣 援ы쁽 �떆�옉 (�꽑�쓽)////////////
	public void countBrowser(String temp) {
		// 2. 釉뚮씪�슦��蹂� �젒�냽 �슏�닔瑜� 援ы븯�뒗 method
		int count = 0;
		for (int i = 0; i < browser.length; i++) {
			if (temp.contains(browser[i])) {
				browserCnt[i]++;
			} // end for
			mapBrowser.put(browser[i], browserCnt[i]);
		} // end for
	}// countBrowser
//////////////////////12.22 釉뚮씪�슦�� 移댁슫�꽣, mapBrowser�뿉 ���옣 援ы쁽 �걹 (�꽑�쓽)////////////

	public void countHttpStatusCode(String temp) {
		// 3. �꽌鍮꾩뒪瑜� �꽦怨듭쟻�쑝濡� �닔�뻾�븳 �슏�닔, �떎�뙣(404) �슏�닔
		// 6. 鍮꾩젙�긽�쟻�씤 �슂泥�(403)�씠 諛쒖깮�븳 �슏�닔 援ы븯�뒗 method, 鍮꾩쑉 援ы븯湲곕뒗 calBrowserShare()�뿉 援ы쁽

	}// countHttpStatusCod


	public void countRequestHour(String temp) {
		// 4. �슂泥� �떆媛꾨퀎 �슏�닔瑜� 援ы븯�뒗 method, mapHour�뿉 <�떆媛�,cnt>瑜� ���옣
		// 4-1.�셿�꽦�븳 mapHour蹂��닔瑜� �씠�슜, mostFrequentHour瑜� 援ы빐�빞 �븿(calMostFrequentHour()援ы쁽)
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
