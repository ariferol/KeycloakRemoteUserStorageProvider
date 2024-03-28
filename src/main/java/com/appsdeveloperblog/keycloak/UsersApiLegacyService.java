package com.appsdeveloperblog.keycloak;

import java.io.IOException;

import javax.ws.rs.PathParam;

import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersApiLegacyService {
	KeycloakSession session;

	private final String serviceUrl;
	private final String authenticatedToken;

	public UsersApiLegacyService(KeycloakSession session, ComponentModel model ) {
		this.session = session;
		this.serviceUrl = model.get(Constants.SERVICE_URL);
		this.authenticatedToken = model.get(Constants.AUTH_TOKEN);
	}

	private static final Logger LOG = Logger.getLogger(UsersApiLegacyService.class);

	User getUserByUserName(String username) {
		String consumeUrl = "";
		try {
//			String consumeUrl = "http://localhost:8099/users/" + username;
			System.out.println("Harici REST serviste kullanilacak gecerli token : " + this.authenticatedToken);
			consumeUrl = this.serviceUrl + "/" + username;
			System.out.println("Consume edilecek GET url: " + consumeUrl);
			User user = SimpleHttp.doGet(consumeUrl, this.session).asJson(User.class);
			if(user != null && user.getEmail() != null && !user.getEmail().equals(""))
				System.out.println("user eposta: "+ user.getEmail());
			else{
				System.out.println("user bos geldi");
				return null;
			}
			return user;
		} catch (IOException e) {
			System.out.println("getUserByUsername exception a dustu; consumeUrl = " + consumeUrl );
			System.out.println("Hata : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password) {
		System.out.println("Harici REST serviste kullanilacak gecerli token(verifyUserPassword) : " + this.authenticatedToken);
//		String consumeUrl = "http://localhost:8099/users/" + username + "/verify-password";
		String consumeUrl = this.serviceUrl + "/" + username + "/verify-password";
		System.out.println("VerifyUserPassword icin Consume edilecek POST url: " + consumeUrl);

		System.out.println("Parametre olarak gelen username(email): " + username);
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
			System.out.println("verifyUserPassword exception a dustu; consumeUrl = " + consumeUrl );
			System.out.println("Hata2 : " + e.getMessage());
			e.printStackTrace();
		}

		return verifyPasswordResponse;
	}
}
