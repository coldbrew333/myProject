package com.example.sample;

import org.springframework.stereotype.Component;

//@Component // 스프링 빈 표시
public class HyundaiEngine implements Engine {

	@Override
	public void start() {
		System.out.println("현대 엔진이 동작 : 부릉부릉~~");
	}
}
