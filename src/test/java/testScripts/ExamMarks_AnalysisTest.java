package testScripts;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.AnalysisEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class ExamMarks_AnalysisTest extends BaseTest {
	@Test(description = "Verify API returns 200 when valid auth and valid staffNumber are provided")
	public void givenValidAuthAndStaffNumber_thenReturn200() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "[0].PUB_ASSESSMENT_ID");
		ApiUtils.validateNotNull(response, "[0].CLASS_HIGHEST_SCORE");
		ApiUtils.validateNotNull(response, "[0].HIGHEST_SCORE");
		ApiUtils.validateNotNull(response, "[0].LATEST_SUBMISSION");
		ApiUtils.validateNotNull(response, "[0].CLASS_AVG_SCORE");
		ApiUtils.validateNotNull(response, "[0].ASSESSMENT_NAME");
		ApiUtils.validateNotNull(response, "[0].USER_ID");
	}

	@Test(description = "Verify API returns 401 when invalid authentication token is provided")
	public void givenInvalidAuthToken_thenReturn401() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, "invalidToken", true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 when no authentication is provided")
	public void givenNoAuth_thenReturn401() {
		Response response = AnalysisEndPoints.examMarks_Analysis(false, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 when expired authentication token is provided")
	public void givenExpiredAuthToken_thenReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9....";
		Response response = AnalysisEndPoints.examMarks_Analysis(true, expiredToken, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API response matches the expected JSON schema")
	public void givenValidRequest_thenResponseMatchesSchema() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, true, null);
		response.then().body(matchesJsonSchemaInClasspath("file.json"));
	}

	@Test(description = "Verify API returns 400 when StaffNumber parameter is missing")
	public void givenMissingStaffNumber_thenReturn400() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify API returns 200 with an empty List when non-existing StaffNumber is provided")
	public void givenInvalidStaffNumber_thenReturn200() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, true, "350445");
		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify API handles very large StaffNumber gracefully")
	public void givenLargeStaffNumber_thenReturn200() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, true, "9999999999");
		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify API responds within acceptable time limit")
	public void givenValidAuth_thenRespondWithin3Seconds() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		response.then().time(lessThan(3000L));
	}

	@Test(description = "Verify API works with default StaffNumber when no override is provided")
	public void givenDefaultStaffNumber_thenReturn200() {
		Response response = AnalysisEndPoints.examMarks_Analysis(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
	}
}
