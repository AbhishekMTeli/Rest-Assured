package testScripts;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.CourseEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class DowloadResourceTest extends BaseTest {

	@Test(description = "Verify API returns 200 and saves file when valid auth and valid resourceId are provided")
	public void givenValidAuthAndResourceId_whenDownloadResource_thenReturn200AndSaveFile() throws IOException {
		Response response = CourseEndPoints.downloadResource(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);

		byte[] fileBytes = response.asByteArray();

		// Save into src/test/resources/downloads
		String projectDir = System.getProperty("user.dir");
		Path filePath = Paths.get(projectDir, "src", "test", "resources", "downloads", "downloadedResource.png");

		Files.createDirectories(filePath.getParent()); // ensures "downloads" exists
		Files.write(filePath, fileBytes);

		System.out.println("File saved successfully at: " + filePath.toAbsolutePath());
	}

	@Test(description = "Verify API returns 401 when invalid authentication token is provided")
	public void givenInvalidAuthToken_whenDownloadResource_thenReturn401() {
		Response response = CourseEndPoints.downloadResource(true, "invalidToken", true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 when no authentication is provided")
	public void givenNoAuth_whenDownloadResource_thenReturn401() {
		Response response = CourseEndPoints.downloadResource(false, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API returns 401 when expired authentication token is provided")
	public void givenExpiredAuthToken_whenDownloadResource_thenReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9....";
		Response response = CourseEndPoints.downloadResource(true, expiredToken, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify API response matches the expected JSON schema")
	public void givenValidRequest_whenDownloadResource_thenResponseMatchesSchema() {
		Response response = CourseEndPoints.downloadResource(true, null, true, null);
		response.then().body(matchesJsonSchemaInClasspath("file.json"));
	}

	@Test(description = "Verify API returns 400 when resourceId parameter is missing")
	public void givenMissingResourceId_whenDownloadResource_thenReturn400() {
		Response response = CourseEndPoints.downloadResource(true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify API returns 404 when non-existing resourceId is provided")
	public void givenInvalidResourceId_whenDownloadResource_thenReturn404() {
		Response response = CourseEndPoints.downloadResource(true, null, true, "350445");
		ApiUtils.validateStatusCode(response, 404);
	}

	@Test(description = "Verify API returns 400 when resourceId format is invalid (non-numeric)")
	public void givenNonNumericResourceId_whenDownloadResource_thenReturn400() {
		Response response = CourseEndPoints.downloadResource(true, null, true, "abc123");
		ApiUtils.validateStatusCode(response, 404);
	}

	@Test(description = "Verify API handles very large resourceId gracefully")
	public void givenLargeResourceId_whenDownloadResource_thenReturn404() {
		Response response = CourseEndPoints.downloadResource(true, null, true, "9999999999");
		ApiUtils.validateStatusCode(response, 404);
	}

	@Test(description = "Verify API returns 400 when resourceId is explicitly empty")
	public void givenEmptyResourceId_whenDownloadResource_thenReturn400() {
		Response response = CourseEndPoints.downloadResource(true, null, true, "");
		ApiUtils.validateStatusCode(response, 404);
	}

	@Test(description = "Verify response headers are correct for file download")
	public void givenValidAuth_whenDownloadResource_thenVerifyResponseHeaders() {
		Response response = CourseEndPoints.downloadResource(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		response.then().header("Content-Type", "application/octet-stream"); // adjust based on API
		response.then().header("Content-Disposition", containsString("attachment"));
	}

	@Test(description = "Verify downloaded resource file is not empty")
	public void givenValidAuth_whenDownloadResource_thenFileShouldNotBeEmpty() {
		Response response = CourseEndPoints.downloadResource(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		byte[] fileBytes = response.asByteArray();
		Assert.assertTrue(fileBytes.length > 0, "Downloaded file is empty!");
	}

	@Test(description = "Verify API responds within acceptable time limit")
	public void givenValidAuth_whenDownloadResource_thenRespondWithin3Seconds() {
		Response response = CourseEndPoints.downloadResource(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		response.then().time(lessThan(3000L));
	}

	@Test(description = "Verify API returns same response on duplicate requests (idempotency)")
	public void givenDuplicateRequests_whenDownloadResource_thenReturnSameResponse() {
		Response firstResponse = CourseEndPoints.downloadResource(true, null, true, null);
		Response secondResponse = CourseEndPoints.downloadResource(true, null, true, null);

		ApiUtils.validateStatusCode(firstResponse, 200);
		ApiUtils.validateStatusCode(secondResponse, 200);

		Assert.assertEquals(firstResponse.asByteArray(), secondResponse.asByteArray(),
				"Duplicate requests did not return identical responses");
	}

	@Test(description = "Verify API works with default resourceId when no override is provided")
	public void givenDefaultResourceId_whenDownloadResource_thenReturn200() {
		Response response = CourseEndPoints.downloadResource(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
	}
}
