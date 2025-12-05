package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class BoardApi {
    private RequestSpecification requestSpecification;
    private Response response;
    private Assertions Assertion;

    public BoardApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    public Response getResponse() {
        return response;
    }
    //endpoints
    private static final String Boards_endpoint = "boards/";

    //@Step("Create a new user account with full details")
    public BoardApi createBoard(String name, String idOrganization, String powerUps)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        queryParams.put("idOrganization",idOrganization);
        queryParams.put("powerUps",powerUps);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Boards_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi createBoard(String name, String idOrganization)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        queryParams.put("idOrganization",idOrganization);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Boards_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi enablePowerUpOnBoard(String boardId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .post(Boards_endpoint+boardId+"/boardPlugins");
        Log.info(response.asPrettyString());
        return this;
    }

    public BoardApi getBoard(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Boards_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi updateBoard(String id, String name){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(Boards_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi deleteBoard(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Boards_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi getActionsOfBoard(String boardId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Boards_endpoint+boardId+"/actions");
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi getMembersOfBoard(String boardId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Boards_endpoint+boardId+"/members");
        Log.info(response.asPrettyString());
        return this;
    }
    public BoardApi verifyBoardCreated(String boardName,String idOrganization){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("name"), boardName,"Board is not created successfully")
                .assertEquals(response.jsonPath().get("idOrganization"), idOrganization,"Board is not created successfully");
        return this;
    }

    public BoardApi verifyBoardretriving(String boardId , String boardName,String idOrganization) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), boardId,"Board is not created successfully")
                .assertEquals(response.jsonPath().get("name"), boardName,"Board is not created successfully")
                .assertEquals(response.jsonPath().get("idOrganization"),idOrganization,"Board is not created successfully");

        return this;
    }

    public BoardApi verifyBoardUpdating(String boardId , String boardName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), boardId,"Board is not created successfully")
                .assertEquals(response.jsonPath().get("name"), boardName,"Board is not created successfully");
        return this;
    }
    public BoardApi verifyBoardRetrivingAfterUpdating(String boardId , String name) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), boardId,"Board is not updated successfully")
                .assertEquals(response.jsonPath().get("name"), name,"Board is not updated successfully");
        return this;
    }

    public BoardApi verifyBoardDeleted() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
        return this;
    }
    public BoardApi verifyBoardRetrivingAfterDeleting() {
        Assertion.assertTrue(response.getStatusCode() == 404,"Response code is not 404");
        return this;
    }
    public BoardApi verifyNonExistingBoardManipulating(){
        Assertion.assertTrue(response.getStatusCode() == 400,"Response code is not 400");
        return this;
    }
}

