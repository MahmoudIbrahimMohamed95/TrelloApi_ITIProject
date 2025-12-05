package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class SearchApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion;

    public SearchApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints
    private static final String Search_endpoint = "search";

    public SearchApi SearchTrello(String query){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("query",query);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .get(Search_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public SearchApi SearchForMembers(String query){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("query",query);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .get(Search_endpoint+"/members/");
        Log.info(response.asPrettyString());
        return this;
    }


    public  SearchApi verifySearchOnBoard(String boardId) {
        Assertion.assertEquals(response.jsonPath().getString("boards.id[0]"),boardId,"Error on searchBoardId Not Found");
        return this;
    }
    public  SearchApi verifySearchOnOrganization(String spaceId) {
        Assertion.assertEquals(response.jsonPath().getString("organizations.id[0]"),spaceId,"Error on search space Not Found");
        return this;
    }
}
