package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.mapper.AttachMapper;
import com.example.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private AttachMapper attachMapper;
	
	
	public int register(BoardVO boardVO) {
		return boardMapper.insert(boardVO);
	}
	
	@Transactional
	public void registerBoardAndAttaches(BoardVO boardVO, List<AttachVO> attachList) {
		// attach 테이블의 bno 컬럼이 외래키로서
		// board 테이블의 num 컬럼을 참조하므로
		// board 레코드가 먼저 insert된 후 attach 레코드가 insert되야 함.
		boardMapper.insert(boardVO);
		
		if (attachList != null && attachList.size() > 0) {
			attachMapper.insertAttaches(attachList);
		}
	} // registerBoardAndAttaches
	
	public int nextNum() {
		return boardMapper.nextNum();
	}
	
	public int deleteAll() {
		return boardMapper.deleteAll();
	}
	
	@Transactional
	public void deleteBoardAndAttaches(int num) {
		attachMapper.deleteAttachesByBno(num);
		boardMapper.deleteBoardByNum(num);
	}
	
	public List<BoardVO> getBoards() {
		return boardMapper.getBoards();
	}
	
	// 페이징으로 글 가져오기
	public List<BoardVO> getBoards(Criteria cri) {
		// 시작 행번호 (MySQL의 LIMIT절의 시작행번호) 구하기
		
		// 한 페이지당 글개수(amount)가 10개씩일때
		// 1 페이지 -> 0
		// 2 페이지 -> 10
		// 3 페이지 -> 20
		// 4 페이지 -> 30
		// ...
		int startRow = (cri.getPageNum() - 1) * cri.getAmount();
		cri.setStartRow(startRow); // 시작행번호 설정
		
		return boardMapper.getBoardsWithPaging(cri);
	}

	public int getTotalCount() {
		return boardMapper.getTotalCount();
	}
	
	public int getTotalCountBySearch(Criteria cri) {
		return boardMapper.getTotalCountBySearch(cri);
	}
	
	public BoardVO getBoard(int num) {
		return boardMapper.getBoard(num);
	}

	
	
	public BoardVO getBoardAndAttaches(int num) {
//		BoardVO boardVO = boardMapper.getBoard(num);
//		List<AttachVO> attachList = attachMapper.getAttachesByBno(num);
//		boardVO.setAttachList(attachList);
//		return boardVO;
		
		return boardMapper.getBoardAndAttaches(num); // join 쿼리로 데이터 가져오기
	}
	
	
	
	public void updateReadcount(int num) {
		boardMapper.updateReadcount(num);
	}
	
	
	@Transactional
	public void updateBoardAndInsertAttachesAndDeleteAttaches(BoardVO boardVO, List<AttachVO> newAttachList, List<String> delUuidList) {
		
		if (newAttachList != null && newAttachList.size() > 0) {
			attachMapper.insertAttaches(newAttachList);
		}
		
		if (delUuidList != null && delUuidList.size() > 0) {
			attachMapper.deleteAttachesByUuids(delUuidList);
		}
		
		boardMapper.updateBoard(boardVO);
	} // updateBoardAndInsertAttachesAndDeleteAttaches
	
	
	@Transactional
	public void addReplyAndAddAttaches(BoardVO boardVO, List<AttachVO> attachList) {
		// 답글을 남길 대상글과 같은 글그룹 안에서
		//  대상글의 순번보다 큰 글들의 순번을 1씩 증가(UPDATE)
		boardMapper.updateReSeqPlusOne(boardVO.getReRef(), boardVO.getReSeq());
		
		// insert할 답글 re값으로 수정
		boardVO.setReLev(boardVO.getReLev() + 1);
		boardVO.setReSeq(boardVO.getReSeq() + 1);
		
		// 답글 insert 하기
		boardMapper.insert(boardVO);
		
		// 첨부파일 정보 insert하기
		if (attachList != null && attachList.size() > 0) {
			attachMapper.insertAttaches(attachList);
		}
	} // addReplyAndAddAttaches

	
	

}







