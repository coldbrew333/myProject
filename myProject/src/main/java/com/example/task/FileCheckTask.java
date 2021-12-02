package com.example.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.domain.AttachVO;
import com.example.mapper.AttachMapper;

@Component
public class FileCheckTask {
	
	@Autowired
	private AttachMapper attachMapper;
	

	@Scheduled(cron = "0 0 2 * * ?")  // 매일 오전 2시 (cron = "0 0 2 * * ?")
	public void checkFiles() throws Exception {
		System.out.println("====================================");
		System.out.println("checkFiles() task run.......");
		System.out.println("====================================");
		
		// 어제 날짜 구하기
		Calendar cal = Calendar.getInstance(); // 현재날짜시간 정보 가진 Calendar 객체 가져오기
		cal.add(Calendar.DATE, -3);  // 어제날짜 = 현재날짜시간에서 하루 빼기
		
		// 어제날짜 "년/월/일" 문자열 구하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = sdf.format(cal.getTime());  // "2021/09/03"
		
		// DB에서 "년/월/일" 경로에 해당하는 첨부파일 정보 리스트 가져오기
		List<AttachVO> attachList = attachMapper.getAttachesByUploadpath(strDate);
		
		
		String path = "/Users/jiyul/Desktop/workspace/upload/" + strDate;  // "/Users/jiyul/Desktop/workspace/upload/2021/09/03"
		System.out.println("path : " + path);
		
		// 어제날짜 경로를 파일객체로 준비
		File dir = new File(path);
		// 어제날짜 경로 폴더안에 있는 파일목록을 파일 배열로 가져오기
		File[] files = dir.listFiles();
		// 배열을 리스트로 변환하기
		List<File> fileList = Arrays.asList(files);
		
		//======================================================
		// attachList와 fileList를 비교.
		// 1) String 요소를 가지는 List 두개로 contains() 포함여부로 비교하기
		
		List<String> dbFilenameList = new ArrayList<String>();
		for (AttachVO attachVO : attachList) {
			dbFilenameList.add(attachVO.getUuid() + "_" + attachVO.getFilename());
			
			if (attachVO.getFiletype().equals("I")) { // 이미지 이면
				dbFilenameList.add("s_" + attachVO.getUuid() + "_" + attachVO.getFilename());
			}
		}
		System.out.println(dbFilenameList); // 테이블 기준으로 확인된 파일명 리스트
		
		
		List<String> filenameList = new ArrayList<String>();
		for (File file : fileList) {
			filenameList.add(file.getName());
		}
		System.out.println(filenameList); // 하드디스크에 존재하는 파일명 리스트
		
		//======================================================
		
		for (String filename : filenameList) {  // 실제 폴더에 있는 파일명
			// 실제 파일명이 DB에서 가져온 목록에 포함되어 있지 않으면
			if (dbFilenameList.contains(filename) == false) {
				File delFile = new File(path, filename); // 삭제할 파일객체 준비
				
				if (delFile.exists()) { // 해당 파일이 존재하면
					delFile.delete();  // 삭제하기
					
					// 삭제된 파일명 출력하기
					System.out.println(delFile.getName() + " 파일 삭제됨...");
				}
			}
		} // for
	} // checkFiles
	
	
	
	@Scheduled(cron = "0 0 2 * * ?")
	public void checkFiles2() throws Exception {
		System.out.println("====================================");
		System.out.println("checkFiles2() task run.......");
		System.out.println("====================================");
		
		// 어제 날짜 구하기
		Calendar cal = Calendar.getInstance(); // 현재날짜시간 정보 가진 Calendar 객체 가져오기
		cal.add(Calendar.DATE, -3);  // 어제날짜 = 현재날짜시간에서 하루 빼기
		
		// 어제날짜 "년/월/일" 문자열 구하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = sdf.format(cal.getTime());  // "2021/09/03"
		
		// DB에서 "년/월/일" 경로에 해당하는 첨부파일 정보 리스트 가져오기
		List<AttachVO> attachList = attachMapper.getAttachesByUploadpath(strDate);
		
		
		String path = "/Users/jiyul/Desktop/workspace/upload/" + strDate;  // "/Users/jiyul/Desktop/workspace/upload/2021/09/03"
		System.out.println("path : " + path);
		
		// 어제날짜 경로를 파일객체로 준비
		File dir = new File(path);
		// 어제날짜 경로 폴더안에 있는 파일목록을 파일 배열로 가져오기
		File[] files = dir.listFiles();
		// 배열을 리스트로 변환하기
		List<File> fileList = Arrays.asList(files);
		
		//======================================================
		// attachList와 fileList를 비교.
		// 2) 두 리스트의 파일명을 equals() 메소드로 비교하기
		aa:
		for (File file : fileList) { // 실제 파일
			for (AttachVO attachVO : attachList) { // DB테이블 파일정보
				String dbFilename = attachVO.getUuid() + "_" + attachVO.getFilename();
				
				if (file.getName().equals(dbFilename) == true) {
					continue aa;
				}
				
				// 썸네일 이미지 확인
				if (file.getName().equals("s_" + dbFilename) == true) {
					continue aa;
				}
			} // inner for
			
			
			// file 삭제
			if (file.exists()) {
				file.delete();
				
				System.out.println(file.getName() + " 파일 삭제됨...");
			}
		} // outer for
		
	} // checkFiles2
	
}






