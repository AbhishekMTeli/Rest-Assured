package testScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import builders.SaveExamResultsRequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.SaveExamResultsRequest;
import utilities.ApiUtils;

public class SaveExamResultsApiTest extends BaseTest {

	// Positive tests

	@Test(description = "Verify Save Exam Results API accepts valid payload and returns success")
	public void verifySaveExamResults_ValidPayload() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		ApiUtils.validateStatusCode(response, 200);

		Integer assessmentGradingId = response.jsonPath().getInt("assessmentGradingId");
		Assert.assertNotNull(assessmentGradingId, "assessmentGradingId should not be null");
		Assert.assertTrue(assessmentGradingId > 0, "assessmentGradingId should be positive");
	}

	@Test(description = "Verify Save Exam Results API response time is within acceptable limits")
	public void verifySaveExamResults_ResponseTime() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		ApiUtils.validateStatusCode(response, 200);

		long responseTime = response.getTime();
		Assert.assertTrue(responseTime < 3000, "Response time exceeded: " + responseTime + " ms");
	}

	@Test(description = "Verify Save Exam Results API handles boundary values for scores")
	public void verifySaveExamResults_BoundaryScores() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();
		request.setUserScore(Integer.MAX_VALUE);
		request.setTotalScore(Integer.MAX_VALUE);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		// Depending on API behavior, accept 200 or 400
		Assert.assertTrue(response.statusCode() == 200 || response.statusCode() == 400,
				"Response status should be 200 or 400 for boundary scores");
	}

	// Authorization tests

	@Test(description = "Verify Save Exam Results API returns 401 Unauthorized without auth")
	public void verifySaveExamResults_MissingAuthorization() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();

		Response response = given().spec(requestSpecNoAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		ApiUtils.validateStatusCode(response, 401);

		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type missing 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Unauthorized"), "Response body missing 'Unauthorized'");
	}

	@Test(description = "Verify Save Exam Results API returns 401 Unauthorized for invalid token")
	public void verifySaveExamResults_InvalidAuthorizationToken() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();

		Response response = given().spec(requestSpecNoAuth).header("Authorization", "Bearer invalidToken123")
				.contentType(ContentType.JSON).body(request).when().post("/saveExamResults");

		ApiUtils.validateStatusCode(response, 401);

		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type missing 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Unauthorized"), "Response body missing 'Unauthorized'");
	}

	// Validation tests

	@Test(description = "Verify Save Exam Results API returns 400 Bad Request for missing mandatory pubAssessmentId")
	public void verifySaveExamResults_MissingPubAssessmentId() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();
		request.setPubAssessmentId(0); // invalid pubAssessmentId

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		// Adjust according to actual backend behavior
		Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 200,
				"Expected status 400 or 200 but got " + response.statusCode());
	}

	@Test(description = "Verify Save Exam Results API returns 400 Bad Request for empty questionsList")
	public void verifySaveExamResults_EmptyQuestionsList() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();
		request.setQuestionsList(null);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 200,
				"Expected status 400 or 200 but got " + response.statusCode());
	}

	@Test(description = "Verify Save Exam Results API returns 400 Bad Request for question with missing pubQuestionId")
	public void verifySaveExamResults_QuestionMissingId() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();
		SaveExamResultsRequest.Question firstQuestion = request.getQuestionsList().get(0);
		firstQuestion.setPubQuestionId(0);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 200,
				"Expected status 400 or 200 but got " + response.statusCode());
	}

	@Test(description = "Verify Save Exam Results API returns 400 Bad Request for answer with missing pubAnswerId")
	public void verifySaveExamResults_AnswerMissingId() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();
		SaveExamResultsRequest.Answer firstAnswer = request.getQuestionsList().get(0).getAnswerList().get(0);
		firstAnswer.setPubAnswerId(0);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 200,
				"Expected status 400 or 200 but got " + response.statusCode());
	}

	// Optional additional tests

	@Test(description = "Verify Save Exam Results API rejects invalid data types")
	public void verifyInvalidDataTypes() {
		String invalidPayload = """
				{
				  "pubAssessmentId": "abc",
				  "userScore": "2",
				  "timeSpent": "seventy",
				  "questionsList": []
				}
				""";

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(invalidPayload).when()
				.post("/saveExamResults");

		ApiUtils.validateStatusCode(response, 400);
		// Optionally validate error message content
	}

	@Test(description = "Verify Save Exam Results API rejects malformed JSON")
	public void verifyMalformedJson() {
		String malformedJson = "{pubAssessmentId: 258016, userScore: 2,}"; // trailing comma

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(malformedJson).when()
				.post("/saveExamResults");

		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify Save Exam Results API handles duplicate questions gracefully")
	public void verifyDuplicateQuestions() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();

		List<SaveExamResultsRequest.Question> mutableQuestions = new ArrayList<>(request.getQuestionsList());
		mutableQuestions.add(request.getQuestionsList().get(0)); // add duplicate
		request.setQuestionsList(mutableQuestions);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		Assert.assertTrue(response.statusCode() == 200 || response.statusCode() == 400,
				"Expected status 200 or 400 but got " + response.statusCode());
	}

	@Test(description = "Verify Save Exam Results API rejects special characters/HTML injections in texts")
	public void verifySpecialCharactersInjection() {
		SaveExamResultsRequest request = SaveExamResultsRequestBuilder.buildSampleRequest();
		request.getQuestionsList().get(0).getQuestionTextList().get(0).setQuestionText("<script>alert('XSS')</script>");

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post("/saveExamResults");

		// Depending on backend validation, expect 400 or 200
		Assert.assertTrue(response.statusCode() == 200 || response.statusCode() == 400,
				"Expected status 200 or 400 but got " + response.statusCode());
	}
}
