package action.in.blog.domain;

import action.in.blog.domain.transport.AirPlane;
import action.in.blog.domain.transport.Transport;
import action.in.blog.domain.transport.Truck;

public class TransportManager {

    public Transport reserveTransport(String transportType) {
        Transport transport = getTransport(transportType);
        transport.reserve();
        return transport;
    }

    protected Transport getTransport(String transportType) {
        switch (transportType) {
            case "Truck":
                return new Truck();
            case "AirPlane":
                return new AirPlane();
            default:
                throw new RuntimeException("Not Supported Transport Type: " + transportType);
        }
    }
}
