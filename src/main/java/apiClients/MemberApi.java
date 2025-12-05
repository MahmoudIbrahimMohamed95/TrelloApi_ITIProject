package apiClients;

import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class MemberApi {
    RequestSpecification requestSpecification;
    Response response;
    //   Verification verification;

    public MemberApi() {
        requestSpecification = RestAssured.given();
        //     verification = new Verification();
    }

    //endpoints
    private static final String Members_endpoint = "members/";
    public MemberApi getMember(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Members_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public MemberApi updateMember(String id, String fullName){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("fullName",fullName);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(Members_endpoint+id);
        Log.info(response.asPrettyString());
        return this;
    }
    public MemberApi getFieldOnMember(String id ,String field){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Members_endpoint+id+"/field");
        Log.info(response.asPrettyString());
        return this;
    }
}
