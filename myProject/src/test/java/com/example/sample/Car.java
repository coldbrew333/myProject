package com.example.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // 스프링 빈 표시
public class Car {
	
	//@Autowired
	private Engine engine; // 업캐스팅 (다형성)

	//@Autowired
	public Car(Engine engine) {
		this.engine = engine;
	}

	//@Autowired
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void drive() {
		engine.start();
	}
}




