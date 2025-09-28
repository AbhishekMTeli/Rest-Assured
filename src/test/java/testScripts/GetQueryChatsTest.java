package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.Q_E_F_Endpoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class GetQueryChatsTest extends BaseTest {

	@Test(description = "Verify Get Query Chats API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyQueryResponseAPI_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = Q_E_F_Endpoints.getQueryChats(true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "queryId");
		ApiUtils.validateFieldExists(response, "staffNumber");
		ApiUtils.validateFieldExists(response, "message");
	}

	@Test(description = "Verify Get Query Chats API returns 401 Unauthorized for invalid authentication token")
	public void verifyGetQueryChatsAPI_WithInvalidAuth_ShouldReturn401() {
		Response response = Q_E_F_Endpoints.getQueryChats(true, "ovverride token");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get Query Chats API returns 401 Unauthorized when no authentication is provided")
	public void verifyGetQueryChatsAPI_WithoutAuth_ShouldReturn401() {
		Response response = Q_E_F_Endpoints.getQueryChats(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get Query Chats API response matches the expected JSON schema")
	public void verifyGetQueryChatsAPI_ResponseMatchesJsonSchema() {
		Response response = Q_E_F_Endpoints.getQueryChats(true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify Get Query Chats API returns 401 Unauthorized for expired token")
	public void verifyGetQueryChats_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = Q_E_F_Endpoints.getQueryChats(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}
}
