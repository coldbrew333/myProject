package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.ProfilePicVO;
import com.example.mapper.ProfilePicMapper;

@Service // @Component 계열 애노테이션
@Transactional
public class ProfilePicService {
	
	@Autowired
	private ProfilePicMapper profilePicMapper;


	/* 나의 정보 프로필 사진 조회*/
	public ProfilePicVO getProfilePic(String id) {
		return profilePicMapper.selectProfilePic(id);
	}

	/* 나의 정보 프로필 사진 업데이트*/
	public void updateProfilePic(ProfilePicVO profilePicVO) {
		profilePicMapper.updateProfilePic(profilePicVO);
		
	}
	
	/* 나의 정보 프로필 사진 인설트*/
	public void insertProfilePic(ProfilePicVO profilePicVO) {
		profilePicMapper.insertProfilePic(profilePicVO);
		
	}

	/* 나의 정보 프로필 사진 딜리트*/
	public void deleteProfilePic(String id) {
		profilePicMapper.deleteProfilePic(id);
		
	}
	

}





