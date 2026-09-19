package co.wethinkcode.trafficflow.Exception;

public class UnknownIntersectionException
        extends RuntimeException {

    public UnknownIntersectionException(String message) {
        super(message);
    }
}