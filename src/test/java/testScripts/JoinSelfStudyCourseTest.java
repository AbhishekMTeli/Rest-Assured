package testScripts;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import builders.JoinSelfStudyCourseRequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.JoinSelfStudyCourseRequest;
import utilities.ApiUtils;
import utilities.CourseEndPointsReader;

public class JoinSelfStudyCourseTest extends BaseTest {

	private String ENDPOINT;

	@BeforeClass(alwaysRun = true)
	public void init() {
		ENDPOINT = CourseEndPointsReader.get("selfStudyJoin");
	}

	// ---------------- Positive Tests ----------------

	@Test(description = "Verify JoinSelfStudyCourse API accepts valid payload and returns success")
	public void testJoinSelfStudyCourse_ValidPayload() {
		JoinSelfStudyCourseRequest request = JoinSelfStudyCourseRequestBuilder.buildValidRequest();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify JoinSelfStudyCourse API response time is within limits")
	public void testJoinSelfStudyCourse_ResponseTime() {
		JoinSelfStudyCourseRequest request = JoinSelfStudyCourseRequestBuilder.buildValidRequest();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateResponseTime(response, 3000);
	}

	// ---------------- Negative Tests ----------------

	@DataProvider(name = "invalidJoinCourseData")
	public Object[][] invalidJoinCourseData() {
		return new Object[][] { { JoinSelfStudyCourseRequestBuilder.buildInvalidCourseId(), 400 },
				{ JoinSelfStudyCourseRequestBuilder.buildInvalidStaffNumber(), 400 },
				{ JoinSelfStudyCourseRequestBuilder.buildWithNullBookmark(), 400 } };
	}

	@Test(dataProvider = "invalidJoinCourseData", description = "Verify API handles invalid input data")
	public void testInvalidJoinCourseData(JoinSelfStudyCourseRequest request, int expectedStatus) {
		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, expectedStatus);
	}

	// ---------------- Authorization Tests ----------------

	@Test(description = "Verify API returns 401 Unauthorized without token")
	public void testJoinSelfStudyCourse_MissingAuth() {
		JoinSelfStudyCourseRequest request = JoinSelfStudyCourseRequestBuilder.buildValidRequest();

		Response response = given().spec(requestSpecNoAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 Unauthorized for invalid token")
	public void testJoinSelfStudyCourse_InvalidToken() {
		JoinSelfStudyCourseRequest request = JoinSelfStudyCourseRequestBuilder.buildValidRequest();

		Response response = given().spec(requestSpecNoAuth).header("Authorization", "Bearer invalidToken123")
				.contentType(ContentType.JSON).body(request).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
	}

	// ---------------- Boundary / Edge Tests ----------------

	@Test(description = "Verify API handles duplicate join requests gracefully")
	public void testJoinSelfStudyCourse_DuplicatePayload() {
		JoinSelfStudyCourseRequest request = JoinSelfStudyCourseRequestBuilder.buildValidRequest();

		Response response1 = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Response response2 = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Assert.assertTrue(
				(response1.getStatusCode() == 200 || response1.getStatusCode() == 400)
						&& (response2.getStatusCode() == 200 || response2.getStatusCode() == 400),
				"Unexpected status codes for duplicate join request");
	}

	@Test(description = "Verify API rejects malformed JSON")
	public void testJoinSelfStudyCourse_MalformedJson() {
		String malformedJson = "{courseId: 100000, staffNumber: '1234567', role: 'tester', }"; // trailing comma

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(malformedJson).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 400);
	}
}
