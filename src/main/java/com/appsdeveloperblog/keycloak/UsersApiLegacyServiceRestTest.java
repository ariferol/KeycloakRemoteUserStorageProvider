//package com.appsdeveloperblog.keycloak;
//
//import java.io.IOException;
//
//import javax.ws.rs.PathParam;
//
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.jboss.logging.Logger;
//import org.keycloak.broker.provider.util.SimpleHttp;
//import org.keycloak.models.KeycloakSession;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//public class UsersApiLegacyServiceRestTest {
//    KeycloakSession session;
//
////Burasi keycloak tarafindan cagirilmadigi icin elimizde session yok o yuzden kapatiyoruz;
////    public UsersApiLegacyServiceRestTest(KeycloakSession session) {
////        this.session = session;
////    }
//
//    private static final Logger LOG = Logger.getLogger(UsersApiLegacyService.class);
//    private CloseableHttpClient httpClient = HttpClients.createDefault();
//
//    @GetMapping("/{userName}")
//    public User getUserByUserName(@PathVariable("userName") String username) {
////	User getUserByUserName(String username) {
//        try {
//            User result = new User();
//            SimpleHttp resp = SimpleHttp.doGet("http://localhost:8099/users/" + username, this.httpClient);
//            if(resp != null){
//                result = resp.asJson(User.class);
//            }
////			return SimpleHttp.doGet("http://localhost:8099/users/" + username, this.session).asJson(User.class);
//            return result;
//        } catch (IOException e) {
//            LOG.warn("Error fetching user " + username + " from external service: " + e.getMessage(), e);
//        }
//        return null;
//    }
//
//    @PostMapping("/{userName}/verify-password")
//    public VerifyPasswordResponse verifyUserPassword(@PathVariable("userName") String userName,
//                                                     @RequestBody String password) {
////    VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password) {
////        SimpleHttp simpleHttp = SimpleHttp.doPost("http://localhost:8099/users/" + username + "/verify-password",
////                this.session);
//        /*Password degerini body de post olarak gonderiyoruz;*/
//        SimpleHttp simpleHttp = SimpleHttp.doPost("http://localhost:8099/users/" + userName + "/verify-password",
//                this.httpClient).json(password);
//        VerifyPasswordResponse verifyPasswordResponse = null;
//
//        // Add the request parameters as a map
////        simpleHttp.param("password", password);
//
//        // Add any headers if needed
////        simpleHttp.header("Content-Type", "application/x-www-form-urlencoded");
//        simpleHttp.header("Content-Type", "application/json");
//
//        try {
//            String response = simpleHttp.asString();
//
//            // Create an ObjectMapper instance
//            ObjectMapper mapper = new ObjectMapper();
//
//            // Convert the JSON string to a VerifyPasswordResponse object
//            verifyPasswordResponse = mapper.readValue(response, VerifyPasswordResponse.class);
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return verifyPasswordResponse;
//    }
//}
