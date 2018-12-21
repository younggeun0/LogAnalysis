package kr.co.sist.log.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import kr.co.sist.log.view.SelectDialog;

public class SelectDialogEvt implements ActionListener {

	private SelectDialog sd;
	private String logFilePath;
	// 1~6을 처리한 내용을 instance 변수에 저장해야 함
	// 저장 후 결과를 가공하여 JOptionPane.showMessageDialog에 붙여 출력
	
	public SelectDialogEvt(SelectDialog sd) {
		this.sd = sd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sd.getJbView()) {
			
			selectFile();
			
		}
		
		if (e.getSource() == sd.getJbReport()) {
			// jbView가 한번이상 눌렸을 때 수행되도록 구현
		}
	}
	
	public void selectFile() {
		
		FileDialog fd = new FileDialog(sd, "log 파일 선택", FileDialog.LOAD);
		
		fd.setVisible(true);
		
		String directoryPath = fd.getDirectory();
		String fileName = fd.getName();
		logFilePath = directoryPath+fileName;
		
	}
}
