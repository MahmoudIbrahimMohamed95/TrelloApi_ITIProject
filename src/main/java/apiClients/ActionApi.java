package apiClients;
import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.Assertion;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class ActionApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion;

    public ActionApi() {
        requestSpecification = RestAssured.given();
        Assertion=new Assertions();
    }

    //endpoints
    private static final String Actions_endpoint = "actions/";

    public Response getResponse() {
        return response;
    }

    public ActionApi getAction(String actionId)
    {
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Actions_endpoint+actionId);
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi getSpecificAction(String id,String field ) {
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Actions_endpoint+id+"/"+field);
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi getOrganizationForAction(String actionId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Actions_endpoint+actionId+"/organization");
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi getBoardForAction(String actionId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Actions_endpoint+actionId+"/board");
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi updateAction(String actionId, String text){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("text",text);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(Actions_endpoint+actionId);
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi deleteAction(String actionId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Actions_endpoint+actionId);
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi createReactionForAction(String idAction)
    {
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Actions_endpoint+idAction+"/reactions");
        Log.info(response.asPrettyString());
        return this;
    }

    public ActionApi getReactionOfAction(String idAction, String idReaction){
     response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Actions_endpoint+idAction+"/reactions/"+idReaction);
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi deleteReactionOfAction(String idAction, String idReaction) {
        response = requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Actions_endpoint + idAction + "/reactions/" + idReaction);
        Log.info(response.asPrettyString());
        return this;
    }
    public ActionApi verifyActionField(String cardId,String text){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
               .assertEquals(response.jsonPath().get("idCard"), cardId,"Card Comment is not updated successfully")
                .assertEquals(response.jsonPath().get("text"), text,"Card Comment is not updated successfully");

        return this;
    }


    public ActionApi verifyActionFieldAfterDeleting() {
    Assertion.assertTrue(response.getStatusCode() == 404,"Response code is not 404");
    return this;
    }

    public ActionApi verifyUpdatingAction(String commentId, String text) {

        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), commentId,"Card Comment is not updated successfully")
                .assertEquals(response.jsonPath().get("data.text"), text,"Card Comment is not updated successfully");
    return this;
    }
    public ActionApi verifyRetrivingAfterUpdatingAction(String commentId, String text) {

        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), commentId,"Card Comment is not updated successfully")
                .assertEquals(response.jsonPath().get("data.text"), text,"Card Comment is not updated successfully");
        return this;
    }

    public ActionApi verifydeleteAction() {
    Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
    return this;
    }
    public ActionApi verifyRetriveAfterdeleteAction() {
        Assertion.assertTrue(response.getStatusCode() == 404,"Response code is not 404");
        return this;
    }
}