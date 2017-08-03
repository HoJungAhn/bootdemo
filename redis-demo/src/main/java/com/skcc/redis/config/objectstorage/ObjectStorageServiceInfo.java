package com.skcc.redis.config.objectstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.service.UriBasedServiceInfo;
import org.springframework.cloud.service.ServiceInfo.ServiceLabel;

@ServiceLabel("Object-Storage")
public class ObjectStorageServiceInfo extends UriBasedServiceInfo{
	public static final String OBJECT_STORAGE_SCHEME = "Object-Storage";
	
	private Logger logger = LoggerFactory.getLogger(ObjectStorageServiceInfo.class);
	
	public ObjectStorageServiceInfo(String id, String scheme, String host, int port, String username, String password, String path) {
		super(id, scheme, host, port, username, password, path);
		logger.debug("ObjectStorageServiceInfo : id={}, scheme={}, host={}, port={},username={}, password={}, path={}", new Object[]{id, host, port, username, password, path} );
	}
}
