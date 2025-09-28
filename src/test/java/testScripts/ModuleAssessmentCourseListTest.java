package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.AdminBaseTest;
import endpoints.AdminAnalysisEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class ModuleAssessmentCourseListTest extends AdminBaseTest {
	@Test(description = "Verify Module Assessment Course List API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyModuleAssessmentCourseList_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = AdminAnalysisEndPoints.moduleAssessmentCourseList(true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "[0].ASSESSMENTS[0].ASSESSMENT_NAME");
		ApiUtils.validateFieldExists(response, "[0].ASSESSMENTS[0].PUB_ASSESSMENT_ID");
		ApiUtils.validateFieldExists(response, "[0].COURSE_ID");
		ApiUtils.validateFieldExists(response, "[0].COURSE_TITLE");
		ApiUtils.validateFieldExists(response, "[0].MODULES[0].LESSONS[0].LESSON_ID");
		ApiUtils.validateFieldExists(response, "[0].MODULES[0].LESSONS[0].LESSON_TITLE");
		ApiUtils.validateFieldExists(response, "[0].MODULES[0].MODULE_ID");
		ApiUtils.validateFieldExists(response, "[0].MODULES[0].MODULE_TITLE");
	}

	@Test(description = "Verify Module Assessment Course List API returns 401 Unauthorized for invalid authentication token")
	public void verifyModuleAssessmentCourseList_WithInvalidAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.moduleAssessmentCourseList(true, "ovverride token");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Module Assessment Course List API returns 401 Unauthorized when no authentication is provided")
	public void verifyModuleAssessmentCourseList_WithoutAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.moduleAssessmentCourseList(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Module Assessment Course List API response matches the expected JSON schema")
	public void verifyModuleAssessmentCourseList_ResponseMatchesJsonSchema() {
		Response response = AdminAnalysisEndPoints.moduleAssessmentCourseList(true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify Module Assessment Course List API returns 401 Unauthorized for expired token")
	public void verifyModuleAssessmentCourseList_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = AdminAnalysisEndPoints.moduleAssessmentCourseList(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}
}
