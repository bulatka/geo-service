import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static ru.netology.geo.GeoServiceImpl.*;
import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

public class MessageSenderImplTest {

    static final private Map <String, String> headers = new HashMap<>();
    static final private String usaIp = "96.95.94.93";
    static final private String rusIp = "172.95.94.93";
    static final private String rus = "Добро пожаловать";
    static final private String defaultMessage = "Welcome";


    LocalizationService locService = Mockito.mock(LocalizationService.class);

    GeoService geoService = Mockito.mock(GeoService.class);

    @ParameterizedTest
    @MethodSource("methodSource")
    void testSend(String ip, String message) {
        headers.put(ip, IP_ADDRESS_HEADER);
        MessageSender messageSender = new MessageSenderImpl(geoService, locService);
        Mockito.when(geoService.byIp(ip)).thenReturn(
                        new Location(
                        Mockito.anyString(),
                        country,
                        Mockito.anyString(), Mockito.anyInt()));

        String actual = messageSender.send(headers);
        Assertions.assertEquals(message, actual);
    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of(rusIp, rus),
                Arguments.of(usaIp, defaultMessage)
        );
    }
}
