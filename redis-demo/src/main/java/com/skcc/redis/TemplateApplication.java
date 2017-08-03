package com.skcc.redis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.client.IOSClientBuilder.V3;
import org.openstack4j.api.storage.ObjectStorageService;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TemplateApplication {

	@Autowired
	private RedisTemplate<String, Object> template;
	
	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}
	
	@GetMapping("/get/{key}")
	public String get(@PathVariable("key") String key){
		
		ValueOperations<String, Object> opsForValue = template.opsForValue();
		return (String)opsForValue.get(key);
	}
	
	@GetMapping("/set/{key}/{value}")
	public String set(@PathVariable("key") String key, @PathVariable("value") String value){
		ValueOperations<String, Object> opsForValue = template.opsForValue();
		opsForValue.set(key, value);
		return (String)opsForValue.get(key);
	}

	
	private static final String OBJECT_STORAGE_AUTH_URL = "https://identity.open.softlayer.com";
	private static final String PASSWORD = "YU,l99Eg,mNMu5!)";
	private static final String USERID = "e12340034fd244349378577b345f32a6";
	private static final String DOMAINNAME = "1404765";
	private static final String PROJECT = "object_storage_dc896def_6460_4c8b_8e2d_55a19e18ff4d";
	private static final String containerName = "DEV";
	
	private ObjectStorageService authenticateAndGetObjectStorageService() throws Exception {
		

		System.out.println("Authenticating...");

		Identifier domainIdent = Identifier.byName(DOMAINNAME);
		Identifier projectIdent = Identifier.byName(PROJECT);
		 
		V3 os = OSFactory.builderV3()
				 .endpoint(OBJECT_STORAGE_AUTH_URL + "/v3")
				 .credentials(USERID, PASSWORD)
				 .scopeToProject(projectIdent, domainIdent);
		
		OSClientV3 authenticate = os.authenticate();

		System.out.println("Authenticated successfully!");

		ObjectStorageService objectStorage = authenticate.objectStorage();

		return objectStorage;
	}
	
	@GetMapping("/filedownload/{fileName}")
	public ResponseEntity<?> getfile(@PathVariable String fileName, HttpServletResponse response) throws Exception {
		ObjectStorageService objectStorage = authenticateAndGetObjectStorageService();

		System.out.println("Retrieving file from ObjectStorage...");


		if(containerName == null || fileName == null){ //No file was specified to be found, or container name is missing
			System.out.println("Container name or file name was not specified.");
			return ResponseEntity.badRequest().body(HttpServletResponse.SC_NOT_FOUND);
		}

		SwiftObject pictureObj = objectStorage.objects().get(containerName,fileName);

		if(pictureObj == null){ //The specified file was not found
			System.out.println("File not found.");
			return ResponseEntity.badRequest().body(HttpServletResponse.SC_NOT_FOUND);
		
		}

		String mimeType = pictureObj.getMimeType();

		DLPayload payload = pictureObj.download();

		InputStream in = payload.getInputStream();

		response.setContentType(mimeType);

		OutputStream out = response.getOutputStream();

		IOUtils.copy(in, out);
		in.close();
		out.close();

		System.out.println("Successfully retrieved file from ObjectStorage!");
		
		return ResponseEntity.ok(HttpServletResponse.SC_OK);
	}
	
	@GetMapping("/fileupload/{fileName}")
	private ResponseEntity<?> putFile(@PathVariable String fileName) throws Exception {
		ObjectStorageService objectStorage = authenticateAndGetObjectStorageService();

		System.out.println("Storing file in ObjectStorage...");
		
		if(containerName == null || fileName == null){ //No file was specified to be found, or container name is missing
			System.out.println("File not found.");
			return ResponseEntity.badRequest().body(HttpServletResponse.SC_NOT_FOUND);
		}
		
		File uploadFile = new File("/Users/ahnhojung/Downloads/" + fileName);

		if(uploadFile.exists() == false){
			System.out.println("upload File not found.[" + uploadFile + "]");
			return ResponseEntity.badRequest().body(HttpServletResponse.SC_NOT_FOUND);
		}
		
		final InputStream fileStream = new FileInputStream(uploadFile);

		Payload<InputStream> payload = new PayloadClass(fileStream);

		objectStorage.objects().put(containerName, fileName, payload);
		
		System.out.println("Successfully stored file in ObjectStorage!");
		
		return ResponseEntity.ok(HttpServletResponse.SC_OK);
	}
}

class PayloadClass implements Payload<InputStream> {
	private InputStream stream = null;

	public PayloadClass(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public void close() throws IOException {
		stream.close();
	}

	@Override
	public InputStream open() {
		return stream;
	}

	@Override
	public void closeQuietly() {
		try {
			stream.close();
		} catch (IOException e) {
		}
	}

	@Override
	public InputStream getRaw() {
		return stream;
	}

}