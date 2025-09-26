package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.GlobalSearchEndPointsReader;

public class GlobalSearchEndPoints extends BaseTest {

	public static Response global_Search(boolean includeAuth, String tokenOverride, boolean includeQuery, String query,
			boolean includeStaffNumber, String staffNumber) {
		// Get endpoint URL from properties/config
		String global_Search = GlobalSearchEndPointsReader.get("global_Search");

		// Start building request
		RequestSpecification req = given();

		// Handle authentication
		if (includeAuth) {
			if (tokenOverride != null) {
				// Use provided token
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + tokenOverride);
			} else {
				// Use default valid token spec
				req.spec(requestSpecAuth);
			}
		} else {
			// No authentication
			req.spec(requestSpecNoAuth);
		}

		// Add query params correctly
		if (includeQuery) {
			req.queryParam("query", query != null ? query : "cbt");
		}
		if (includeStaffNumber) {
			req.queryParam("staff_number", staffNumber != null ? staffNumber : "13355");
		}

		// Fire GET request and return response
		return req.when().get(global_Search);
	}
}
