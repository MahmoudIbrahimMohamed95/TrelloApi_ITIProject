package configFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import readers.PropertyReader;

import java.util.Map;

public class RequestBuilder {

        private static final String baseURI= PropertyReader.getProperty("baseUrlApi");
        private static final String apikey=  PropertyReader.getProperty("api_key");
        private static final String apitoken=PropertyReader.getProperty("api_token");

       public static RequestSpecification getRequestSpecification() {
           return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .addHeader("Accept","application/json")
                .addQueryParam("key", apikey)
                .addQueryParam("token", apitoken)
                .build();
    }
       public static RequestSpecification getRequestSpecification(Map<String, Object> queryParams){
                return new RequestSpecBuilder()
                        .setBaseUri(baseURI)
                        .setContentType(ContentType.URLENC)
                        .addHeader("Accept", "application/json")
                        .addQueryParam("key", apikey)
                        .addQueryParam("token", apitoken)
                        .addQueryParams(queryParams)
                        .build();
       }
    public static RequestSpecification getunAuthorizedRequestSpecification(Map<String, Object> queryParams){
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.URLENC)
                .addHeader("Accept", "application/json")
                .addQueryParam("key", apikey)
                .addQueryParams(queryParams)
                .build();
    }
}
