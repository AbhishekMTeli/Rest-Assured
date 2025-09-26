package utilities;

import java.io.File;
import java.util.List;

import org.testng.Assert;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ApiUtils {

	/** ✅ Get status code */
	public static int getStatusCode(Response response) {
		return response.getStatusCode();
	}

	/** ✅ Extract value from response using JsonPath */
	public static String getValue(Response response, String jsonPath) {
		return response.jsonPath().getString(jsonPath);
	}

	/** ✅ Validate status code */
	public static void validateStatusCode(Response response, int expectedStatusCode) {
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
				"❌ Status code mismatch. Response: " + response.asPrettyString());
	}

	/** ✅ Validate header */
	public static void validateHeader(Response response, String headerName, String expectedValue) {
		String actual = response.getHeader(headerName);
		Assert.assertEquals(actual, expectedValue, String.format("❌ Header mismatch for '%s'. Expected: %s, Actual: %s",
				headerName, expectedValue, actual));
	}

	/** ✅ Validate response time (ms) */
	public static void validateResponseTime(Response response, long maxTimeInMs) {
		long actualTime = response.time();
		Assert.assertTrue(actualTime <= maxTimeInMs,
				String.format("❌ Response time exceeded. Expected <= %d ms, Actual = %d ms", maxTimeInMs, actualTime));
	}

	/** ✅ Validate JSON field matches expected */
	public static void validateField(Response response, String jsonPath, Object expectedValue) {
		Object actualValue = response.jsonPath().get(jsonPath);
		Assert.assertEquals(actualValue, expectedValue, String.format(
				"❌ Value mismatch for field '%s'. Expected: %s, Actual: %s", jsonPath, expectedValue, actualValue));
	}

	/** ✅ Validate JSON field is not null */
	public static void validateNotNull(Response response, String jsonPath) {
		Object actualValue = response.jsonPath().get(jsonPath);
		Assert.assertNotNull(actualValue,
				String.format("❌ Field '%s' is null in response: %s", jsonPath, response.asPrettyString()));
	}

	/** ✅ Validate JSON field is not empty */
	public static void validateNotEmpty(Response response, String jsonPath) {
		String value = response.jsonPath().getString(jsonPath);
		Assert.assertTrue(value != null && !value.trim().isEmpty(),
				String.format("❌ Field '%s' is empty in response: %s", jsonPath, response.asPrettyString()));
	}

	/** ✅ Validate against JSON schema */
	public static void validateJsonSchema(Response response, String schemaFilePath) {
		File schema = new File(schemaFilePath);
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
	}

	/** ✅ Pretty log full response */
	public static void logResponse(Response response) {
		System.out.println("📩 Response:\n" + response.asPrettyString());
	}

	public static void validateEmptyList(Response response, String jsonPath) {
		List<Object> list = response.jsonPath().getList(jsonPath);
		Assert.assertTrue(list.isEmpty(),
				String.format("❌ Expected empty list at '%s' but got: %s", jsonPath, response.asPrettyString()));
	}
	
	public static void validateFieldExists(Response response, String fieldName) {
        Assert.assertTrue(
            response.getBody().jsonPath().get(fieldName) != null,
            "Expected field '" + fieldName + "' to exist in the response body, but it was missing."
        );
    }

}
