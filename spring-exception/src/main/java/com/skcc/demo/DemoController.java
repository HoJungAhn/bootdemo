package com.skcc.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	@GetMapping("/sample")
	public ResponseEntity<Object> sample() throws Exception{
		throw new Exception("test exception");
	}
}
