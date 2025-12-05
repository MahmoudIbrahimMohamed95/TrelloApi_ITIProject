package apiClients;

import assertion.Assertions;
import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class CheckListApi {
    RequestSpecification requestSpecification;
    Response response;
    Assertions Assertion;

    private static final String CheckLists_endpoint = "checklists";

    public CheckListApi() {
        requestSpecification = RestAssured.given();
        Assertion = new Assertions();
    }

    //endpoints


    public Response getResponse() {
        return response;
    }
    //
    public CheckListApi createCheckList(String cardId,String name)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name",name);
        queryParams.put("idCard",cardId);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .post(CheckLists_endpoint);
        Log.info(response.asPrettyString());
        return this;
    }
    public CheckListApi getCheckList(String checkListId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(CheckLists_endpoint+"/"+checkListId);
        Log.info(response.asPrettyString());
        return this;
    }
    public CheckListApi updateCheckList(String checkListId, String displayName){
        Map <String, Object> queryParams = new HashMap<>();
        queryParams.put("name",displayName);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification(queryParams))
                .put(CheckLists_endpoint+"/"+checkListId);
        Log.info(response.asPrettyString());
        return this;
    }

    public CheckListApi deleteCheckList(String id){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .delete(CheckLists_endpoint+"/"+id);
        Log.info(response.asPrettyString());
        return this;
    }

    public CheckListApi verifyCheckListCreated(String checkListName,String idCard){
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("name"), checkListName,"checkList is not created successfully")
                .assertEquals(response.jsonPath().get("idCard"), idCard,"checkList is not created successfully");
        return this;
    }

    public CheckListApi verifyCheckListretriving(String checkListId , String checkListName,String idCard) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), checkListId,"CheckList is not created successfully")
                .assertEquals(response.jsonPath().get("name"), checkListName,"CheckListis not created successfully")
                .assertEquals(response.jsonPath().get("idCard"),idCard,"CheckList is not created successfully");

        return this;
    }

    public CheckListApi verifyCheckListUpdating(String checkListId , String checkListName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), checkListId,"CheckList is not created successfully")
                .assertEquals(response.jsonPath().get("name"), checkListName,"CheckList is not created successfully");
        return this;
    }
    public CheckListApi verifyCheckListRetrivingAfterUpdating(String checkListId , String checkListName) {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200")
                .assertEquals(response.jsonPath().get("id"), checkListId,"CheckList is not updated successfully")
                .assertEquals(response.jsonPath().get("name"), checkListName,"CheckList is not updated successfully");
        return this;
    }

    public CheckListApi verifyCheckListDeleted() {
        Assertion.assertTrue(response.getStatusCode() == 200,"Response code is not 200");
        return this;
    }
    public CheckListApi verifyCheckListRetrivingAfterDeleting() {
        Assertion.assertTrue(response.getStatusCode()==404,"Response code is not 204");
        return this;
    }

}