package testScripts;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.CourseEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class CourseCatalogTest extends BaseTest {
	@Test
	public void verifyCourseCatalog_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = CourseEndPoints.courseCatalog(true, null,true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "[0].latestSubmittedDate");
		ApiUtils.validateNotNull(response, "[0].dueDate");
		ApiUtils.validateNotNull(response, "[0].remainingDays");
		ApiUtils.validateNotNull(response, "[0].modules[0].moduleTitle");
		ApiUtils.validateNotNull(response, "[0].modules[0].moduleId");
		ApiUtils.validateNotNull(response, "[0].modules[0].lessons[0].lessonTitle");
		ApiUtils.validateNotNull(response, "[0].modules[0].lessons[0].lessonId");
		ApiUtils.validateNotNull(response, "[0].modules[0].lessons[0].status");
		ApiUtils.validateNotNull(response, "[0].courseTitle");
		ApiUtils.validateNotNull(response, "[0].courseId");
		ApiUtils.validateNotNull(response, "[0].bookmarked");
	}

	@Test(description = "Verify course catalog API returns 401 Unauthorized for invalid authentication token")
	public void verifyCourseCatalog_WithInvalidAuth_ShouldReturn401() {
		Response response = CourseEndPoints.courseCatalog(true, "ovverride Token",true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify course catalog API returns 401 Unauthorized when no authentication is provided")
	public void verifyCourseCatalog_WithoutAuth_ShouldReturn401() {
		Response response = CourseEndPoints.courseCatalog(false, null,true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify course catalog API returns 401 Unauthorized when expired authentication is provided")
	public void verifyCourseCatalog_ExpiredAuth_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = CourseEndPoints.courseCatalog(true, expiredToken,true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify course catalog API response matches the expected JSON schema")
	public void verifyCourseCatalog_ResponseMatchesJsonSchema() {
		Response response = CourseEndPoints.courseCatalog(true, null,true, null);
		response.then().body(matchesJsonSchemaInClasspath("file.json"));
	}
	
	@Test(description = "Verify course catalog API response matches the expected JSON schema")
	public void verifyCourseCatalog_missStaffNumber() {
		Response response = CourseEndPoints.courseCatalog(true, null,false, null);
		ApiUtils.validateStatusCode(response, 400);
	}
	
	@Test(description = "Verify course catalog API response matches the expected JSON schema")
	public void verifyCourseCatalog_ovverideStaffNumber() {
		Response response = CourseEndPoints.courseCatalog(true, null,true, "4646644");
		ApiUtils.validateStatusCode(response, 404);
		
	}
	
}
