//package org.onboard.employeeservice.utils;
//
//import org.keycloak.admin.client.CreatedResponseUtil;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.keycloak.admin.client.resource.RealmResource;
//import org.keycloak.admin.client.resource.UserResource;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RoleRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.onboard.employeeservice.dto.EmployeeInputDto;
//import org.onboard.employeeservice.entities.Employee;
//
//import javax.ws.rs.core.Response;
//import java.util.List;
//
//public class KeycloakUtils {
//    private static Keycloak getAdminKeycloakUser() {
//        return KeycloakBuilder.builder()
//                .serverUrl("http://localhost:8080/")
//                .grantType("password")
//                .realm("onboard-realm")
//                .clientId("onboard-client")
//                .username("admin")
//                .password("admin")
//                .build();
//    }
//    private static RealmResource getRealm() {
//        return getAdminKeycloakUser().realm("onboard-realm");
//    }
//
//    private static void setCredentials(String userId, String password){
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setTemporary(false);
//        credentialRepresentation.setType("password");
//        credentialRepresentation.setValue(password);
//
//        UserResource userResource = getRealm().users().get(userId);
//        userResource.resetPassword(credentialRepresentation);
//    }
//
//    private static void addRole(String userId, String role){
//        RoleRepresentation roleRepresentation = getRealm().roles().get(role).toRepresentation();
//
//        UserResource userResource = getRealm().users().get(userId);
//        userResource.roles().realmLevel().add(List.of(roleRepresentation));
//    }
//
//    public static String createKeycloakUserWithRole(EmployeeInputDto employeeInputDto){
//        UserRepresentation userRepresentation = new UserRepresentation();
//        userRepresentation.setLastName(employeeInputDto.getLname());
//        userRepresentation.setFirstName(employeeInputDto.getFname());
//        userRepresentation.setUsername(employeeInputDto.getLname());
//        userRepresentation.setEnabled(true);
//        userRepresentation.setEmail(employeeInputDto.getEmail());
//        System.out.println("User Representation: " +userRepresentation.getFirstName() +" " + userRepresentation.getLastName() +" "+ userRepresentation.getEmail() +" "+userRepresentation.isEnabled());
//        Response response = getRealm().users().create(userRepresentation);
//        System.out.println("response: "+ response);
//        String userId = CreatedResponseUtil.getCreatedId(response);
//        System.out.println("userId: "+ userId);
////        String userId="12345";
//        setCredentials(userId, employeeInputDto.getPassword());
//        addRole(userId,employeeInputDto.getRole());
//        return userId;
//    }
//}
