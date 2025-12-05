package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class OrganizationApi {
    RequestSpecification requestSpecification;
    public Response response;

       Assertions Assertion;

    public OrganizationApi() {
        // Constructor
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints
    private static final String WorkSpace_endpoint = "organizations";

    public Response getResponse(){
        return response;
    }
    //@Step("Create a new user account with full details")
    public OrganizationApi createWorkSpace(String displayName){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("displayName",displayName);
        response =requestSpecification
                 .spec(RequestBuilder.getRequestSpecification(queryParams))
                 .post(WorkSpace_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public OrganizationApi createWorkSpaceWithoutAuthorization(String displayName){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("displayName",displayName);
        response =requestSpecification
                .spec(RequestBuilder.getunAuthorizedRequestSpecification(queryParams))
                .post(WorkSpace_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public OrganizationApi getWorkSpace(String idOrganization){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(WorkSpace_endpoint+"/"+idOrganization);
        Log.info(response.asPrettyString());
        return this;
    }
    public OrganizationApi updateWorkSpace(String idOrganization, String displayName){
        Map <String,Object> queryParams = new HashMap<>();
        queryParams.put("displayName",displayName);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(WorkSpace_endpoint+"/"+idOrganization);
        Log.info(response.asPrettyString());
        return this;
    }
    public OrganizationApi deleteWorkSpace(String idOrganization){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(WorkSpace_endpoint+"/"+idOrganization);
        Log.info(response.asPrettyString());
        return this;
    }
    public OrganizationApi getActionsOfOrganization(String idOrganization) {
        response = requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(WorkSpace_endpoint + "/" + idOrganization + "/actions");
        Log.info(response.asPrettyString());
        return this;
    }

    //Assertions

    public OrganizationApi verifyWorkSpaceCreated(String displayName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                 .assertEquals(response.jsonPath().get("displayName"), displayName,"Workspace is not created successfully");
        return this;
    }

    public OrganizationApi verifyWorkSpaceRetriving(String workspaceId , String displayName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), workspaceId,"Workspace is not created successfully")
                .assertEquals(response.jsonPath().get("displayName"), displayName,"Workspace is not created successfully");
        return this;
    }

    public OrganizationApi verifyWorkSpaceUpdated(String workspaceId , String name) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                 .assertEquals(response.jsonPath().get("displayName"), name,"Workspace is not updated successfully")
                 .assertEquals(response.jsonPath().get("id"), workspaceId,"Workspace is not updated successfully");
        return this;
    }
    public OrganizationApi verifyWorkSpaceRetrivingAfterUpdating(String workspaceId , String name) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), workspaceId,"Workspace is not updated successfully")
                .assertEquals(response.jsonPath().get("displayName"), name,"Workspace is not updated successfully");
        return this;
    }

    public OrganizationApi verifyWorkSpaceDeleted() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
        return this;
    }

    public OrganizationApi verifyWorkSpaceRetrivingAfterDeleting() {
        Assertion.assertTrue((response.getStatusCode()==404),"Response code is not 404");
        return this;
    }

    public void verifyUnauthorizedErrorGeneratedSuccessfully() {
        Assertion.assertTrue(response.getStatusCode()==401,"Error in Authorization ")
                 .assertEquals(response.jsonPath().get("message"),"missing scopes","Workspace is not updated successfully");

    }

    public OrganizationApi verifyErrorNotFoundWorkingSuccessfully() {
        Assertion.assertTrue((response.getStatusCode()==404),"Response code is not 404");
        return this;
    }
}
