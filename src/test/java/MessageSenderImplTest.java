import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

public class MessageSenderImplTest {

    static final private Map<String, String> HEADERS = new HashMap<>();
    static final private String USA_IP = "96.95.94.93";
    static final private String RUS_IP = "172.95.94.93";
    static final private String RUS = "Добро пожаловать";
    static final private String DEFAULT_MESSAGE = "Welcome";
    static final private String MOCK_STR = "";
    static final private int MOCK_INT = 111;


    LocalizationService locService = Mockito.mock(LocalizationServiceImpl.class);

    GeoService geoService = Mockito.mock(GeoServiceImpl.class);

    @ParameterizedTest
    @MethodSource("methodSource")
    void testSend(String ip, String message, Country country) {
        HEADERS.put(IP_ADDRESS_HEADER, ip);
        MessageSender messageSender = new MessageSenderImpl(geoService, locService);
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(
                new Location(
                        MOCK_STR,
                        country, MOCK_STR, MOCK_INT));
        Mockito.when(locService.locale(country)).thenCallRealMethod();
        String actual = messageSender.send(HEADERS);
        Assertions.assertEquals(message, actual);
    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of(RUS_IP, RUS, Country.RUSSIA),
                Arguments.of(USA_IP, DEFAULT_MESSAGE, Country.USA)
        );
    }
}