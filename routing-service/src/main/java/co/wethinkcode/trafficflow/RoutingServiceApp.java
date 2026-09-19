package co.wethinkcode.trafficflow;

import java.util.Map;

import co.wethinkcode.trafficflow.Exception.DependencyException;
import co.wethinkcode.trafficflow.Exception.UnknownIntersectionException;
import io.javalin.Javalin;

public class RoutingServiceApp {

    public static void main(String[] args) {
          RoutingService service = new RoutingService();
        Javalin app = Javalin.create().start(7023);

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Provides estimated travel times based on congestion and intersection.)
        // Add domain endpoints for routing-service here.

          app.get( "/route/{intersectionId}",
                ctx -> {
                    String intersectionId =
                            ctx.pathParam("intersectionId");

                    try {

                        RouteResult result = service.calculateRoute(intersectionId);
                        ctx.json(result);

                    } catch (UnknownIntersectionException e) {

                        ctx.status(404);
                        ctx.json(Map.of("error",e.getMessage()));

                    } catch (DependencyException e) {

                        ctx.status(503);
                        ctx.json(Map.of("error",e.getMessage()));

                    } catch (Exception e) {

                        ctx.status(500);
                        ctx.json(Map.of("error","Routing service failed"));
                    }
                }
        );
    
    }
}

// MQ TODO: subscribes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.trafficflow.mq.MqConfig)
