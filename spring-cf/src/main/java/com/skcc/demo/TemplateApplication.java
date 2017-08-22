package com.skcc.demo;

import java.util.stream.Collectors;

import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.operations.applications.ApplicationSummary;
import org.cloudfoundry.operations.organizations.OrganizationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


@RestController
@SpringBootApplication
public class TemplateApplication {

	@Autowired
	private DefaultCloudFoundryOperations cloudFoundryOperations;
	
	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}
	
	@GetMapping("/org")
	public DeferredResult <ResponseEntity<String>> getClient() {
		
	     DeferredResult <ResponseEntity<String>> result = new DeferredResult<ResponseEntity<String>>(); 
	     cloudFoundryOperations.organizations()
	     .list()
	     .map(OrganizationSummary::getName)
	     .collect(Collectors.joining(", "))
	     .map(n -> ResponseEntity.ok(n))
	     .subscribe(re -> result.setResult(re));
	     
	     return result;
	}
	
	@GetMapping("/applist")
	public DeferredResult <ResponseEntity<String>> getApplicationList() {
		DeferredResult <ResponseEntity<String>> result = new DeferredResult<ResponseEntity<String>>(); 
	     
	     cloudFoundryOperations.applications()
	     .list()
	     .map(ApplicationSummary::getName)
	     .collect(Collectors.joining(", "))
	     .map(n -> ResponseEntity.ok(n))
	     .subscribe(re -> result.setResult(re));
	     
	     return result;
	}
}
