package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.CourseEndPointsReader;

public class CourseEndPoints extends BaseTest {
	public static Response courseCatalog(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String overrideStaffNumber) {
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // start with noAuth spec to avoid double Authorization header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}
		if (includeStaffNumber) {
			if (overrideStaffNumber != null) {
				req.queryParam("staffNumber", overrideStaffNumber);
			} else {
				req.queryParam("staffNumber", "13355");
			}
		}
		String courseCatalog = CourseEndPointsReader.get("courseCatalog");
		return req.when().get(courseCatalog);
	}

	public static Response downloadResource(boolean includeAuth, String tokenOverride, boolean includeResourceId,
			String overrideResourceId) {
		RequestSpecification req = given();

// Handle authentication
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}

// Handle resourceId
		if (includeResourceId) {
			req.queryParam("resourceId", overrideResourceId != null ? overrideResourceId : "3702");
		}

// Fetch endpoint from config
		String downloadResource = CourseEndPointsReader.get("downloadResource");

// Execute request
		return req.when().get(downloadResource);
	}

	public static Response lessonDetails_courseWise(boolean includeAuth, String tokenOverride,
			boolean includeStaffNumber, String staffNumber, boolean includeCourseId, String courseId) {
// Get endpoint URL from properties/config
		String lessonDetails_courseWise = CourseEndPointsReader.get("lessonDetails_courseWise");

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
		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "13355");
		}
		if (includeCourseId) {
			req.queryParam("courseId", courseId != null ? courseId : "12601");
		}

// Fire GET request and return response
		return req.when().get(lessonDetails_courseWise);
	}

	public static Response getFOCIPCertificate(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {
// Get endpoint URL from properties/config
		String getFOCIPCertificate = CourseEndPointsReader.get("getFOCIPCertificate");

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
		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "13355");
		}

// Fire GET request and return response
		return req.when().get(getFOCIPCertificate);
	}

}
