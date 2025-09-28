package testScripts;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class PostQueryChatTest extends BaseTest {
	@Test(description = "Validate chat API with valid JSON data and file attachment")
	public void testChatApiWithAttachment() {
		String jsonData = "{" + "\"staffNumber\": \"123456\"," + "\"roles\": \"Trainee\","
				+ "\"message\": \"helloooooooooouiooooo\"" + "}";

		String attachmentFilePath = "/Users/ggipl/Desktop/LearnOnRestAssured/src/test/resources/downloads/downloadedResource.png";

		Response response = given().spec(requestSpecAuthMultipart).multiPart("data", jsonData)
				.multiPart("attachments", new java.io.File(attachmentFilePath)).post("/chat/281");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 200);

		// Example: Validate response contains success message or chat ID (adjust as per
		// API)
		String actualResponse = response.asString();
		assert actualResponse.toLowerCase().contains("message sent successfully")
				: "Expected success indication not found in response.";
	}

	@Test(description = "Validate chat API rejects request without data form field")
	public void testChatApiMissingData() {
		String attachmentFilePath = "/Users/ggipl/Desktop/LearnOnRestAssured/src/test/resources/downloads/downloadedResource.png";

		Response response = given().spec(requestSpecAuthMultipart)
				.multiPart("attachments", new java.io.File(attachmentFilePath)).post("/chat/281");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.validateField(response, "message",
				"Required request parameter 'data' for method parameter type String is not present");
	}

	@Test(description = "Validate chat API rejects malformed JSON in data form field")
	public void testChatApiInvalidJsonData() {
		String invalidJson = "{ staffNumber: 123456 roles: Trainee message: 'text' }";

		Response response = given().spec(requestSpecAuthMultipart).multiPart("data", invalidJson).post("/chat/281");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400); // Or expected error code
		String actualResponse = response.asString();
		assert actualResponse.toLowerCase().contains("Roles cannot be null")
				|| actualResponse.toLowerCase().contains("error") : "Expected error message not found in response.";
	}

	@Test(description = "Validate chat API accepts request without attachment when not required")
	public void testChatApiMissingAttachment() {
		String jsonData = "{" + "\"staffNumber\": \"123456\"," + "\"roles\": \"Trainee\","
				+ "\"message\": \"helloooooooo\"" + "}";

		Response response = given().spec(requestSpecAuthMultipart).multiPart("data", jsonData).post("/chat/281");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 200);

		String actualResponse = response.asString();
		assert actualResponse.toLowerCase().contains("message sent successfully")
				: "Expected success message not found in response. Actual: " + actualResponse;
	}

	@Test(description = "Validate chat API returns error when 'message' field is missing in data")
	public void testChatApiMissingMessageField() {
		// JSON payload without the "message" field
		String jsonData = "{" + "\"staffNumber\": \"123456\"," + "\"roles\": \"Trainee\"" + "}";

		Response response = given().spec(requestSpecAuthMultipart).multiPart("data", jsonData).post("/chat/281");

		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400); // or appropriate error status

		String actualResponse = response.asString();
		assert actualResponse.toLowerCase().contains("message cannot be null")
				: "Expected 'message cannot be null' error not found. Actual: " + actualResponse;
	}

}
