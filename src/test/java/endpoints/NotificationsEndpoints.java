package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.NotificationsEndPointsReader;

public class NotificationsEndpoints extends BaseTest {
	public static Response getNotification(boolean includeAuth, String ovverrideToken) {
		String getNotification = NotificationsEndPointsReader.get("getNotification");
		RequestSpecification req = given();
		if (includeAuth) {
			if (ovverrideToken != null) {
				req.spec(requestSpecNoAuth).header("Autharization", "Bearer " + ovverrideToken);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}
		return req.when().get(getNotification);
	}
}
