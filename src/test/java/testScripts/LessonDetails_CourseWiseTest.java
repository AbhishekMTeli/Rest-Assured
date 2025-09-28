package testScripts;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.CourseEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class LessonDetails_CourseWiseTest extends BaseTest {
	/**
	 * ✅ Positive Test: Valid staffNumber, valid courseId, valid auth
	 */
	@Test(description = "Verify API returns 200 and valid response with correct staffNumber, courseId, and token")
	public void testValidLessonDetails() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "[0].moduleId");
		ApiUtils.validateNotNull(response, "[0].staffNumber");
		ApiUtils.validateField(response, "[0].moduleTitle", "Test Module");
		ApiUtils.validateNotNull(response, "[0].lessons[0].lessonId");
		ApiUtils.validateNotNull(response, "[0].lessons[0].lessonTitle");
		ApiUtils.validateNotNull(response, "[0].lessons[0].resourceId");
		ApiUtils.validateNotNull(response, "[0].lessons[0].totalStatus");
		ApiUtils.validateNotNull(response, "[0].lessons[0].resourcePath");
		ApiUtils.validateNotNull(response, "[0].lessons[0].filePath");
		ApiUtils.validateNotNull(response, "[0].lessons[0].fileExtension");
	}

	/**
	 * ❌ Negative Test: Missing authentication token
	 */
	@Test(description = "Verify API returns 401 when no authentication token is provided")
	public void testMissingAuthToken() {
		Response response = CourseEndPoints.lessonDetails_courseWise(false, null, true, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	/**
	 * ❌ Negative Test: Invalid token
	 */
	@Test(description = "Verify API returns 401 when invalid authentication token is provided")
	public void testInvalidAuthToken() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, "Token", true, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	/**
	 * ❌ Negative Test: Expired token
	 */
	@Test(description = "Verify API returns 401 when expired token is provided")
	public void testExpiredAuthToken() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = CourseEndPoints.lessonDetails_courseWise(true, expiredToken, true, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	/**
	 * ❌ Negative Test: Invalid staffNumber
	 */
	@Test(description = "Verify API returns 404/400 when invalid staffNumber is provided")
	public void testInvalidStaffNumber() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, true, "133@,55", true, null);
		ApiUtils.validateStatusCode(response, 404);
	}

	/**
	 * ❌ Negative Test: Invalid courseId
	 */
	@Test(description = "Verify API returns 200 when invalid courseId is provided")
	public void testInvalidCourseId() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, true, null, true, "999999");
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateEmptyList(response, "$");
	}

	/**
	 * ❌ Negative Test: Missing staffNumber
	 */
	@Test(description = "Verify API returns 400 when staffNumber parameter is missing")
	public void testMissingStaffNumber() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, false, null, true, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	/**
	 * ❌ Negative Test: Missing courseId
	 */
	@Test(description = "Verify API returns 400 when courseId parameter is missing")
	public void testMissingCourseId() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	/**
	 * ✅ Schema Validation
	 */
	@Test(description = "Verify lesson details API response matches JSON schema")
	public void testJsonSchemaValidation() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateJsonSchema(response, "src/test/resources/schemas/lessonDetailsCourseWiseSchema.json");
	}

	/**
	 * 🔎 Edge Case: Large staffNumber
	 */
	@Test(description = "Verify API gracefully handles very large staffNumber values")
	public void testLargeStaffNumber() {
		Response response = CourseEndPoints.lessonDetails_courseWise(true, null, true, "9999999999999", true, null);
		ApiUtils.validateStatusCode(response, 404);
	}
}
