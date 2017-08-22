package com.skcc.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TemplateApplicationTests {

	@Test
	public void contextLoads() {
		Pattern HOSTNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9-.]+$");
//		String url = "api.ng.bluemix.net";
		String url = "api.local.pcfdev.io";
		Matcher matcher = HOSTNAME_PATTERN.matcher(url);
		System.out.println(matcher.matches());
	}

}
