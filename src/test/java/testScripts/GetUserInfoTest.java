package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class GetUserInfoTest extends BaseTest {
	@Test(description = "Verify Get User Info API returns 200 OK and valid response fields when valid authentication and parameters are provided")
	public void verifyGetUserInfo_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = UserEndPoints.getUserInfo(true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "name");
		ApiUtils.validateNotNull(response, "email");
	}

	@Test(description = "Verify Get User Info API returns 401 Unauthorized for invalid authentication token")
	public void verifyGetUserInfo_WithInvalidAuth_ShouldReturn401() {
		Response response = UserEndPoints.getUserInfo(true, "invalidToken");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get User Info API returns 401 Unauthorized when no authentication token is provided")
	public void verifyGetUserInfo_WithoutAuth_ShouldReturn401() {
		Response response = UserEndPoints.getUserInfo(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get User Info API returns 401 Unauthorized when expired authentication token is used")
	public void verifyGetUserInfo_WithExpiredAuth_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = UserEndPoints.getUserInfo(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get User Info API response matches the expected JSON schema")
	public void verifyGetUserInfo_ResponseMatchesJsonSchema() {
		Response response = UserEndPoints.getUserInfo(true, null);
		response.then().body(matchesJsonSchemaInClasspath("file.json"));
	}

}
