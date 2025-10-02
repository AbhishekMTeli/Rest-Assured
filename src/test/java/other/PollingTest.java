package other;

public class PollingTest {

	public static void main(String[] args) {
		boolean isApiReady = PollingUtil.pollUntil(() -> {
			// Example API call to check status
			String status = ApiClient.getStatus("/status");
			return "COMPLETED".equalsIgnoreCase(status);
		}, 2000, 30); // poll every 2 seconds, timeout after 30 seconds

		if (isApiReady) {
			System.out.println("API process completed.");
		} else {
			System.out.println("Timeout waiting for API.");
		}
	}
}
