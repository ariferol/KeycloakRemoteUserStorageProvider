package com.appsdeveloperblog.keycloak;

import java.io.IOException;

import javax.ws.rs.PathParam;

import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersApiLegacyService {
	KeycloakSession session;

	public UsersApiLegacyService(KeycloakSession session) {
		this.session = session;
	}

	private static final Logger LOG = Logger.getLogger(UsersApiLegacyService.class);

	User getUserByUserName(String username) {
		try {
			String consumeUrl = "http://localhost:8099/users/" + username;
			System.out.println("Consume edilecek GET url: " + consumeUrl);
			User user = SimpleHttp.doGet(consumeUrl, this.session).asJson(User.class);
			if(user != null)
				System.out.println("user eposta: "+ user.getEmail());
			else
				System.out.println("user bos geldi");
			return user;
		} catch (IOException e) {
			LOG.warn("Error fetchingString consumeUrl = \"http://localhost:8099/users/\" + username;\n" +
					"\t\t\tSystem.out.println(\"getUserByUsername exception a dustu; Consume edilmis GET url: \" + consumeUrl); user " + username + " from external service: " + e.getMessage(), e);
		}
		return null;
	}

	VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password) {

		String consumeUrl = "http://localhost:8099/users/" + username + "/verify-password";
		System.out.println("VerifyUserPassword icin Consume edilecek POST url: " + consumeUrl);

		System.out.println("Parametre olarak gelen username: " + username);
		System.out.println("Parametre olarak gelen password: " + password);

//		SimpleHttp simpleHttp = SimpleHttp.doPost(consumeUrl, this.session);
		/*Password degerini body de post olarak gonderiyoruz;*/
		SimpleHttp simpleHttp = SimpleHttp.doPost(consumeUrl, this.session).json(password);

		VerifyPasswordResponse verifyPasswordResponse = null;

		// Add the request parameters as a map
//		simpleHttp.param("password", password);
		
		// Add any headers if needed
//		simpleHttp.header("Content-Type", "application/x-www-form-urlencoded");
		simpleHttp.header("Content-Type", "application/json");
		try {
			String response = simpleHttp.asString();

			// Create an ObjectMapper instance
			ObjectMapper mapper = new ObjectMapper();

			// Convert the JSON string to a VerifyPasswordResponse object
			verifyPasswordResponse = mapper.readValue(response, VerifyPasswordResponse.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return verifyPasswordResponse;
	}
}
