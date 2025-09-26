package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ExamEndPointsReader;

public class ExamEndPoints extends BaseTest {

	public static Response userFeedback(boolean includeAuth, boolean includeStaffNumber, String staffNumber,
			String tokenOverride, boolean includeCourseId, String courseId) {
		String userFeedBack = ExamEndPointsReader.get("userFeedBack");
		RequestSpecification req = given();

		// Select RequestSpecification based on includeAuth flag and tokenOverride
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
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "13355");
		}

		if (includeCourseId) {
			req.queryParam("courseId", courseId != null ? courseId : "12422");
		}

		return req.when().get(userFeedBack);
	}

	public static Response launchExam(boolean includeAuth, String tokenOverride, boolean includePath) {
		RequestSpecification req = given();
		String launchExam = ExamEndPointsReader.get("launchExam");

		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}

		if (includePath) {
			return req.when().get(launchExam + "/2106371");
		} else {
			return req.when().get(launchExam);
		}
	}

	public static Response examList(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber, boolean includeCourseId, String courseId) {

		String examList = ExamEndPointsReader.get("examList");
		RequestSpecification req = given();

		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}

		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "13355");
		}

		if (includeCourseId) {
			req.queryParam("courseId", courseId != null ? courseId : "12422");
		}

		return req.when().get(examList);
	}
	// 2105978

	public static Response examFeedback(boolean includeAuth, String tokenOverride, boolean includePath,
			String overridePath) {
		RequestSpecification req = given();
		String examFeedBack = ExamEndPointsReader.get("examFeedBack"); // base endpoint

		// Add authorization header or spec if requested
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}

		// Append path to endpoint if needed
		if (includePath) {
			if (overridePath != null) {
				examFeedBack += overridePath;
			} else {
				examFeedBack += "432234"; // default appended path segment
			}
		}

		// Execute GET request and return the response
		return req.when().get(examFeedBack);
	}

}
