package apiClients;
import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class ListApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion;
    public ListApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints

    private static final String Lists_endpoint = "lists";
    public ListApi createList(String boardId,String name)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        queryParams.put("idBoard",boardId);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Lists_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public ListApi getList(String listId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Lists_endpoint+"/"+listId);
        Log.info(response.asPrettyString());
        return this;
    }
    public ListApi updateList(String listId, String name){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(Lists_endpoint+"/"+listId);
        Log.info(response.asPrettyString());
        return this;
    }
    public ListApi updateListusingGet(String listId, String name) {
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Lists_endpoint+"/"+listId);
        Log.info(response.asPrettyString());
        return this;
    }
    public ListApi verifyListCreated(String listName,String idBoard){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("name"), listName,"List is not created successfully")
                .assertEquals(response.jsonPath().get("idBoard"), idBoard,"Board is not created successfully");
        return this;
    }

    public ListApi verifyListretriving(String ListId , String listName,String idBoard) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), ListId,"List is not created successfully")
                .assertEquals(response.jsonPath().get("name"), listName,"List is not created successfully")
                .assertEquals(response.jsonPath().get("idBoard"),idBoard,"List is not created successfully");

        return this;
    }

    public ListApi verifyListUpdating(String listId , String listName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), listId,"List is not created successfully")
                .assertEquals(response.jsonPath().get("name"), listName,"List is not created successfully");
        return this;
    }

    public ListApi verifyListRetrivingAfterUpdating(String listId , String listName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), listId,"List is not updated successfully")
                .assertEquals(response.jsonPath().get("name"), listName,"List is not updated successfully");
        return this;
    }

    public ListApi verifyListDeleted() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
        return this;
    }
    public ListApi verifyListRetrivingAfterDeleting() {
        Assertion.assertTrue(response.getStatusCode()==404,"Response code is not 204");
        return this;
    }

    public ListApi verifyInvalidListUpdating(String listId , String listName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), listId,"List is not created successfully")
                .assertEquals(response.jsonPath().get("name"), listName,"List is not created successfully");
        return this;
    }


    public ListApi verifyListApiResponse(String listId) {
        Assertion.assertNotEquals(response.jsonPath().get("id"), listId,"List is  same");
        return this;
    }
}
