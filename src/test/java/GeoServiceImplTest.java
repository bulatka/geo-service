import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static ru.netology.geo.GeoServiceImpl.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GeoServiceImplTest {
    final private GeoServiceImpl geoService = new GeoServiceImpl();

    static final private Location moscow = new Location(
            "Moscow", Country.RUSSIA, "Lenina", 15);
    static final private Location localhost = new Location(
            null, null, null, 0);
    static final private Location newYork = new Location(
            "New York", Country.USA, " 10th Avenue", 32);
    static final private Location russia = new Location(
            "Moscow", Country.RUSSIA, null, 0);
    static final private Location usa = new Location(
            "New York", Country.USA, null, 0);

    @ParameterizedTest
    @MethodSource("methodSource")
    void testLocationByIp(String ip, Location location) {


        Location locationAct = geoService.byIp(ip);

        Assertions.assertEquals(locationAct.getCity(), location.getCity());
        Assertions.assertEquals(locationAct.getCountry(), location.getCountry());
        Assertions.assertEquals(locationAct.getBuilding(), location.getBuilding());
        Assertions.assertEquals(locationAct.getStreet(), location.getStreet());

    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of(MOSCOW_IP, moscow),
                Arguments.of(LOCALHOST, localhost),
                Arguments.of(NEW_YORK_IP, newYork),
                Arguments.of("172.171.170.169", russia),
                Arguments.of("96.95.94.93", usa)
        );
    }

    @Test
    void testLocationByCoordinates() {
        double latitude = 55.12;
        double longitude = 91.80;

        Assertions.assertThrows(RuntimeException.class, () -> {
            geoService.byCoordinates(latitude, longitude);
        }, "Not implemented");

    }
}
