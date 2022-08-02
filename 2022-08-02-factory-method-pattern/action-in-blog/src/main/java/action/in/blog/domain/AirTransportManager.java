package action.in.blog.domain;

import action.in.blog.domain.transport.AirPlane;
import action.in.blog.domain.transport.Transport;

public class AirTransportManager extends TransportManager {

    @Override
    public Transport getTransport(String transportType) {
        switch (transportType) {
            case "AirPlane":
                return new AirPlane();
            default:
                throw new RuntimeException("Not Supported Transport Type: " + transportType);
        }
    }
}
