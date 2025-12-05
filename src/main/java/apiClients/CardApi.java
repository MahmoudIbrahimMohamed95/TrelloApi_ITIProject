package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class CardApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion;

    public CardApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints

    private static final String Cards_endpoint = "cards";

    public Response getResponse() {
        return response;
    }

    public CardApi createCard(String listId, String name)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        queryParams.put("idList",listId);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Cards_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public CardApi getCard(String cardId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Cards_endpoint+"/"+cardId);
        Log.info(response.asPrettyString());
        return this;
    }
    public CardApi updateCard(String cardId, String displayName){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("name",displayName);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(Cards_endpoint+"/"+cardId);
        Log.info(response.asPrettyString());
        return this;
    }

    public CardApi deleteCard(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Cards_endpoint+"/"+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public CardApi commentOnCard(String cardId, String text)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("text",text);
        response =requestSpecification
                 .spec(RequestBuilder.getRequestSpecification(queryParams))
                 .post(Cards_endpoint+"/"+cardId+"/actions/comments");
        Log.info(response.asPrettyString());
        return this;
    }
    public CardApi deleteCommentOnCard(String cardId, String commentId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Cards_endpoint+"/"+cardId+"/actions/"+commentId+"/comments");
        Log.info(response.asPrettyString());
        return this;

    }
    public CardApi getCommentActionsOnCard(String cardId){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("filter","commentCard");
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .get(Cards_endpoint+"/"+cardId+"/actions");
        Log.info(response.asPrettyString());
        return this;


    }
    public CardApi getListOfCard(String cardId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", cardId);
        response = requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .get(Cards_endpoint+"/"+cardId+"/list");
        Log.info(response.asPrettyString());
        return this;
    }
    public CardApi verifyCardCreated(String cardName,String idList){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("name"), cardName,"List is not created successfully")
                .assertEquals(response.jsonPath().get("idList"), idList,"Board is not created successfully");
        return this;
    }

    public CardApi verifyCardretriving(String cardId , String cardName,String idList) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), cardId,"Card is not created successfully")
                .assertEquals(response.jsonPath().get("name"), cardName,"Card is not created successfully")
                .assertEquals(response.jsonPath().get("idList"),idList,"Card is not created successfully");

        return this;
    }

    public CardApi verifyCardUpdating(String cardId , String cardName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), cardId,"Card is not created successfully")
                .assertEquals(response.jsonPath().get("name"), cardName,"Card is not created successfully");
        return this;
    }
    public CardApi verifyCardRetrivingAfterUpdating(String cardId , String cardName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), cardId,"Card is not updated successfully")
                .assertEquals(response.jsonPath().get("name"), cardName,"Card is not updated successfully");
        return this;
    }

    public CardApi verifyCardDeleted() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
        return this;
    }
    public CardApi verifyCardRetrivingAfterDeleting() {
        Assertion.assertTrue(response.getStatusCode()==404,"Response code is not 204");
        return this;
    }
    public CardApi verifyCommentOnCard(String cardId, String comment){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("data.card.id"), cardId,"Card Comment is not updated successfully")
                .assertEquals(response.jsonPath().get("data.text"), comment,"Card Comment is not updated successfully");
        return this;
    }
    public CardApi verifyCardCommentDeleted(){
        Assertion.assertTrue(response.getStatusCode()==200,"Response code is not 200");
        return this;
    }

    public CardApi verifyRetrivingComments(String cardId,String text) {
        Assertion.assertEquals(response.jsonPath().get("[0].data.idCard"),cardId,"Card id of comment is not same as expected")
                 .assertEquals(response.jsonPath().get("[0].data.text"),text,"Text of comment is not same as expected");
        return this;
    }

    public CardApi verifyCardNotFound() {
        Assertion.assertTrue(response.getStatusCode()==404,"Response code is not 404");
        return this;
    }
}
