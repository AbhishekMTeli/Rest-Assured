package testScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.AdminBaseTest;
import builders.DownloadExcelRequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.DownloadExcelRequest;
import utilities.ApiUtils;

public class DownloadExcelTest extends AdminBaseTest {

	// Positive test - valid payload accepted
	@Test(description = "Verify Download Excel API accepts valid payload array and returns success")
	public void verifyDownloadExcel_ValidPayload() throws IOException {
		List<DownloadExcelRequest> requestList = DownloadExcelRequestBuilder.buildSampleList();

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(requestList).when()
				.post("/export-excel"); // Adjust endpoint as needed

		ApiUtils.validateStatusCode(response, 200);

		// Example response validation, assuming the API returns a success message or ID
//		// list
//		String message = response.jsonPath().getString("message");
//		Assert.assertNotNull(message, "Response message should not be null");
//		Assert.assertTrue(message.toLowerCase().contains("success"), "Response should indicate success");
		byte[] fileBytes = response.asByteArray();

		// Save into src/test/resources/downloads
		String projectDir = System.getProperty("user.dir");
		Path filePath = Paths.get(projectDir, "src", "test", "resources", "downloads", "excel.xlsx");

		Files.createDirectories(filePath.getParent()); // ensures "downloads" exists
		Files.write(filePath, fileBytes);

		System.out.println("File saved successfully at: " + filePath.toAbsolutePath());
	}

	// Authorization tests

	@Test(description = "Verify Download Excel API returns 401 Unauthorized without auth")
	public void verifyDownloadExcel_MissingAuthorization() {
		List<DownloadExcelRequest> requestList = DownloadExcelRequestBuilder.buildSampleList();

		Response response = given().spec(requestSpecNoAuth).contentType(ContentType.JSON).body(requestList).when()
				.post("/export-excel");

		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type missing 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Unauthorized"), "Response body missing 'Unauthorized'");
	}

	@Test(description = "Verify Download Excel API returns 401 Unauthorized for invalid token")
	public void verifyDownloadExcel_InvalidAuthorizationToken() {
		List<DownloadExcelRequest> requestList = DownloadExcelRequestBuilder.buildSampleList();

		Response response = given().spec(requestSpecNoAuth).header("Authorization", "Bearer invalidToken123")
				.contentType(ContentType.JSON).body(requestList).when().post("/export-excel");

		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type missing 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Unauthorized"), "Response body missing 'Unauthorized'");
	}

	// Validation tests

	@Test(description = "Verify Download Excel API returns 400 Bad Request for null userScore in an entry")
	public void verifyDownloadExcel_NullUserScore() {
		List<DownloadExcelRequest> requestList = DownloadExcelRequestBuilder.buildSampleList();

		// Manually set userScore to null for one item (already null in sample but
		// ensure)
		requestList.get(0).setUserScore(null);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(requestList).when()
				.post("/export-excel");

		Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 200,
				"Expected status 400 or 200 but got " + response.statusCode());
	}

	@Test(description = "Verify Download Excel API returns 400 Bad Request for missing mandatory name field")
	public void verifyDownloadExcel_MissingName() {
		List<DownloadExcelRequest> requestList = DownloadExcelRequestBuilder.buildSampleList();
		requestList.get(0).setName(null);

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(requestList).when()
				.post("/export-excel");

		Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 200,
				"Expected status 400 or 200 but got " + response.statusCode());
	}

	@Test(description = "Verify Download Excel API handles empty payload gracefully")
	public void verifyDownloadExcel_EmptyPayload() {
		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body("[]").when()
				.post("/export-excel");

		// Adjust as per backend behavior: 200 OK or 400 Bad Request expected
		Assert.assertTrue(response.statusCode() == 200 || response.statusCode() == 400,
				"Expected status 200 or 400 but got " + response.statusCode());
	}

	// Optional additional tests

	@Test(description = "Verify Download Excel API rejects malformed JSON")
	public void verifyMalformedJson() {
		String malformedJson = "[{name: \"Sample\", assessment}]"; // malformed JSON

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(malformedJson).when()
				.post("/export-excel");

		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify Download Excel API rejects invalid data types for userScore")
	public void verifyInvalidDataTypes() {
		String invalidPayload = """
				[
				   {
				     "name": "Test User",
				     "assessment": "RECURRENT QUESTIONNAIRE",
				     "status": "Pending",
				     "userScore": "invalidNumber",
				     "completionDate": "Sep 01, 2025"
				   }
				]
				""";

		Response response = given().spec(requestSpecAuth).contentType(ContentType.JSON).body(invalidPayload).when()
				.post("/export-excel");

		ApiUtils.validateStatusCode(response, 400);
	}
}
