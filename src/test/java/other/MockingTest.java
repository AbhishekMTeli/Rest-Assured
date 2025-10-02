package other;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

public class MockingTest {
	private WireMockServer wireMockServer;

	@BeforeTest
	public void setup() {
		wireMockServer = new WireMockServer(8080);
		wireMockServer.start();
		configureFor("localhost", 8080);
		ApiWireMocking.setupSampleStub(wireMockServer);
	}

	@Test
	public void test() {
		String response = ApiClient.get("http://localhost:8080/sample");
		System.out.println(response);
		Assert.assertTrue(response.contains("Stubbed response"));
	}

	@AfterTest
	public void teardown() {
		if (wireMockServer != null) {
			wireMockServer.stop();
		}
	}

}
