package com.skcc.redis.config.objectstorage;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.storage.ObjectStorageAccountService;
import org.openstack4j.api.storage.ObjectStorageService;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({BluemixObjectStorageProperties.class})
public class BluemixObjectStorageAutoConfig {
	private Logger logger = LoggerFactory.getLogger(BluemixObjectStorageAutoConfig.class);
	
	@Autowired
	private BluemixObjectStorageProperties properties;
	
	@Bean
	public Token authenticateAndGetObjectStorageService() throws Exception {

		logger.info("Object Storage Authenticating...");
		logger.info(properties.toString());

		Identifier domainIdent = Identifier.byName(properties.getDomainName());
		Identifier projectIdent = Identifier.byName(properties.getProject());
		 
		OSClientV3 os = OSFactory.builderV3()
				 .endpoint(properties.getAuthUrl() + "/v3")
				 .credentials(properties.getUserId(), properties.getPassword())
				 .scopeToProject(projectIdent, domainIdent)
				 .authenticate();
		Token token = os.getToken();
		
		logger.info("Object Storage Authenticated successfully!");

		return token;
	}
}
