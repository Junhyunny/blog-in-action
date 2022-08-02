package action.in.blog.domain;

import action.in.blog.domain.transport.BulkShip;
import action.in.blog.domain.transport.ContainerShip;
import action.in.blog.domain.transport.Transport;

public class ShipTransportManager extends TransportManager {

    @Override
    public Transport getTransport(String transportType) {
        switch (transportType) {
            case "BulkShip":
                return new BulkShip();
            case "ContainerShip":
                return new ContainerShip();
            default:
                throw new RuntimeException("Not Supported Transport Type: " + transportType);
        }
    }
}
