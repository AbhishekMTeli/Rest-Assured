package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ReferenceMaterialEndPointsReader;

public class ReferenceMaterialEndPoints extends BaseTest {

	public static Response getReferenceMaterial(boolean includeAuth, String tokenOverride, boolean includeStaffNumber,
			String staffNumber) {

		RequestSpecification req = given();
		String referenceMaterialUrl = ReferenceMaterialEndPointsReader.get("referenceMeterial");
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

		return req.when().get(referenceMaterialUrl);
	}
}
