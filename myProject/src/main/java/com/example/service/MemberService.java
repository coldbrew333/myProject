package com.example.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.MemberVO;
import com.example.domain.ProfilePicVO;
import com.example.mapper.MemberMapper;


@Service // @Component 계열 애노테이션
@Transactional
public class MemberService {
	
	private MemberMapper memberMapper;
	

	// @Autowired 애노테이션이 생성자에서는 생략가능 
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	// 회원가입하기
	public void register(MemberVO memberVO) {
		
//		long beginTime = System.currentTimeMillis();
		
		memberMapper.insert(memberVO);
		
//		long endTime = System.currentTimeMillis();
		
//		long diff = endTime - beginTime;
//		System.out.println("메소드 실행시간 : " + diff + "ms");
	}
	
	public MemberVO getMemberById(String id) {
		MemberVO memberVO = memberMapper.getMemberById(id);
		
		return memberVO;
	}
	
	public List<MemberVO> getMembers() {
		return memberMapper.getMembers();
	}
	
	public int getCountById(String id) {
		return memberMapper.getCountById(id);
	}
	
	public int deleteMemberById(String id) {
		int rowCount = memberMapper.deleteMemberById(id);
		return rowCount;
	}
	
	public void updateMember(MemberVO memberVO) {
		memberMapper.updateMember(memberVO);
	}

	
	
	/* 비밀번호 변경 */
	public boolean updatePassword(String id, String password, String newPassword) {

		boolean isPasswordSame = checkPassword(id,password);
		
		// 해당 패스워드가 맞다면
		if(isPasswordSame) {
			// 비밀번호 암호화
			String hasedPw = BCrypt.hashpw(newPassword, BCrypt.gensalt());
			// 맴버 vo 설정
			MemberVO memberVO = new MemberVO();
			memberVO.setId(id);
			memberVO.setPasswd(hasedPw);
			
			memberMapper.updatePasswd(memberVO);
			
			return true;
		}

		return false;
	}
	
	/* 비밀 번호 확인 체크*/
	public boolean checkPassword(String id ,String password) {
		// 해당 id값의 정보 조회
		MemberVO memberVO =  memberMapper.selectCheckPassword(id);
		
		// 해당 id의 passwd값과 사용자가 입력한 passwd값 비교한 값 return
		return BCrypt.checkpw(password, memberVO.getPasswd());
	}

	/* 나의 정보 업데이트 */
	public void updateMyInfo(MemberVO memberVO,ProfilePicVO profilePicVO) {
		memberMapper.updateMember(memberVO);

	}
	

}





