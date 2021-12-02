package com.example.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


import com.example.domain.MemberVO;
import com.example.domain.ProfilePicVO;

public interface MemberMapper {

	@Insert("INSERT INTO member (id, passwd, name, birthday, gender, email, recv_email, reg_date) "
			+ " VALUES (#{id}, #{passwd}, #{name}, #{birthday}, #{gender}, #{email}, #{recvEmail}, #{regDate}) ")
	int insert(MemberVO memberVO);
	
	@Select("SELECT * FROM member WHERE id = #{id}")
	MemberVO getMemberById(String id);
	
	@Select("SELECT * FROM member ORDER BY id")
	List<MemberVO> getMembers();
	
	@Select("SELECT COUNT(*) FROM member WHERE id = #{id}")
	int getCountById(String id);
	
	@Delete("DELETE FROM member WHERE id = #{id}")
	int deleteMemberById(String id);
	
	
//	@Update(" UPDATE member "
//			+ " SET passwd = #{passwd}, name = #{name}, birthday = #{birthday}, gender = #{gender}, email = #{email}, recv_email = #{recvEmail} "
//			+ " WHERE id = #{id} ")
//	void updateMember(MemberVO memberVO);

	
	/* SELECT - 해당 비밀번호가 맞는지 확인  */
	MemberVO selectCheckPassword(String id);
	
	/* UPDATE - 비밀번호 변경*/
	void updatePasswd(MemberVO memberVO);

	/* UPDATE - 나의 정보 업데이트*/
	void updateMember(MemberVO memberVO);

}







