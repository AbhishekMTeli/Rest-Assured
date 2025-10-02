package other;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ApiWireMocking {

	public static void setupSampleStub(WireMockServer wireMockServer) {
		wireMockServer.stubFor(get(urlEqualTo("/sample")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json").withBody("{\"message\":\"Stubbed response\"}")));
	}

}
