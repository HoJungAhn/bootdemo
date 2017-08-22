package com.skcc.demo.config;

import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.doppler.ReactorDopplerClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.cloudfoundry.reactor.uaa.ReactorUaaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudFoundaryConfiguration {
	@Bean
	DefaultConnectionContext connectionContext(@Value("${cf.apiHost}") String apiHost) {
	    return DefaultConnectionContext.builder()
	        .apiHost(apiHost)
	        .build();
	}

	@Bean
	PasswordGrantTokenProvider tokenProvider(@Value("${cf.username}") String username,
	                                         @Value("${cf.password}") String password) {
	    return PasswordGrantTokenProvider.builder()
	        .password(password)
	        .username(username)
	        .build();
	}
	@Bean
	ReactorCloudFoundryClient cloudFoundryClient(ConnectionContext connectionContext, TokenProvider tokenProvider) {
	    return ReactorCloudFoundryClient.builder()
	        .connectionContext(connectionContext)
	        .tokenProvider(tokenProvider)
	        .build();
	}

	@Bean
	ReactorDopplerClient dopplerClient(ConnectionContext connectionContext, TokenProvider tokenProvider) {
	    return ReactorDopplerClient.builder()
	        .connectionContext(connectionContext)
	        .tokenProvider(tokenProvider)
	        .build();
	}

	@Bean
	ReactorUaaClient uaaClient(ConnectionContext connectionContext, TokenProvider tokenProvider) {
	    return ReactorUaaClient.builder()
	        .connectionContext(connectionContext)
	        .tokenProvider(tokenProvider)
	        .build();
	}
	
	@Bean
	DefaultCloudFoundryOperations defaultCloudFoundaryOperation(ReactorCloudFoundryClient cloudFoundryClient,
			ReactorDopplerClient dopplerClient, ReactorUaaClient uaaClient, 
			@Value("${cf.organization}") String organization, @Value("${cf.space}") String space) {

	    return DefaultCloudFoundryOperations.builder()
	    	    .cloudFoundryClient(cloudFoundryClient)
	    	    .dopplerClient(dopplerClient)
	    	    .uaaClient(uaaClient)
	    	    .organization(organization)
	    	    .space(space)
	    	    .build();
	}

}
