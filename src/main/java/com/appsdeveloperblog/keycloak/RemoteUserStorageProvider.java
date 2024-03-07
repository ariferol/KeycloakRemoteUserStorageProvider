package com.appsdeveloperblog.keycloak;

//import lombok.extern.slf4j.Slf4j;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

//@Slf4j
public class RemoteUserStorageProvider implements UserLookupProvider, CredentialInputValidator, UserStorageProvider
        , UserQueryProvider, CredentialInputUpdater, UserRegistrationProvider {

    private KeycloakSession session;
    private ComponentModel model;
    private UsersApiLegacyService usersService;

    public RemoteUserStorageProvider(KeycloakSession session, ComponentModel model,
                                     UsersApiLegacyService usersService) {
        this.session = session;
        this.model = model;
        this.usersService = usersService;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        //new method
        return supportsCredentialType(credentialType) && user.credentialManager().isConfiguredFor(credentialType);
    }

//    @Override
//    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
//        //old method
//        return user.credentialManager().isConfiguredFor(credentialType);
//    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        System.out.println("isValid methoduna girdi,credentialInput.getChallengeResponse(): " + credentialInput.getChallengeResponse());

        // Gelen parametre validations;
        if (!supportsCredentialType(credentialInput.getType()) || !(credentialInput instanceof UserCredentialModel)) {
            return false;
        }
        System.out.println("isValid methoduna gelen getUsername: " + user.getUsername());
        VerifyPasswordResponse verifyPasswordResponse = usersService.verifyUserPassword(user.getUsername(),
                credentialInput.getChallengeResponse());

        if (verifyPasswordResponse == null)
            return false;

        return verifyPasswordResponse.getResult();
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
//        StorageId storageId = new StorageId(id);
//        String username = storageId.getExternalId();
//        log.debug("getUserById ye gelen id: {}", id);
        return findUser(realm, StorageId.externalId(id));
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
//        log.debug("getUserByEmail methoduna gelen email: {}", email);
        System.out.println("getUserByEmail methoduna gelen email: " + email);
        return findUser(realm, email);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
//        log.debug("getUserByUsername methoduna gelen username: {}", username);
        System.out.println("getUserByUsername methoduna gelen username: " + username);
        return findUser(realm, username);
    }

    private UserModel findUser(RealmModel realm, String username) {
        System.out.println("findUser methoduna giren username: " + username);
        UserModel userModel = null;
        try {
            User user = usersService.getUserByUserName(username);
            user.setUserName(user.getEmail()); //Bu satir daha sonra kapatilacak!
            //sample roles;
            List<String> tempRoles = new ArrayList<>();
            tempRoles.add("ADMIN");
            tempRoles.add("USER");
            user.setRoles(tempRoles);
            tempRoles.add("MANAGER");
            user.setGroups(tempRoles);
            user.setGender("Erkek");
            userModel = new UserModelAdapter(session, realm, model, user);
        }
//        catch (WebApplicationException e) {
        catch (Exception e) {
//            System.out.println(username + " isimli user bulunamadi, donen response status: " + e.getResponse().getStatus());
            System.out.println(username + " isimli user bulunamadi,hata: " + e.getMessage());
            e.printStackTrace();
        }
        return userModel;
    }

//    @Override
//    public UserModel getUserByUsername(RealmModel realm, String username) {
//        UserModel returnValue = null;
//        User user = usersService.getUserByUserName(username);
//        if (user != null) {
//            returnValue = createAdapter(realm, username);
//        }
//        return returnValue;
//    }

//    protected UserModel createAdapter(RealmModel realm, String username) {
//
//        // Create a new user adapter based on the AbstractUserAdapter class
//        return new AbstractUserAdapter(session, realm, model) {
//            // Override the getUsername method to return the username from the remote
//            // service
//            @Override
//            public String getUsername() {
//                return username;
//            }
//            @Override
//            public SubjectCredentialManager credentialManager() {
//                // Create a new credential manager based on the LegacyUserCredentialManager
//                // class
//                return new LegacyUserCredentialManager(session, realm, this) {
//                };
//            }
//        };
//    }

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
//        log.debug("Keycloak taki user update olursa harici servisteki user i da update etmek istersek burasi yazilacak. Simdilik keycloak in user ini update etmek istemiyoruz");
        return false;
    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
    }

    @Override
    public Stream<String> getDisableableCredentialTypesStream(RealmModel realm, UserModel user) {
        return Stream.empty();
    }

    @Override
    public int getUsersCount(RealmModel realm) {
//        return usersService.getUsersCount();
        return 1; //Simdilik users count bos birakildi
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
//        log.debug("searchForUserStream, search={}, first={}, max={}", search, firstResult, maxResults);
//        return toUserModelStream(usersService.getUsers(search, firstResult, maxResults), realm);
        return toUserModelStream(new ArrayList<User>(), realm);
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
//        log.debug("searchForUserStream, params={}, first={}, max={}", params, firstResult, maxResults);
//        return toUserModelStream(usersService.getUsers(null, firstResult, maxResults), realm);
        return toUserModelStream(new ArrayList<User>(), realm);
    }

    private Stream<UserModel> toUserModelStream(List<User> users, RealmModel realm) {
//        log.debug("Provider dan alinan users size: {} ", users.size());
        return users.stream().map(user -> new UserModelAdapter(session, realm, model, user));
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.empty();
    }

    @Override
    public UserModel addUser(RealmModel realm, String username) {
        return null;
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        return false;
    }

}
