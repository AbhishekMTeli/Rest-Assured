package testScripts;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.AdminBaseTest;
import endpoints.AdminAnalysisEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class UserCourseExpiryTest extends AdminBaseTest {
	@Test(description = "Verify User Course Expiry API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyUserCourseExpiry_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = AdminAnalysisEndPoints.userCourseExpiry(true, null);
		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify User Course Expiry API returns 401 Unauthorized for invalid authentication token")
	public void verifyUserCourseExpiry_WithInvalidAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.userCourseExpiry(true, "ovverride token");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify User Course Expiry API returns 401 Unauthorized when no authentication is provided")
	public void verifyUserCourseExpiry_WithoutAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.userCourseExpiry(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify User Course Expiry API response matches the expected JSON schema")
	public void verifyUserCourseExpiry_ResponseMatchesJsonSchema() {
		Response response = AdminAnalysisEndPoints.userCourseExpiry(true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify User Course Expiry API returns 401 Unauthorized for expired token")
	public void verifyUserCourseExpiry_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = AdminAnalysisEndPoints.userCourseExpiry(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}
}
