package testScripts;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import builders.CourseBookmarkRequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.CourseBookmarkRequest;
import utilities.ApiUtils;
import utilities.CourseEndPointsReader;

public class BookMarkCourseTest extends BaseTest {

	private String ENDPOINT;

	@BeforeClass(alwaysRun = true)
	public void init() {
		ENDPOINT = CourseEndPointsReader.get("bookmarkCourse");
	}

	// ---------------- Positive Tests ----------------

	@Test(description = "Verify SetCourseBookmark API accepts valid payload and returns success")
	public void testSetCourseBookmark_ValidPayload() {
		CourseBookmarkRequest request = CourseBookmarkRequestBuilder.buildValidBookmark();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
	}

	@Test(description = "Verify SetCourseBookmark API response time is within limits")
	public void testSetCourseBookmark_ResponseTime() {
		CourseBookmarkRequest request = CourseBookmarkRequestBuilder.buildValidBookmark();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateResponseTime(response, 3000);
	}

	// ---------------- Negative Tests ----------------

	@DataProvider(name = "invalidBookmarkData")
	public Object[][] invalidBookmarkData() {
		return new Object[][] { { CourseBookmarkRequestBuilder.buildInvalidStaffNumber(), 404 },
				{ CourseBookmarkRequestBuilder.buildInvalidCourseId(), 404 },
				{ CourseBookmarkRequestBuilder.buildBookmarkWithNull(), 404 } };
	}

	@Test(dataProvider = "invalidBookmarkData", description = "Verify API handles invalid input data")
	public void testInvalidBookmarkData(CourseBookmarkRequest request, int expectedStatus) {
		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, expectedStatus);
	}

	// ---------------- Authorization Tests ----------------

	@Test(description = "Verify API returns 401 Unauthorized without token")
	public void testSetCourseBookmark_MissingAuth() {
		CourseBookmarkRequest request = CourseBookmarkRequestBuilder.buildValidBookmark();

		Response response = given().spec(requestSpecNoAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 Unauthorized for invalid token")
	public void testSetCourseBookmark_InvalidToken() {
		CourseBookmarkRequest request = CourseBookmarkRequestBuilder.buildValidBookmark();

		Response response = given().spec(requestSpecNoAuth).header("Authorization", "Bearer invalidToken123")
				.contentType(ContentType.JSON).body(request).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
	}

	// ---------------- Boundary / Edge Tests ----------------

	@Test(description = "Verify API handles duplicate bookmark requests gracefully")
	public void testSetCourseBookmark_DuplicatePayload() {
		CourseBookmarkRequest request = CourseBookmarkRequestBuilder.buildValidBookmark();

		Response response1 = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Response response2 = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(request).when()
				.post(ENDPOINT);

		Assert.assertTrue(
				(response1.getStatusCode() == 200 || response1.getStatusCode() == 400)
						&& (response2.getStatusCode() == 200 || response2.getStatusCode() == 400),
				"Unexpected status codes for duplicate bookmark request");
	}

	@Test(description = "Verify API rejects malformed JSON")
	public void testSetCourseBookmark_MalformedJson() {
		String malformedJson = "{staffNumber: '13355', courseId: 12868, bookMark: true, }"; // trailing comma

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(malformedJson).when()
				.post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 400);
	}
}
