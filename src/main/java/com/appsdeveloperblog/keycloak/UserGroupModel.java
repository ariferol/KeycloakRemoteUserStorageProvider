package com.appsdeveloperblog.keycloak;

//import lombok.RequiredArgsConstructor;
import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.ReadOnlyException;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author arif.erol
 */
//@RequiredArgsConstructor
public class UserGroupModel implements GroupModel {

    public UserGroupModel(String name) {
        this.name = name;
    }

    private final String name;
    private static final String READONLY_GROUP_MESSAGE = "group salt okunur";

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String s) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public void setSingleAttribute(String s, String s1) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public void setAttribute(String s, List<String> list) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public void removeAttribute(String s) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public String getFirstAttribute(String s) {
        return null;
    }

    @Override
    public Stream<String> getAttributeStream(String s) {
        return Stream.empty();
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return Map.of();
    }

    @Override
    public GroupModel getParent() {
        return null;
    }

    @Override
    public String getParentId() {
        return null;
    }

    @Override
    public Stream<GroupModel> getSubGroupsStream() {
        return Stream.empty();
    }

    @Override
    public void setParent(GroupModel groupModel) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public void addChild(GroupModel groupModel) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public void removeChild(GroupModel groupModel) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public Stream<RoleModel> getRealmRoleMappingsStream() {
        return Stream.empty();
    }

    @Override
    public Stream<RoleModel> getClientRoleMappingsStream(ClientModel clientModel) {
        return Stream.empty();
    }

    @Override
    public boolean hasRole(RoleModel roleModel) {
        return false;
    }

    @Override
    public void grantRole(RoleModel roleModel) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }

    @Override
    public Stream<RoleModel> getRoleMappingsStream() {
        return Stream.empty();
    }

    @Override
    public void deleteRoleMapping(RoleModel roleModel) {
        throw new ReadOnlyException(READONLY_GROUP_MESSAGE);
    }
}
