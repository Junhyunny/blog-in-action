package action.in.blog.domain;

import action.in.blog.domain.transport.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransportManagerTests {

    private static Stream<Arguments> getTransportTypes() {
        return Stream.of(
                Arguments.of("Truck", Truck.class),
                Arguments.of("AirPlane", AirPlane.class)
        );
    }

    @ParameterizedTest
    @MethodSource("getTransportTypes")
    void reserve_supported_transport(String transportType, Class clz) {

        TransportManager sut = new TransportManager();
        Transport reservedTransport = sut.reserveTransport(transportType);

        assertThat(reservedTransport, instanceOf(clz));
        assertThat(reservedTransport.isReserved(), equalTo(true));
    }

    private static Stream<Arguments> getNotSupportedTransportTypes() {
        return Stream.of(
                Arguments.of("BulkShip", "Not Supported Transport Type: BulkShip"),
                Arguments.of("ContainerShip", "Not Supported Transport Type: ContainerShip"),
                Arguments.of("Trailer", "Not Supported Transport Type: Trailer")
        );
    }

    @ParameterizedTest
    @MethodSource("getNotSupportedTransportTypes")
    void throw_not_support_exception_from_transport_manager(String transportType, String errorMessage) {

        Throwable throwable = assertThrows(RuntimeException.class, () -> new TransportManager().reserveTransport(transportType));
        assertThat(throwable.getMessage(), equalTo(errorMessage));
    }

    private static Stream<Arguments> getRoadTransportType() {
        return Stream.of(
                Arguments.of("Truck", Truck.class),
                Arguments.of("Trailer", Trailer.class)
        );
    }

    @ParameterizedTest
    @MethodSource("getRoadTransportType")
    void reserve_transport_via_road_transport_manager(String transportType, Class clz) {

        TransportManager sut = new RoadTransportManager();
        Transport reservedTransport = sut.reserveTransport(transportType);

        assertThat(reservedTransport, instanceOf(clz));
        assertThat(reservedTransport.isReserved(), equalTo(true));
    }

    private static Stream<Arguments> getNotSupportedRoadTransportTypes() {
        return Stream.of(
                Arguments.of("BulkShip", "Not Supported Transport Type: BulkShip"),
                Arguments.of("ContainerShip", "Not Supported Transport Type: ContainerShip"),
                Arguments.of("AirPlane", "Not Supported Transport Type: AirPlane")
        );
    }

    @ParameterizedTest
    @MethodSource("getNotSupportedRoadTransportTypes")
    void throw_not_support_exception_from_road_transport_manager(String transportType, String errorMessage) {

        Throwable throwable = assertThrows(RuntimeException.class, () -> new RoadTransportManager().reserveTransport(transportType));
        assertThat(throwable.getMessage(), equalTo(errorMessage));
    }

    private static Stream<Arguments> getShipTransportType() {
        return Stream.of(
                Arguments.of("BulkShip", BulkShip.class),
                Arguments.of("ContainerShip", ContainerShip.class)
        );
    }

    @ParameterizedTest
    @MethodSource("getShipTransportType")
    void reserve_transport_via_ship_transport_manager(String transportType, Class clz) {

        TransportManager sut = new ShipTransportManager();
        Transport reservedTransport = sut.reserveTransport(transportType);

        assertThat(reservedTransport, instanceOf(clz));
        assertThat(reservedTransport.isReserved(), equalTo(true));
    }

    private static Stream<Arguments> getNotSupportedShipTransportTypes() {
        return Stream.of(
                Arguments.of("AirPlane", "Not Supported Transport Type: AirPlane"),
                Arguments.of("Truck", "Not Supported Transport Type: Truck"),
                Arguments.of("Trailer", "Not Supported Transport Type: Trailer")
        );
    }

    @ParameterizedTest
    @MethodSource("getNotSupportedShipTransportTypes")
    void throw_not_support_exception_from_ship_transport_manager(String transportType, String errorMessage) {

        Throwable throwable = assertThrows(RuntimeException.class, () -> new ShipTransportManager().reserveTransport(transportType));
        assertThat(throwable.getMessage(), equalTo(errorMessage));
    }

    @Test
    void reserve_transport_via_air_transport_manager() {

        TransportManager sut = new AirTransportManager();
        Transport reservedTransport = sut.reserveTransport("AirPlane");

        assertThat(reservedTransport, instanceOf(AirPlane.class));
        assertThat(reservedTransport.isReserved(), equalTo(true));
    }

    private static Stream<Arguments> getNotSupportedAirTransportTypes() {
        return Stream.of(
                Arguments.of("Truck", "Not Supported Transport Type: Truck"),
                Arguments.of("Trailer", "Not Supported Transport Type: Trailer"),
                Arguments.of("BulkShip", "Not Supported Transport Type: BulkShip"),
                Arguments.of("ContainerShip", "Not Supported Transport Type: ContainerShip")
        );
    }

    @ParameterizedTest
    @MethodSource("getNotSupportedAirTransportTypes")
    void throw_not_support_exception_from_air_transport_manager(String transportType, String errorMessage) {

        Throwable throwable = assertThrows(RuntimeException.class, () -> new AirTransportManager().reserveTransport(transportType));
        assertThat(throwable.getMessage(), equalTo(errorMessage));
    }
}
