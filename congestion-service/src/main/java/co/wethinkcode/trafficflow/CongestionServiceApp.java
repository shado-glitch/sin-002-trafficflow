package co.wethinkcode.trafficflow;

import java.util.Map;

import io.javalin.Javalin;

public class CongestionServiceApp {

    private int congestionLevel = 0;

    public int getCongestionLevel() {
        return congestionLevel;
    }

    public void setCongestionLevel(int level) {

        if (level < 0 || level > 8) {
            throw new IllegalArgumentException("Congestion level must be between 0 and 8");
        }
        this.congestionLevel = level;
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7022);
        CongestionServiceApp service = new CongestionServiceApp();
        app.get("/health", ctx -> ctx.result("OK"));
        
        // Get current congestion level
        app.get("/congestion", ctx -> {
            ctx.json(Map.of("level",service.getCongestionLevel()));
        });

        app.put("/congestion", ctx -> {

            try {

                Map<String, Object> request =ctx.bodyAsClass(Map.class);
                Object levelValue = request.get("level");

                if (levelValue == null) {
                    ctx.status(400);
                    ctx.json(Map.of("error","level is required"));
                    return;
                }

                int level = ((Number) levelValue).intValue();

                service.setCongestionLevel(level);

                ctx.json(Map.of("level",service.getCongestionLevel()));

            } catch (IllegalArgumentException e) {

                ctx.status(400);
                ctx.json(Map.of("error",e.getMessage()));

            } catch (Exception e) {

                ctx.status(400);
                ctx.json(Map.of("error","Invalid request"));
            }
        });
        // TODO (Tracks the city-wide Congestion Level (0-8).)
        // Add domain endpoints for congestion-service here.
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.trafficflow.mq.MqConfig)
