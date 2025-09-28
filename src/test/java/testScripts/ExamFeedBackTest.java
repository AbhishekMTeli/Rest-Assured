package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.ExamEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class ExamFeedBackTest extends BaseTest {

	@Test(description = "Verify exam feedback API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyExamFeedback_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = ExamEndPoints.examFeedback(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);

		// Validate critical top-level fields in pbasdata
		ApiUtils.validateNotNull(response, "pbasdata.unlimited_submissions");
		ApiUtils.validateField(response, "pbasdata.fb_correct_response", true);
		ApiUtils.validateFieldExists(response, "pbasdata.passingGrade");
		ApiUtils.validateNotNull(response, "pbasdata.timeCheck");
		ApiUtils.validateField(response, "pbasdata.pub_assessment_id", 2105599);
		ApiUtils.validateNotNull(response, "pbasdata.assessment_name");
		ApiUtils.validateField(response, "pbasdata.question_level_fb", true);

		// Validate first section in sectionList
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].pubSectionId");
		ApiUtils.validateNotEmpty(response, "pbasdata.sectionList[0].title");
		ApiUtils.validateField(response, "pbasdata.sectionList[0].status", true);
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList");

		// Validate first question in questionsList
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].pubQuestionId");
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].qtModel.questionType");
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].points");
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].answerList");

		// Validate first answer of first question
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].answerList[0].pubAnswerId");
		ApiUtils.validateNotEmpty(response, "pbasdata.sectionList[0].questionsList[0].answerList[0].text");
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].answerList[0].is_correct");
		ApiUtils.validateNotNull(response, "pbasdata.sectionList[0].questionsList[0].answerList[0].score");

		// Validate presence of feedback texts (may be null)
		ApiUtils.validateFieldExists(response, "pbasdata.sectionList[0].questionsList[0].correctFeedbackText");
		ApiUtils.validateFieldExists(response, "pbasdata.sectionList[0].questionsList[0].inCorrectFeedbackText");
	}

	@Test(description = "Verify exam feedback API returns 401 Unauthorized for invalid authentication token")
	public void verifyExamFeedback_WithInvalidAuth_ShouldReturn401() {
		Response response = ExamEndPoints.examFeedback(true, "ovverride token", true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify exam feedback API returns 401 Unauthorized when no authentication is provided")
	public void verifyExamFeedback_WithoutAuth_ShouldReturn401() {
		Response response = ExamEndPoints.examFeedback(false, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify exam feedback API response matches the expected JSON schema")
	public void verifyExamFeedback_ResponseMatchesJsonSchema() {
		Response response = ExamEndPoints.examFeedback(true, null, true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify exam feedback API returns error for invalid path parameter with 200 OK")
	public void verifyExamFeedback_WithInvalidPath_ShouldReturn200WithErrorField() {
		Response response = ExamEndPoints.examFeedback(true, null, true, "3452456");
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateField(response, "error", "No value present");
	}

	@Test(description = "Verify exam feedback API returns 400 Bad Request when path parameter is missing")
	public void verifyExamFeedback_WithoutPath_ShouldReturn400() {
		Response response = ExamEndPoints.examFeedback(true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify exam feedback API returns 401 Unauthorized for expired token")
	public void verifyExamFeedback_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = ExamEndPoints.examFeedback(true, expiredToken, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify exam feedback API handles invalid path format gracefully")
	public void verifyExamFeedback_WithInvalidPathFormat_ShouldReturn400() {
		Response response = ExamEndPoints.examFeedback(true, null, true, "invalid_path!@#");
		ApiUtils.validateStatusCode(response, 400);
	}

}
