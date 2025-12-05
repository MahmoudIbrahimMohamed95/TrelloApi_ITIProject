package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class WebhooksApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion ;
    public WebhooksApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints
    private static final String Webhooks_endpoint = "webhooks/";

    public Response getResponse() {
        return response;
    }
    //@Step("Create a new user account with full details")
    public WebhooksApi createWebhook(String callbackURL, String idModel)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("callbackURL",callbackURL);
        queryParams.put("idModel",idModel);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(Webhooks_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public WebhooksApi getWebhook(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Webhooks_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public WebhooksApi updateWebhook(String id,String idModel){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("idModel",idModel);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(Webhooks_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public WebhooksApi deleteWebhook(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Webhooks_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }

    public WebhooksApi verifyWebhookCreated(String callbackReference , String idModel) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("idModel"),idModel,"Webhook is not created successfully")
                .assertEquals(response.jsonPath().get("callbackURL"), callbackReference,"Webhook is not created successfully");
        return this;
    }

    public WebhooksApi verifyWebhookRetrivedSuccessfully(String id,String callbackReference, String idModel) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"),id,"Webhook is not created successfully")
                .assertEquals(response.jsonPath().get("idModel"),idModel,"Webhook is not created successfully")
                .assertEquals(response.jsonPath().get("callbackURL"), callbackReference,"Webhook is not created successfully");
        return this;
    }

    public WebhooksApi verifyWebhookUpdatedSuccessfully(String webhookId, String workspaceId) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"),webhookId,"Webhook is not updated successfully")
                .assertEquals(response.jsonPath().get("idModel"),workspaceId,"Webhook is not updated successfully");
        return this;
    }

    public WebhooksApi verifyWebhookDeletedSuccessfully() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
    return this;
    }
    public WebhooksApi verifyWebhookRetrivingAfterDeleting() {
        Assertion.assertTrue(response.getStatusCode() == 404,"Response code is not 404");
        return this;
    }
}
