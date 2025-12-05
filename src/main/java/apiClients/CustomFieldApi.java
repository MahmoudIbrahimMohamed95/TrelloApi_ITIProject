package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class CustomFieldApi {
    private RequestSpecification requestSpecification;
    private Response response;
    private Assertions Assertion;
    public CustomFieldApi() {
        requestSpecification = RestAssured.given();
       Assertion = new Assertions();
    }

    //endpoints
    private static final String customFields_endpoint = "customFields";

    //@Step("Create a new user account with full details")
    public CustomFieldApi createCustomFieldOnBoard(String idModel, String modelType, String name
                                                    ,String type, String pos)
    {
        Map<String, String> body = new HashMap<>();
        body.put("idModel",idModel);
        body.put("modelType",modelType);
        body.put("name",name);
        body.put("type",type);
        body.put("pos",pos);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .body(body)
                .post(customFields_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public CustomFieldApi createCustomFieldOnBoardWithMissingParameter( String modelType, String name
            ,String type, String pos)
    {
        Map<String, String> body = new HashMap<>();
        body.put("modelType",modelType);
        body.put("name",name);
        body.put("type",type);
        body.put("pos",pos);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .body(body)
                .post(customFields_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public CustomFieldApi getCustomField(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(customFields_endpoint+"/"+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public CustomFieldApi updateCustomField(String id, String name){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(customFields_endpoint+"/"+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public CustomFieldApi deleteCustomField(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(customFields_endpoint+"/"+id);
        Log.info(response.asPrettyString());
        return this;
    }

    public CustomFieldApi verifyErrorMissingBodyMessage(String message) {
        Assertion.assertEquals(response.jsonPath().get("message"), message, "error message incorrect")
                        .assertTrue(response.getStatusCode()==400, "status code incorrect");

        return this;
    }
}
