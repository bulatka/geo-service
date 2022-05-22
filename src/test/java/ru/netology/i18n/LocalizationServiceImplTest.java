package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    final private LocalizationServiceImpl locService = new LocalizationServiceImpl();

    private static final String rus = "Добро пожаловать";
    private static final String defaultMessage = "Welcome";

    static final private Country russia = Country.RUSSIA;
    static final private Country usa = Country.USA;
    static final private Country bra = Country.BRAZIL;
    static final private Country ger = Country.GERMANY;


    @ParameterizedTest
    @MethodSource("methodSource")
    public void testLocale(Country country, String welcomeMessage) {
        String actual = locService.locale(country);

        Assertions.assertEquals(actual, welcomeMessage);
    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of(russia, rus),
                Arguments.of(usa, defaultMessage),
                Arguments.of(bra, defaultMessage),
                Arguments.of(ger, defaultMessage)
        );
    }
}
