package apiClients;

import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

public class TokensApi {
    RequestSpecification requestSpecification;
    Response response;
    //   Verification verification;

    public TokensApi() {
        requestSpecification = RestAssured.given();
        //     verification = new Verification();
    }

    //endpoints
    private static final String Tokens_endpoint = "tokens/";

    public TokensApi getToken(String token){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Tokens_endpoint+token);
        Log.info(response.asPrettyString());
        return this;
    }
    public TokensApi getTokensMember(String token){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Tokens_endpoint+token+"/member");
        Log.info(response.asPrettyString());
        return this;
    }
    public TokensApi getTokensWebhooks(String token){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Tokens_endpoint+token+"/webhooks");
        Log.info(response.asPrettyString());
        return this;
    }
    public TokensApi updateWebhookCreatedByToken(String token, String idWebhook){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .put(Tokens_endpoint+token+"/webhooks/"+idWebhook);
        Log.info(response.asPrettyString());
        return this;
    }
    public TokensApi deleteWebhookCreatedByToken(String token, String idWebhook){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Tokens_endpoint+token+"/webhooks/"+idWebhook);
        Log.info(response.asPrettyString());
        return this;
    }
    public TokensApi deleteToken(String token){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(Tokens_endpoint+token+"/");
        Log.info(response.asPrettyString());
        return this;
    }
}
