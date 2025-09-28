package testScripts;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.Q_E_F_Endpoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class QueryDeleteTest extends BaseTest {
	@Test(description = "Verify Admin Get All Queries API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyAdminGetAllQueriesAPI_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = Q_E_F_Endpoints.deleteQuery(true, null);
		ApiUtils.validateStatusCode(response, 200);
		String queryId = "1234";
		String expectedMessage = "deleted query " + queryId + " successfully";
		response.then().body(equalTo(expectedMessage));
	}

	@Test(description = "Verify Admin Get All Queries API returns 401 Unauthorized for invalid authentication token")
	public void verifyAdminGetAllQueriesAPI_WithInvalidAuth_ShouldReturn401() {
		Response response = Q_E_F_Endpoints.deleteQuery(true, "ovverride token");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Admin Get All Queries API returns 401 Unauthorized when no authentication is provided")
	public void verifyAdminGetAllQueriesAPI_WithoutAuth_ShouldReturn401() {
		Response response = Q_E_F_Endpoints.deleteQuery(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Admin Get All Queries API returns 401 Unauthorized for expired token")
	public void verifyAdminGetAllQueriesAPI_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = Q_E_F_Endpoints.deleteQuery(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}
}
