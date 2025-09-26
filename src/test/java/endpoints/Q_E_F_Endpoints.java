package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Q_E_F_EndpointsReader;

public class Q_E_F_Endpoints extends BaseTest {

	public static Response queryResponse_API(boolean includeAuth, String tokenOverride) {
		// Get endpoint URL from properties/config
		String queryResponse_API = Q_E_F_EndpointsReader.get("queryResponse_API");

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

		// Fire GET request and return response
		return req.when().get(queryResponse_API);
	}

	public static Response getQueryChats(boolean includeAuth, String tokenOverride) {
		// Get endpoint URL from properties/config
		String getQueryChats = Q_E_F_EndpointsReader.get("getQueryChats");

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

		// Fire GET request and return response
		return req.when().get(getQueryChats);
	}

	public static Response adminGetAllQueries(boolean includeAuth, String tokenOverride) {
		// Get endpoint URL from properties/config
		String adminGetAllQueries = Q_E_F_EndpointsReader.get("adminGetAllQueries");

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

		// Fire GET request and return response
		return req.when().get(adminGetAllQueries);
	}

	public static Response closeQuery(boolean includeAuth, String tokenOverride, boolean includeUserRole,
			String userRole, boolean includeQueryId, String queryId) {
		// Get endpoint URL from properties/config
		String closeQuery = Q_E_F_EndpointsReader.get("closeQuery");

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
		if (includeUserRole) {
			req.queryParam("userRole", userRole != null ? userRole : "Administrator");
		}
		if (includeQueryId) {
			req.queryParam("queryId", queryId != null ? queryId : "281");
		}

		// Fire GET request and return response
		return req.when().get(closeQuery);
	}

	public static Response deleteQuery(boolean includeAuth, String tokenOverride) {
		// Get endpoint URL from properties/config
		String deleteQuery = Q_E_F_EndpointsReader.get("deleteQuery");

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

		return req.when().delete(deleteQuery);
	}

}
