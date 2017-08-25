package com.skcc.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.demo.event.BusinessEvent;
import com.skcc.demo.event.BusinessEventListener;

@RestController
public class DemoController {
	@Autowired
	private ApplicationEventPublisher publisher;	

	@GetMapping("/event")
	public ResponseEntity<Object> event() throws Exception{
		Map<String, Object> userMap=new HashMap<String, Object>();
		userMap.put("AAAA", "aaaa");
		userMap.put("BBBB", "bbbb");
		
		BusinessEvent event=new BusinessEvent("user","join_user",userMap);
		publisher.publishEvent(event);
		return ResponseEntity.ok(userMap);
	}	
	
	@BusinessEventListener("user.join_member")
	public void welcome(HashMap <String, Object>map){
	 	System.out.println("hello1 >>>>>>>>>>>>>>>>>" +map.toString());
	}
	
	@BusinessEventListener("user.join_member")
	public void welcome1(HashMap <String, Object>map){
	 	System.out.println("hello2 >>>>>>>>>>>>>>>>>" +map.toString());
	}
}
