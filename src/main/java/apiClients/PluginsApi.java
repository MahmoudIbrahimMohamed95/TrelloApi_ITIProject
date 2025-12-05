package apiClients;

import configFactory.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import readers.Log;

import java.util.HashMap;
import java.util.Map;

public class PluginsApi {
    RequestSpecification requestSpecification;
    Response response;
    //   Verification verification;

    public PluginsApi() {
        requestSpecification = RestAssured.given();
        //     verification = new Verification();
    }

    //endpoints
    private static final String Plugins_endpoint = "plugins/";

    public PluginsApi getPlugin(String organizationId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .get(Plugins_endpoint+organizationId+"/");
        Log.info(response.asPrettyString());
        return this;
    }
    public PluginsApi updatePlugin(String organizationId){
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .put(Plugins_endpoint+organizationId+"/");
        Log.info(response.asPrettyString());
        return this;
    }
    public PluginsApi createListingForPlugin(String idPlugin,String description, String locale
                                            , String overview, String name)
    {
        Map <String, String> body = new HashMap<>();
        body.put("idPlugin",idPlugin);
        body.put("description",description);
        body.put("locale",locale);
        body.put("overview",overview);
        body.put("name",name);
        response =requestSpecification
                .spec(RequestBuilder.getRequestSpecification())
                .body(body)
                .post(Plugins_endpoint+idPlugin+"/listing");
        Log.info(response.asPrettyString());
        return this;
    }
}
