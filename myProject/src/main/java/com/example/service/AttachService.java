package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.mapper.AttachMapper;

@Service
public class AttachService {
	
	@Autowired
	private AttachMapper attachMapper;
	
	public List<AttachVO> getAttachesByBno(int bno) {
		return attachMapper.getAttachesByBno(bno);
	}
	
	public List<AttachVO> getAttachesByUuids(List<String> uuidList) {
		return attachMapper.getAttachesByUuids(uuidList);
	}
	
	/* 페이징으로 글의 이미지 파일(type = 'I') 가져오기 */
	public List<AttachVO> getBoardsAttach(Criteria cri) {
		
		int startRow = (cri.getPageNum() - 1) * cri.getAmount();
		cri.setStartRow(startRow); // 시작행번호 설정
		
		return attachMapper.getBoardsWithAttachWithPaging(cri);
	}
	
	/* 페이징으로 글의 이미지 파일(type = 'I') 전체 개수 가져오기 */
	public int getTotalBoardsAttach() {
		return attachMapper.getTotalBoardsAttach();
	}
	
}










