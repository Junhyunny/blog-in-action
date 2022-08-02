package action.in.blog.domain;

import action.in.blog.domain.transport.Trailer;
import action.in.blog.domain.transport.Transport;
import action.in.blog.domain.transport.Truck;

public class RoadTransportManager extends TransportManager {

    @Override
    protected Transport getTransport(String transportType) {
        switch (transportType) {
            case "Truck":
                return new Truck();
            case "Trailer":
                return new Trailer();
            default:
                throw new RuntimeException("Not Supported Transport Type: " + transportType);
        }
    }
}
