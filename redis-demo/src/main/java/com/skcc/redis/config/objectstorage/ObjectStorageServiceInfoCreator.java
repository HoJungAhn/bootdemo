package com.skcc.redis.config.objectstorage;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

public class ObjectStorageServiceInfoCreator extends CloudFoundryServiceInfoCreator<ObjectStorageServiceInfo>{
	private Logger logger = LoggerFactory.getLogger(ObjectStorageServiceInfoCreator.class);
	
	public ObjectStorageServiceInfoCreator() {
		// the literal in the tag is CloudFoundry-specific
		super(new Tags("Object-Storage"), ObjectStorageServiceInfo.OBJECT_STORAGE_SCHEME);
	}
	
	@Override
	public ObjectStorageServiceInfo createServiceInfo(Map<String, Object> serviceData) {
		
		logger.debug("SERVICE INFO :" + serviceData);
		return new ObjectStorageServiceInfo("test", "", "", 0, "", "", "");
	}

}
