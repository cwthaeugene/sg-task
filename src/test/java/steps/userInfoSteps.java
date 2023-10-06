package steps;

import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.json.JSONObject;


public class userInfoSteps{
    public userInfoSteps()
    {
        RestAssured.baseURI = "https://ayco69dbm3.execute-api.us-east-1.amazonaws.com/singular_qa";
    }
    Response infoResponse;
    String token;
    @Given("user information api is available")
    public void user_information_api_is_available() {

    }

    @Then("we send a post request to \\/authorize endpoint with username and password credentials to gain and save the token")
    public void we_send_a_post_request_to_authorize_endpoint_with_username_and_password_credentials_to_gain_and_save_the_token() {
        JSONObject loginData = new JSONObject();
        loginData.put("username", "your_username");
        loginData.put("password", "your_password");
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(loginData.toString())
                        .when().post("/authorize");
        token = response.jsonPath().getString("token");
        assertEquals(response.jsonPath().getString("code"), "10");
        assertEquals(response.jsonPath().getString("message"), "success");

    }

    @Then("we send another get request to \\/info endpoint with the gained token in the authorization header")
    public void we_send_another_get_request_to_info_endpoint_with_the_gained_token_in_the_authorization_header() {
        infoResponse =
                given()
                        .header("Authorization",token)
                        .when().get("/info");
    }

    @Then("response status code is {int}")
    public void response_status_code_is(Integer int1) {
        assertEquals(infoResponse.getStatusCode(), 200);
    }

    @Then("the response returns user info")
    public void the_response_json_is_false() {
        assertEquals(infoResponse.jsonPath().getString("code"),"10");
        assertEquals(infoResponse.jsonPath().getString("message"),"SUCCESS");
        assertEquals(infoResponse.jsonPath().getString("data.name"),"John");
        assertEquals(infoResponse.jsonPath().getString("data.surname"),"Doe");
        assertEquals(infoResponse.jsonPath().getString("data.age"),"30");
        assertEquals(infoResponse.jsonPath().getString("data.gender"),"1");
        assertEquals(infoResponse.jsonPath().getString("data.language"),"en");
        assertEquals(infoResponse.jsonPath().getString("data.status"),"registered");
        assertEquals(infoResponse.jsonPath().getString("data.isBlocked"),"false");
    }

    @Then("we send get request to \\/info endpoint")
    public void send_get_request_to_info_endpoint() {
        infoResponse = given().when().get("/info");
    }
}
