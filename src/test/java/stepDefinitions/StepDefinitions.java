package stepDefinitions;

import io.cucumber.java.an.E;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StepDefinitions {
    Response response;
    Integer actualStatusCode;
    Map<String, Object> requestBody;
    Map<String, Object> expectedResponseBody;
    JsonPath json;

    @When("I send get request {string} for path parameters {string} and {string} for {string}")
    public void i_send_get_request_for_path_parameters_and_for(String pathParName1, String pathParam1, String pathParName2, String pathParam2) {
        BaseUrlReq.baseUrl.pathParams(pathParName1, pathParam1,
                pathParName2, pathParam2);
        String pathParameterForGroovy = String.format("{%s}/{%s}", pathParName1, pathParName2);
        response = given().spec(BaseUrlReq.baseUrl).when().get(pathParameterForGroovy);
        response.prettyPrint();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer expectedStatusCode) {
        actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("I verify if data from GET body matches with data from response {string} for {string} and {string} for {string}")
    public void i_verify_if_data_from_GET_body_matches_with_data_from_response_for_and_for(String jsonPath1, String testData1, String jsonPath2, String testData2) {
        if (jsonPath2.equals("data.id")) {
            response.then().assertThat().body(jsonPath1, equalTo(testData1),
                    jsonPath2, equalTo(Integer.valueOf(testData2)));
        } else {
            response.then().assertThat().body(jsonPath1, equalTo(testData1),
                    jsonPath2, equalTo(testData2));
        }
    }

    @When("I set the expected data for request and response {string} and {string}")
    public void i_set_the_expected_data_for_request_and_response_and(String path, String path2) throws Exception {
        // parsing file
        Object obj = new JSONParser().parse(new FileReader(path));
        Object obj2 = new JSONParser().parse(new FileReader(path2));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
        JSONObject jo2 = (JSONObject) obj2;
        requestBody = (Map<String, Object>) jo.get("data");
        expectedResponseBody = (Map<String, Object>) jo2.get("data");
    }

    @When("I send the post request with path parameter name {string} for {string}")
    public void i_send_the_post_request_with_path_parameter_name_for(String pathParName1, String pathParam1) {
        BaseUrlReq.baseUrl.pathParams(pathParName1, pathParam1);
        String postUrlPath = String.format("{%s}", pathParName1);

        response = given().
                contentType(ContentType.JSON).
                spec(BaseUrlReq.baseUrl).
                body(requestBody).
                when().
                post(postUrlPath);
        actualStatusCode = response.statusCode();
        response.prettyPrint();
    }

    @Then("I verify if data from POST body matches with data from response {string} and {string}")
    public void i_verify_if_data_from_POST_body_matches_with_data_from_response_and(String data1, String data2) {
        json = response.jsonPath();
        String data = "";
        if (data1.equals("id")) {
            Assert.assertEquals(expectedResponseBody.get(data1), (json.getLong(data1)));
        } else {
            Assert.assertEquals(expectedResponseBody.get(data1), (json.getString(data1)));
            Assert.assertEquals(expectedResponseBody.get(data2), json.getString(data2));
        }
    }

    @Then("I verify if data from POST body matches with data from response {string}")
    public void i_verify_if_data_from_POST_body_matches_with_data_from_response(String data) {
        json = response.jsonPath();
        Assert.assertEquals(expectedResponseBody.get(data), json.getString(data));
    }


    @When("I send GET request to the endpoint with path parameter name as {string} for {string}  and {string} is {string} for query parameters")
    public void i_send_GET_request_to_the_endpoint_with_path_parameter_name_as_for_and_is_for_query_parameters(String pathParName, String pathPar, String queryName, String query) {
        BaseUrlReq.baseUrl.pathParams(pathParName, pathPar).queryParam(queryName, query);
        String getUrl = String.format("{%s}", pathParName);
        response = given().spec(BaseUrlReq.baseUrl).when().get(getUrl);
        response.prettyPrint();
    }

    @Then("the number of data should be {int} for each page")
    public void the_number_of_data_should_be_for_each_page(Integer size) {
        json = response.jsonPath();
        List<String> allData = json.getList("data");
        Assert.assertEquals(size, (Integer) allData.size());
    }

    @Then("all users have avatar")
    public void all_users_have_avatar() {
        List<Map<String, Object>> allData = json.getList("data");
        for (Map<String, Object> each : allData) {
            assertFalse(each.equals(" "));
        }
    }

    @Then("verify the data for eleventh user")
    public void verify_the_data_for_eleventh_user(List<List<String>> dataTable) {
        json = response.jsonPath();
        for (int i = 0; i < dataTable.size(); i++) {
            if (json.get(dataTable.get(i).get(0)).equals("id")) {
                assertEquals(dataTable.get(i).get(1), json.getInt(dataTable.get(i).get(0)));
            } else {
                assertEquals(dataTable.get(i).get(1), json.getString(dataTable.get(i).get(0)));
            }
        }
    }
}
