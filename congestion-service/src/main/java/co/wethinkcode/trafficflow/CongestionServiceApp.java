package co.wethinkcode.trafficflow;

import io.javalin.Javalin;

public class CongestionServiceApp {

    private int congestionLevel = 0;

    public int getCongestionLevel() {
        return congestionLevel;
    }

    public void setCongestionLevel(int level) {
        
         if (level < 0 || level > 8) {
            throw new IllegalArgumentException(
                    "Congestion level must be between 0 and 8"
            );
        }
        this.congestionLevel = level;
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7022);

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Tracks the city-wide Congestion Level (0-8).)
        // Add domain endpoints for congestion-service here.
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.trafficflow.mq.MqConfig)
