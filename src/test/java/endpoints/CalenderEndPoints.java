package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ReferenceMaterialEndPointsReader;

public class CalenderEndPoints extends BaseTest {
	public static Response courseCalender(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String courseCalender = ReferenceMaterialEndPointsReader.get("courseCalender");
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

		return req.when().get(courseCalender);
	}

	public static Response simCalender(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String simCalender = ReferenceMaterialEndPointsReader.get("simCalender");
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
			req.queryParam("staffNumber", staffNumber != null ? staffNumber : "20198");
		}

		return req.when().get(simCalender);
	}
}
