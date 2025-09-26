package endpoints;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.UserEndPointsReader;

public class UserEndPoints extends BaseTest {
	public static Response getUserInfo(boolean includeAuth, String OverrideToken) {
		RequestSpecification req = given();
		String getUserInfo = UserEndPointsReader.get("getUserInfo");
		if (includeAuth) {
			if (OverrideToken != null) {
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + OverrideToken);
			} else {
				req.spec(requestSpecAuth);
			}
		}
		return req.when().get(getUserInfo);
	}

	public static Response generalInfoAndGreetings(boolean includeAuth, String OverrideToken) {
		RequestSpecification req = given();
		String generalInfoAndGreetings = UserEndPointsReader.get("generalInfoAndGreetings");
		if (includeAuth) {
			if (OverrideToken != null) {
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + OverrideToken);
			} else {
				req.spec(requestSpecAuth);
			}
		}
		return req.when().get(generalInfoAndGreetings);
	}

	public static Response trainingProfileData(boolean includeAuth, String OverrideToken) {
		RequestSpecification req = given();
		String trainingProfileData = UserEndPointsReader.get("trainingProfileData");
		if (includeAuth) {
			if (OverrideToken != null) {
				req.spec(requestSpecNoAuth).header("Authorization", "Bearer " + OverrideToken);
			} else {
				req.spec(requestSpecAuth);
			}
		}
		return req.when().get(trainingProfileData);
	}
}
