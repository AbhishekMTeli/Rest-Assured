package other;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class PollingUtil {

    /**
     * Polls repeatedly until the successCondition is true or timeout occurs.
     *
     * @param successCondition A lambda returning Boolean indicating success state
     * @param intervalMillis   Poll interval in milliseconds between checks
     * @param timeoutSeconds   How long to wait before giving up, in seconds
     * @return true if successCondition became true within timeout, else false
     */
    public static boolean pollUntil(Supplier<Boolean> successCondition, long intervalMillis, int timeoutSeconds) {
        Instant endTime = Instant.now().plus(Duration.ofSeconds(timeoutSeconds));
        while (Instant.now().isBefore(endTime)) {
            if (successCondition.get()) {
                return true; // Success condition met
            }
            try {
                Thread.sleep(intervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false; // Interrupted during sleep
            }
        }
        return false; // Timeout without success
    }
}
