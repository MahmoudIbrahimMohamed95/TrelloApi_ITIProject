package apiClients;

import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

public class EmojiApi {
    RequestSpecification requestSpecification;
    Response response;
    //   Verification verification;

    public EmojiApi() {
        requestSpecification = RestAssured.given();
        //     verification = new Verification();
    }

    //endpoints
    private static final String Emojis_endpoint = "emoji";
    public EmojiApi listAvailableEmojis() {
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Emojis_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }

}
