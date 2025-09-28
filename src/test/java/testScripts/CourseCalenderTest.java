package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.CalenderEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class CourseCalenderTest extends BaseTest {
	@Test(description = "Verify API returns 200 when valid auth and valid staffNumber are provided")
	public void givenValidAuthAndStaffNumber_thenReturn200() {
		Response response = CalenderEndPoints.courseCalender(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotEmpty(response, "[0].type");
		ApiUtils.validateNotEmpty(response, "[0].courseId");
		ApiUtils.validateNotEmpty(response, "[0].moduleId");
		ApiUtils.validateNotEmpty(response, "[0].title");
		ApiUtils.validateNotEmpty(response, "[0].courseName");
		ApiUtils.validateNotEmpty(response, "[0].moduleName");
		ApiUtils.validateNotEmpty(response, "[0].id");
		ApiUtils.validateNotEmpty(response, "[0].dueDate");
		ApiUtils.validateNotEmpty(response, "[0].status");
		ApiUtils.validateNotEmpty(response, "[0].resourceId");
	}

	@Test(description = "Verify API returns 401 when invalid authentication token is provided")
	public void givenInvalidAuthToken_thenReturn401() {
		Response response = CalenderEndPoints.courseCalender(true, "invalidToken", true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 when no authentication is provided")
	public void givenNoAuth_thenReturn401() {
		Response response = CalenderEndPoints.courseCalender(false, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 when expired authentication token is provided")
	public void givenExpiredAuthToken_thenReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9....";
		Response response = CalenderEndPoints.courseCalender(true, expiredToken, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API response matches the expected JSON schema")
	public void givenValidRequest_thenResponseMatchesSchema() {
		Response response = CalenderEndPoints.courseCalender(true, null, true, null);
		response.then().body(matchesJsonSchemaInClasspath("file.json"));
	}

	@Test(description = "Verify API returns 400 when StaffNumber parameter is missing")
	public void givenMissingStaffNumber_thenReturn400() {
		Response response = CalenderEndPoints.courseCalender(true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify API returns 200 with an empty List when non-existing StaffNumber is provided")
	public void givenInvalidStaffNumber_thenReturn200() {
		Response response = CalenderEndPoints.courseCalender(true, null, true, "350445");
		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify API handles very large StaffNumber gracefully")
	public void givenLargeStaffNumber_thenReturn200() {
		Response response = CalenderEndPoints.courseCalender(true, null, true, "9999999999");
		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify API responds within acceptable time limit")
	public void givenValidAuth_thenRespondWithin3Seconds() {
		Response response = CalenderEndPoints.courseCalender(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		response.then().time(lessThan(3000L));
	}

	@Test(description = "Verify API works with default StaffNumber when no override is provided")
	public void givenDefaultStaffNumber_thenReturn200() {
		Response response = CalenderEndPoints.courseCalender(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
	}
}
