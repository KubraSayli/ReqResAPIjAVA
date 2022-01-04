package stepDefinitions;

import io.cucumber.java.en.Given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseUrlReq {
    public static RequestSpecification baseUrl;
    @Given("I set the url")
    public void i_set_the_url() {
        baseUrl = new RequestSpecBuilder().setBaseUri("https://reqres.in/api/").build();
    }

}
