package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.domain.PageDTO;
import com.example.service.AttachService;
import com.example.service.BoardService;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private AttachService attachService;

	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("list 호출...");
		
		List<BoardVO> boardList = boardService.getBoards(cri);
		
		//int totalCount = boardService.getTotalCount(); // 전체 글개수
		int totalCount = boardService.getTotalCountBySearch(cri); // 검색이 적용된 전체 글개수
		
		PageDTO pageDTO = new PageDTO(totalCount, cri); // 페이지블록(Pagination) 화면 만들때 필요한 정보
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 -> requestScope로 옮겨줌
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageMaker", pageDTO);
		
		return "board/boardList";
	}
	@GetMapping("/detail")
	public String detail() {
		
		
		return "board/detail";
	}
	
	
	@GetMapping("/content")
	public String content(int num, @ModelAttribute("pageNum") String pageNum, Model model) {
		
		// 조회수 1 증가시키기
		boardService.updateReadcount(num);
		
		// 글 한개 가져오기
		//BoardVO boardVO = boardService.getBoard(num);
		// 게시글 한개와 게시글에 첨부된 첨부파일정보 여러개 가져오기
		BoardVO boardVO = boardService.getBoardAndAttaches(num);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("attachList", boardVO.getAttachList());
		model.addAttribute("attachCount", boardVO.getAttachList().size());
		//model.addAttribute("pageNum", pageNum);
		
		return "board/boardContent";
	} // content
	
	
	// 새로운 주글쓰기 폼 화면 요청
	@GetMapping("/write")
	public String write(@ModelAttribute("pageNum") String pageNum) {
		// 사용자 로그인 권한 확인
//		if (session.getAttribute("id") == null) { // 로그인 안한 사용자면
//			return "redirect:/member/login";
//		}
		
		return "board/boardWrite";
	} // get write
	
	
	
	// 년/월/일 형식의 폴더명 리턴하는 메소드
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String str = sdf.format(new Date());
		return str;
	}
	
	
	// 이미지 파일인지 여부 리턴하는 메소드
	private boolean checkImageType(File file) throws IOException {
		boolean isImage = false;
		
		String contentType = Files.probeContentType(file.toPath()); // "image/jpg"
		System.out.println("contentType : " + contentType);
		
		isImage = contentType.startsWith("image");
		return isImage;
	}
	
	
	
	// 첨부파일 업로드(썸네일 생성) 후 attacList 리턴하는 메소드
	private List<AttachVO> uploadFilesAndGetAttachList(List<MultipartFile> files, int bno) throws IllegalStateException, IOException {
		
		List<AttachVO> attachList = new ArrayList<AttachVO>();
		
		// 생성할 파일정보가 없으면 종료
		if (files == null || files.size() == 0) {
			System.out.println("업로드한 첨부파일 개수 : " + files.size());
			return attachList;
		}
		
		
		String uploadFolder = "/Users/jiyul/Desktop/workspace/upload";  // 업로드 기준경로
		
		File uploadPath = new File(uploadFolder, getFolder()); // /Users/jiyul/Desktop/workspace/upload/2021/08/31
		System.out.println("uploadPath : " + uploadPath.getPath());
		
		if (uploadPath.exists() == false) {  // !uploadPath.exists()
			uploadPath.mkdirs();
		}
		
		for (MultipartFile multipartFile : files) {
			// 업로드 시 file type의 input 태그가 총 5개 사용되었는데
			// 그중에 3개만 파일을 선택하고 2개는 파일선택안하고 비워두면
			// files.size()는 5가 되므로, 실제 선택한 파일정보만 가져오려면  isEmpty()로 걸러야됨
			if (multipartFile.isEmpty()) {
				continue;
			}
			
			String originalFilename = multipartFile.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String uploadFilename = uuid.toString() + "_" + originalFilename;
			
			File file = new File(uploadPath, uploadFilename); // 생성할 파일이름 정보
			
			// 파일1개 업로드(파일 생성) 완료
			multipartFile.transferTo(file);
			// ======================================================
			
			// 현재 업로드한 파일이 이미지 파일이면 썸네일 이미지를 추가로 생성하기
			boolean isImage = checkImageType(file); // 이미지 파일여부 확인 
			
			if (isImage == true) {
				File outFile = new File(uploadPath, "s_" + uploadFilename);
				
				Thumbnailator.createThumbnail(file, outFile, 200, 200);  // 썸네일 이미지 파일 생성하기
			}
			
			
			//===== insert할 주글 AttachVO 객체 데이터 생성 ======
			AttachVO attachVO = new AttachVO();
			attachVO.setUuid(uuid.toString());
			attachVO.setUploadpath(getFolder());
			attachVO.setFilename(originalFilename);
			attachVO.setFiletype( (isImage == true) ? "I" : "O" );
			attachVO.setBno(bno);
			
			attachList.add(attachVO); // 리스트에 추가
		} // for
		
		return attachList;
	} // uploadFilesAndGetAttachList
	
	
	
	// 첨부파일 업로드와 함께 주글쓰기 처리
	@PostMapping("/write")
	public String write(List<MultipartFile> files, BoardVO boardVO, 
			HttpServletRequest request, RedirectAttributes rttr) throws IOException {
		
		// insert할 새 글번호 가져오기
		int num = boardService.nextNum();
		
		// 첨부파일 업로드(썸네일 생성) 후 attacList 리턴
		List<AttachVO> attachList = uploadFilesAndGetAttachList(files, num);
		
		
		// ===== insert할 BoardVO 객체 데이터 설정 ======
		boardVO.setNum(num);
		boardVO.setReadcount(0);
		boardVO.setRegDate(new Date());
		boardVO.setIpaddr(request.getRemoteAddr()); // 사용자 IP 주소 저장
		boardVO.setReRef(num);  // 주글일 경우 글그룹번호는 글번호와 동일함
		boardVO.setReLev(0);    // 주글일 경우 들여쓰기 레벨은 0
		boardVO.setReSeq(0);    // 주글일 경우 글그룹 안에서의 순번은 0
		
		//boardService.register(boardVO);
		boardService.registerBoardAndAttaches(boardVO, attachList); // 주글 한개와 첨부파일 여러개 등록 - 트랜잭션 처리
		//===============================================
		
		// 리다이렉트시 서버에 전달할 데이터를 저장하면 스프링이 자동으로 쿼리스트링으로 변환하여 처리해줌
		rttr.addAttribute("num", boardVO.getNum());
		rttr.addAttribute("pageNum", 1);
		
		return "redirect:/board/content";
	} // post write
	
	
	
	private void deleteAttachFiles(List<AttachVO> attachList) {
		// 삭제할 파일정보가 없으면 메소드 종료
		if (attachList == null || attachList.size() == 0) {
			return;
		}
		
		
		String basePath = "/Users/jiyul/Desktop/workspace/upload";
		
		for (AttachVO attachVO : attachList) {
			String uploadpath = basePath + "/" + attachVO.getUploadpath();
			String filename = attachVO.getUuid() + "_" + attachVO.getFilename();
			
			File file = new File(uploadpath, filename);
			
			if (file.exists() == true) { // 해당 경로에 첨부파일이 존재하면
				file.delete(); // 첨부파일 삭제하기
			}
			
			// 첨부파일이 이미지 파일이면 썸네일 이미지파일도 삭제하기
			if (attachVO.getFiletype().equals("I")) { // "Image" 타입이면
				// 섬네일 이미지 존재여부 확인 후 삭제하기
				File thumbnailFile = new File(uploadpath, "s_" + filename);
				if (thumbnailFile.exists() == true) {
					thumbnailFile.delete();
				}
			}
		} // for
	} // deleteAttachFiles
	
	
	@GetMapping("/remove")
	public String remove(int num, String pageNum) {
		
		// ================== 첨부파일 삭제 ==================
		
		List<AttachVO> attachList = attachService.getAttachesByBno(num);
		
		deleteAttachFiles(attachList); // 첨부파일(썸네일도 삭제) 삭제하기
		
		System.out.println("================ 첨부파일 삭제 완료 ================");
		
		
		// attach 와 board 테이블 내용 삭제 - 트랜잭션 단위로 처리 적용
		boardService.deleteBoardAndAttaches(num);
		
		// 글목록으로 리다이렉트 이동
		return "redirect:/board/list?pageNum=" + pageNum;
	} // remove
	
	
	
	@GetMapping("/board/modify")
	public String modifyForm(int num, @ModelAttribute("pageNum") String pageNum, Model model) {
		
		BoardVO boardVO = boardService.getBoardAndAttaches(num);
		
		model.addAttribute("board", boardVO);
		model.addAttribute("attachList", boardVO.getAttachList());
		//model.addAttribute("pageNum", pageNum); // @ModelAttribute 애노테이션으로 처리함과 동일
		
		return "board/boardModify";
	} // modifyForm
	
	
	@PostMapping("/modify")
	public String modify(List<MultipartFile> files, BoardVO boardVO, String pageNum,
			@RequestParam(name = "delfile", required = false) List<String> delUuidList,
			HttpServletRequest request, RedirectAttributes rttr) throws IllegalStateException, IOException {
		
		// 1) 신규 첨부파일 업로드하기. 신규파일정보 신규리스트에 추가.
		List<AttachVO> newAttachList = uploadFilesAndGetAttachList(files, boardVO.getNum());
		System.out.println("================ POST modify - 첨부파일 업로드 완료 ================");
		
		
		// 2) 삭제할 첨부파일 삭제하기(썸네일 이미지도 삭제). 삭제파일정보 삭제리스트에 추가
		// ================== 첨부파일 삭제 ==================
		// 삭제할 첨부파일정보 리스트 가져오기
		List<AttachVO> delAttachList = null;
		
		if (delUuidList != null) {
			delAttachList = attachService.getAttachesByUuids(delUuidList);
			
			deleteAttachFiles(delAttachList); // 첨부파일(썸네일도 삭제) 삭제하기
		}
		System.out.println("================ POST modify - 첨부파일 삭제 완료 ================");
		
		
		// 3) boardVO 준비해서  첨부파일 신규리스트, 삭제리스트와 함께
		//    테이블 글 수정(update)을 트랜잭션 단위로 처리
		
		// ===== update할 BoardVO 객체 데이터 설정 ======
		boardVO.setRegDate(new Date());
		boardVO.setIpaddr(request.getRemoteAddr()); // 사용자 IP 주소 저장
		
		// 글번호에 해당하는 글정보 수정. 첨부파일정보 수정(insert, delete) - 트랜잭션 단위 처리
		boardService.updateBoardAndInsertAttachesAndDeleteAttaches(boardVO, newAttachList, delUuidList);
		System.out.println("================ POST modify - 테이블 수정 완료 ================");
		
		// 리다이렉트 쿼리스트링 정보 설정
		rttr.addAttribute("num", boardVO.getNum());
		rttr.addAttribute("pageNum", pageNum);
		
		// 상세보기 화면으로 리다이렉트 이동
		return "redirect:/board/content";
	} // modify
	
	
	
	/*  글목록 화면
	  num     subject             re_ref     re_lev     re_seq
	=============================================================
	   6      제목3                 6          0          0
	   4      제목2                 4          0          0
	   5        답글2               4          1          1
	   1      제목1                 1          0          0
	   7        답글2               1          1          1
	   2        답글1               1          1          1+1=2
	   3          답글1_1           1          2          2+1=3
	*/
	@GetMapping("/reply")
	public String replyForm(@ModelAttribute("reRef") String reRef, 
			@ModelAttribute("reLev") String reLev, 
			@ModelAttribute("reSeq") String reSeq, 
			@ModelAttribute("pageNum") String pageNum, 
			Model model) {
		/*
		model.addAttribute("reRef", reRef);
		model.addAttribute("reLev", reLev);
		model.addAttribute("reSeq", reSeq);
		model.addAttribute("pageNum", pageNum);
		*/
		return "board/boardReplyWrite";
	} // replyForm
	
	
	// 새로운 답글쓰기
	@PostMapping("/reply")
	public String reply(List<MultipartFile> files, BoardVO boardVO, String pageNum,
			HttpServletRequest request, RedirectAttributes rttr) throws IOException {
		
		// insert할 새 글번호 가져오기
		int num = boardService.nextNum();
		
		// 첨부파일 업로드(썸네일 생성) 후 attacList 리턴
		List<AttachVO> attachList = uploadFilesAndGetAttachList(files, num);
		
		
		// ===== insert할 답글 BoardVO 객체 데이터 설정 ======
		boardVO.setNum(num);
		boardVO.setReadcount(0);
		boardVO.setRegDate(new Date());
		boardVO.setIpaddr(request.getRemoteAddr()); // 사용자 IP 주소 저장

		// (*) 현재 boardVO에 들어있는 reRef, reLev, reSeq는 답글로서 insert될 정보가 아님.
		//  답글을 남길 대상글에 대한 정보임에 주의!!

		// 답글을 남길 대상글과 같은 글그룹 안에서
		//  대상글의 순번보다 큰 글들의 순번을 1씩 증가(UPDATE)한 후, 답글 규칙으로 INSERT하기
		//  -> 트랜잭션 처리 필요.
		
		boardService.addReplyAndAddAttaches(boardVO, attachList); // 답글 한개와 첨부파일 여러개 등록 - 트랜잭션 처리
		//===============================================
		
		// 리다이렉트시 서버에 전달할 데이터를 저장하면 스프링이 자동으로 쿼리스트링으로 변환하여 처리해줌
		rttr.addAttribute("num", boardVO.getNum());
		rttr.addAttribute("pageNum", pageNum);
		
		return "redirect:/board/content";
	} // reply
	
	
	/*******************************갤러리 list*****************************************/
	
	@GetMapping("/gallery")
	public String gallery(Criteria cri, Model model) {

		List<AttachVO> attachList = attachService.getBoardsAttach(cri);
		
		int totalCount = attachService.getTotalBoardsAttach(); // 전체 이미지 개수
		
		PageDTO pageDTO = new PageDTO(totalCount, cri); // 페이지블록(Pagination) 화면 만들때 필요한 정보
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 -> requestScope로 옮겨줌
		model.addAttribute("attachList", attachList);
		model.addAttribute("pageMaker", pageDTO);
		
		return "board/boardGalleryList";
	}

}






