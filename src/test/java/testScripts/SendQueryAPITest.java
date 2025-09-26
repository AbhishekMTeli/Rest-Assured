package testScripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class SendQueryAPITest extends BaseTest {

	@Test(description = "Validate send-query API with valid form data request")
	public void testSendQueryWithValidFormData() {
		String userQueryJson = "{" + "\"topic\": \"Testing againinni\"," + "\"query\": \"Test query message\","
				+ "\"slideNo\": \"3\"," + "\"priority\": 2," + "\"adminResponse\": \"Response pending\","
				+ "\"responded\": false," + "\"staffNumber\": \"J1234\"," + "\"roles\": \"Administrator\"" + "}";

		Response response = given().spec(requestSpecAuthMultipart).multiPart("userQuery", userQueryJson)
				.post("/send-query");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 200);
		response.then().body(equalTo("Data uploaded successfully"));
	}

	@Test(description = "Validate send-query API returns error when userQuery is missing")
	public void testSendQueryMissingFormData() {
		Response response = given().spec(requestSpecAuth).post("/send-query");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.validateFieldExists(response, "error");
	}

	@Test(description = "Validate send-query API rejects invalid JSON in userQuery form-data")
	public void testSendQueryInvalidJsonFormData() {
		String invalidJson = "{\"topper\": \"Testing againinni\" }";
		Response response = given().spec(requestSpecAuthMultipart).multiPart("userQuery", invalidJson)
				.post("/send-query");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Validate send-query API rejects empty JSON in userQuery form-data")
	public void testSendQueryEmptyJsonFormData() {
		String invalidJson = "{ }";

		Response response = given().spec(requestSpecAuthMultipart).multiPart("userQuery", invalidJson)
				.post("/send-query");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
		response.then().body(equalTo("Topic is null"));
	}
}
