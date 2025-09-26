package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ReferenceMaterialEndPointsReader;

public class AnalysisEndPoints extends BaseTest {
	public static Response analysis(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String analysis = ReferenceMaterialEndPointsReader.get("analysis");
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

		// Handle StaffNumber
		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "23077");
		}

		return req.when().get(analysis);
	}

	public static Response courseTime_Analysis(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String courseTime_Analysis = ReferenceMaterialEndPointsReader.get("courseTime_Analysis");
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

		// Handle StaffNumber
		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "13355");
		}

		return req.when().get(courseTime_Analysis);
	}

	public static Response examMarks_Analysis(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String examMarks_Analysis = ReferenceMaterialEndPointsReader.get("examMarks_Analysis");
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

		// Handle StaffNumber
		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "23077");
		}

		return req.when().get(examMarks_Analysis);
	}

	public static Response examTime_Anaysis(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String examTime_Anaysis = ReferenceMaterialEndPointsReader.get("examTime_Anaysis");
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

		// Handle StaffNumber
		if (includeStaffNumber) {
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "23077");
		}

		return req.when().get(examTime_Anaysis);
	}
}
