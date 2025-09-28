package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.NotificationsEndpoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class GetNotificationTest extends BaseTest {
	@Test(description = "Verify Get Notification API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyGetNotification_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = NotificationsEndpoints.getNotification(true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "[0].id");
		ApiUtils.validateFieldExists(response, "[0].staff_number");
		ApiUtils.validateFieldExists(response, "[0].notification_type");
		ApiUtils.validateFieldExists(response, "[0].notification_value");
		ApiUtils.validateFieldExists(response, "[0].notification_status");
		ApiUtils.validateFieldExists(response, "[0].notification_type_id");
		ApiUtils.validateFieldExists(response, "[0].createdDate");
		ApiUtils.validateFieldExists(response, "[0].courseId");
		ApiUtils.validateFieldExists(response, "[0].assessmentId");
		ApiUtils.validateFieldExists(response, "[0].sent_status");
	}

	@Test(description = "Verify Get Notification API returns 401 Unauthorized for invalid authentication token")
	public void verifyGetNotification_WithInvalidAuth_ShouldReturn401() {
		Response response = NotificationsEndpoints.getNotification(true, "ovverride token");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get Notification API returns 401 Unauthorized when no authentication is provided")
	public void verifyGetNotification_WithoutAuth_ShouldReturn401() {
		Response response = NotificationsEndpoints.getNotification(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Get Notification API response matches the expected JSON schema")
	public void verifyGetNotification_ResponseMatchesJsonSchema() {
		Response response = NotificationsEndpoints.getNotification(true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify Get Notification API returns 401 Unauthorized for expired token")
	public void verifyGetNotification_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = NotificationsEndpoints.getNotification(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}

}
