package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.MemberVO;
import com.example.service.MemberService;

/*
  REST 컨트롤러의 HTTP method 매핑 규칙
  POST   - Create  (SQL Insert문)
  GET    - Read    (SQL Select문)
  PUT    - Update  (SQL Update문)
  DELETE - Delete  (SQL Delete문)
*/
@RestController  // 이 컨트롤러의 모든 메소드의 리턴값이 JSON 또는 XML 응답으로 동작함
@RequestMapping("/api/*")
public class MemberRestController {
	
	@Autowired
	private MemberService memberService;

	// GET요청  http://localhost:8090/api/members/user1       -> XML 응답
	// GET요청  http://localhost:8090/api/members/user1.json  -> JSON 응답
	@GetMapping(value = "/members/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> getOne(@PathVariable("id") String id) {
		
		MemberVO memberVO = memberService.getMemberById(id);
		int count = memberService.getCountById(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", memberVO);
		map.put("count", count);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	} // getOne
	
	
	@GetMapping(value = "/members", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<MemberVO>> getAll() {
		
		List<MemberVO> memberList = memberService.getMembers();
		
		return new ResponseEntity<List<MemberVO>>(memberList, HttpStatus.OK);
	} // getAll
	
	
	
	@PostMapping(value = "/members",
			consumes = "application/json",
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<MemberVO> create(@RequestBody MemberVO memberVO) {
		
		// 비밀번호 암호화
		String hashedPw = BCrypt.hashpw(memberVO.getPasswd(), BCrypt.gensalt());
		memberVO.setPasswd(hashedPw);
		
		// 생년월일 구분자("-") 제거
		String birthday = memberVO.getBirthday(); // "2021-09-07"
		birthday = birthday.replace("-", "");
		memberVO.setBirthday(birthday);
		
		// 현재날짜 설정
		memberVO.setRegDate(new Date());
		
		// 회원 등록하기
		memberService.register(memberVO);
		
		// insert된 VO 객체를 OK 상태코드와 함께 응답으로 줌
		return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
	} // create
	
	
	@DeleteMapping(value = "/members/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("id") String id) {
		
		int count = memberService.deleteMemberById(id);
		
		// BAD_GATEWAY (502) : 외부에서 전달받은 값이 잘못되어 오류가 발생한 경우
		// INTERNAL_SERVER_ERROR (500) : 서버 내부 로직 문제로 오류가 발생한 경우
		
		return (count > 0) 
				? new ResponseEntity<String>("success", HttpStatus.OK)
						: new ResponseEntity<String>("fail", HttpStatus.BAD_GATEWAY);
	} // remove
	
	
	
	@PutMapping(value = "/members/{id}",
			consumes = "application/json",
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<MemberVO> modify(@RequestBody MemberVO memberVO) {
		
		// 비밀번호 암호화
		String hashedPw = BCrypt.hashpw(memberVO.getPasswd(), BCrypt.gensalt());
		memberVO.setPasswd(hashedPw);
		
		// 생년월일 구분자("-") 제거
		String birthday = memberVO.getBirthday(); // "2021-09-07"
		birthday = birthday.replace("-", "");
		memberVO.setBirthday(birthday);
		
		memberService.updateMember(memberVO);
		
		// update된 VO 객체를 OK 상태코드와 함께 응답으로 줌
		return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
	} // modify
	

}





