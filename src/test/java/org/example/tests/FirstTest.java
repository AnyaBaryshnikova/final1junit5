package org.example.tests;

import org.example.basetest.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class FirstTest extends BaseTest {

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("300 000", "50 000", 6, 12243.26),
                Arguments.of("100 000", "5 000", 3, 1116.38));
    }

    @ParameterizedTest
    @MethodSource("data")
    @DisplayName("Проверка первого сценария")
    public void startTest(String depositAmount, String monthlyPayment, int months, double percent) {
        app.getMainPage()
                .choseMenu("Вклады")
                .choseCurrency("Рубли")
                .addDepositAmount(depositAmount)
                .addMonths(months)
                .addMounthlyPayment(monthlyPayment)
                .selectCheckBox("Ежемесячная капитализация", true)
                .checkAccuredInrest(percent)
                .checkRefill()
                .checkAmountWithdrawn();
    }
}
