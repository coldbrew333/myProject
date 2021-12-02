package com.example.mapper;

import com.example.domain.ProfilePicVO;

public interface ProfilePicMapper {


	/*SELECT - 나의정보 프로필 사진 조회*/
	ProfilePicVO selectProfilePic(String id);
	
	/*UPDATE - 나의정보 프로필 사진 수정*/
	void updateProfilePic(ProfilePicVO profilePicVO);
	
	/*INSERT - 나의정보 프로필 사진 저장*/
	void insertProfilePic(ProfilePicVO profilePicVO);
	/* DELETE - 나의정보 프로필 사진 삭제*/
	void deleteProfilePic(String id);


	
}







