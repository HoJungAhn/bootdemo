package com.skcc.redis;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.junit.Test;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.storage.ObjectStorageService;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RunWith(SpringRunner.class)
//@SpringBootTest
/*
"auth_url": "https://identity.open.softlayer.com",
"region": "dallas",
"password": "YU,l99Eg,mNMu5!)",

"project": "object_storage_dc896def_6460_4c8b_8e2d_55a19e18ff4d",
"domainName": "1404765",
"username": "admin_9685cbf94668d6a635f3c00ff769a41e7b396795",

"projectId": "706f588d5ac34604a422e05f43dbe799",
"userId": "e12340034fd244349378577b345f32a6",
"domainId": "f13088e02afa43fda8f772112b88dbf1",

"role": "admin"
*/

/*
Object obj = parser.parse(envServices);
JSONObject jsonObject = (JSONObject) obj;
JSONArray vcapArray = (JSONArray) jsonObject.get("Object-Storage");
JSONObject vcap = (JSONObject) vcapArray.get(0);
JSONObject credentials = (JSONObject) vcap.get("credentials");
String userId = credentials.get("userId").toString();
String password = credentials.get("password").toString();
String auth_url = credentials.get("auth_url").toString() + "/v3";
String domain = credentials.get("domainName").toString();
String project = credentials.get("project").toString();
Identifier domainIdent = Identifier.byName(domain);
Identifier projectIdent = Identifier.byName(project);
*/

public class TemplateApplicationTests {
	
	private Logger logger = LoggerFactory.getLogger(TemplateApplication.class);
	
	private static final String OBJECT_STORAGE_AUTH_URL = "https://identity.open.softlayer.com";
	private static final String PASSWORD = "YU,l99Eg,mNMu5!)";
	
	private static final String USERID = "e12340034fd244349378577b345f32a6";
	private static final String DOMAINNAME = "1404765";
	private static final String PROJECT = "object_storage_dc896def_6460_4c8b_8e2d_55a19e18ff4d";
	
//	private static final String USERNAME = "admin_9685cbf94668d6a635f3c00ff769a41e7b396795";
//	private static final String DOMAIN_ID = "1404765";
//	private static final String PROJECT_ID = "object_storage_dc896def_6460_4c8b_8e2d_55a19e18ff4d";
//	private static final String USERNAME = "e12340034fd244349378577b345f32a6";
//	private static final String DOMAIN_ID = "f13088e02afa43fda8f772112b88dbf1";
//	private static final String PROJECT_ID = "706f588d5ac34604a422e05f43dbe799";
	


	
	private static final String containerName = "DEV";
	
	private ObjectStorageService authenticateAndGetObjectStorageService() throws Exception {

		System.out.println("Authenticating...");

		Identifier domainIdent = Identifier.byName(DOMAINNAME);
		Identifier projectIdent = Identifier.byName(PROJECT);
		 
		OSClientV3 os = OSFactory.builderV3()
				 .endpoint(OBJECT_STORAGE_AUTH_URL + "/v3")
				 .credentials(USERID, PASSWORD)
				 .scopeToProject(projectIdent, domainIdent)
				 .authenticate();
		
		
//		OSClientV3 os = OSFactory.builderV3()
//				.endpoint(OBJECT_STORAGE_AUTH_URL)
//				.credentials(USERNAME,PASSWORD, domainIdentifier)
//				.scopeToProject(Identifier.byId(PROJECT_ID))
//				.authenticate();

		System.out.println("Authenticated successfully!");

		ObjectStorageService objectStorage = os.objectStorage();

		return objectStorage;
	}
	
	@Test
	public void contextLoads() throws Exception {
		authenticateAndGetObjectStorageService();
	}
	
	@Test
	public void contextSwift() throws Exception{
		createSwift();
	}
	
	@Test
	public void logging(){
		logTest("id", "scheme", "host", 10, "user", "pass", "path");
	}
	
	private void logTest(String id, String scheme, String host, int port, String username, String password, String path) {
		logger.debug("id={}, scheme={}, host={}, port={},username={}, password={}, path={}", new Object[]{id, host, port, username, password, path} );
	}
	
	private Account createSwift() throws Exception{
		AccountConfig config = new AccountConfig();
		config.setUsername(USERID);
		config.setPassword(PASSWORD);
		config.setAuthUrl(OBJECT_STORAGE_AUTH_URL + "/tokens");
		config.setAuthenticationMethod(AuthenticationMethod.BASIC);
		AccountFactory factory = new AccountFactory(config);
		
		Account account = factory.createAccount();
		return account;
		
	}

}
