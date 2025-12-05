package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class LabelApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion;
    public LabelApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints

    private static final String Labels_endpoint = "labels";

    public Response getResponse() {
        return response;
    }

    public LabelApi createLabel(String boardId, String name, String color) {

            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("name", name);
            queryParams.put("color", color);
            queryParams.put("idBoard", boardId);
            response = requestSpecification
                    .spec(RequestBuilder.getRequestSpecification(queryParams))
                    .post(Labels_endpoint);
            Log.info(response.asPrettyString());
            return this;
        }

    public LabelApi createLabelWithMissingParameter(String boardId, String color) {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("color", color);
        queryParams.put("idBoard", boardId);
        response = requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Labels_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }

    public LabelApi getLabel(String labelId){
            response = requestSpecification
                    .spec(RequestBuilder.getRequestSpecification())
                    .get(Labels_endpoint + "/" + labelId);
            Log.info(response.asPrettyString());
            return this;
        }
        public LabelApi updateLabel(String labelId, String displayName){
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("name", displayName);
            response = requestSpecification
                    .spec(RequestBuilder.getRequestSpecification(queryParams))
                    .put(Labels_endpoint + "/" + labelId);
            Log.info(response.asPrettyString());
            return this;
        }

        public LabelApi deleteLabel(String id){
            response = requestSpecification
                    .spec(RequestBuilder.getRequestSpecification())
                    .delete(Labels_endpoint + "/" + id);
            Log.info(response.asPrettyString());
            return this;
        }
    public LabelApi verifyLabelCreated(String labelName,String idBoard){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("name"), labelName,"label is not created successfully")
                .assertEquals(response.jsonPath().get("idBoard"), idBoard,"label is not created successfully");
        return this;
    }

    public LabelApi verifyLabelRetriving(String labelId , String labelName,String idBoard) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), labelId,"label is not created successfully")
                .assertEquals(response.jsonPath().get("name"), labelName,"label is not created successfully")
                .assertEquals(response.jsonPath().get("idBoard"),idBoard,"label is not created successfully");

        return this;
    }

    public LabelApi verifyLabelUpdating(String labelId , String labelName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), labelId,"Label is not created successfully")
                .assertEquals(response.jsonPath().get("name"), labelName,"Label is not created successfully");
        return this;
    }
    public LabelApi verifyLabelRetrivingAfterUpdating(String labelId , String name) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), labelId,"Label is not updated successfully")
                .assertEquals(response.jsonPath().get("name"), name,"Label is not updated successfully");
        return this;
    }

    public LabelApi verifyLabelDeleted() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
        return this;
    }
    public LabelApi verifyLabelRetrivingAfterDeleting() {
        Assertion.assertTrue(response.getStatusCode() == 404,"Response code is not 403");
        return this;
    }

    public LabelApi verifyBadRequestError() {
        Assertion.assertTrue(response.getStatusCode()==400,"Response code is not 400");
        return this;
    }
}