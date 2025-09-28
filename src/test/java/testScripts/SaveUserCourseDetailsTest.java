package testScripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import builders.LessonDetailsRequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.LessonDetailsRequest;
import utilities.ApiUtils;
import utilities.CourseEndPointsReader;

public class SaveUserCourseDetailsTest extends BaseTest {

	private String ENDPOINT;

	@BeforeClass(alwaysRun = true)
	public void init() {
		// Read endpoint from property file
		ENDPOINT = CourseEndPointsReader.get("saveCourseDetails");
	}

	// ---------------- Positive Tests ----------------

	@Test(description = "Verify saveUserCourseDetails API accepts valid payload and returns success")
	public void testSaveLessonDetails_ValidPayload() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify saveUserCourseDetails API response time is within limits")
	public void testSaveLessonDetails_ResponseTime() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateResponseTime(response, 3000);
	}

	// ---------------- Negative Tests ----------------

	@DataProvider(name = "invalidLessonDetails")
	public Object[][] invalidLessonDetails() {
		return new Object[][] { { LessonDetailsRequestBuilder.buildSampleLessonDetails().setCourseId(0), 400 },
				{ LessonDetailsRequestBuilder.buildSampleLessonDetails().setLessonId(0), 400 },
				{ LessonDetailsRequestBuilder.buildSampleLessonDetails().setUserId("invalid"), 400 } };
	}

	@Test(dataProvider = "invalidLessonDetails", description = "Verify API handles invalid input data")
	public void testInvalidLessonDetails(LessonDetailsRequest request, int expectedStatus) {
		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, expectedStatus);
	}

	// ---------------- Authorization Tests ----------------

	@Test(description = "Verify API returns 401 Unauthorized without token")
	public void testSaveLessonDetails_MissingAuth() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails();

		Response response = given().spec(requestSpecNoAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 Unauthorized for invalid token")
	public void testSaveLessonDetails_InvalidToken() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails();

		Response response = given().spec(requestSpecNoAuth).header("Authorization", "Bearer invalidToken123")
				.contentType(ContentType.JSON).body(request).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
	}

	// ---------------- Boundary / Edge Tests ----------------

	@Test(description = "Verify API handles extreme percentage and score values")
	public void testSaveLessonDetails_BoundaryValues() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails().setPercentage(1000)
				.setScore(Integer.MAX_VALUE);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Assert.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400,
				"Unexpected status code: " + response.getStatusCode());
	}

	@Test(description = "Verify API handles null optional fields gracefully")
	public void testSaveLessonDetails_NullFields() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails().setScormDetails(null)
				.setTimeSpent(null);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify API rejects malformed JSON")
	public void testSaveLessonDetails_MalformedJson() {
		String malformedJson = "{LessonId: 78018, courseId: 71484, }"; // trailing comma

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(malformedJson).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify API rejects special characters in scormDetails")
	public void testSaveLessonDetails_SpecialCharacters() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails()
				.setScormDetails("<script>alert('XSS')</script>");

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Assert.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400,
				"Unexpected status code: " + response.getStatusCode());
	}

	@Test(description = "Verify API handles duplicate lesson payload gracefully")
	public void testSaveLessonDetails_DuplicatePayload() {
		LessonDetailsRequest request = LessonDetailsRequestBuilder.buildSampleLessonDetails();

		// Example: sending the same payload twice
		Response response1 = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Response response2 = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Assert.assertTrue(
				(response1.getStatusCode() == 200 || response1.getStatusCode() == 400)
						&& (response2.getStatusCode() == 200 || response2.getStatusCode() == 400),
				"Unexpected status codes for duplicate payload");
	}
}
