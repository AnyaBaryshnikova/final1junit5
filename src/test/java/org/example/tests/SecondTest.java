package org.example.tests;

import org.example.basetest.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class SecondTest extends BaseTest {



    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("500 000", "20 000", 12, 920.60),
                Arguments.of("25 000", "1 000", 6, 13.64));
    }

    @ParameterizedTest
    @MethodSource("data")
    @DisplayName("Проверка второго сценария")
    public void startTest(String depositAmount, String monthlyPayment, int months, double percent) {
        app.getMainPage()
                .choseMenu("Вклады")
                .choseCurrency("Доллары США")
                .addDepositAmount(depositAmount)
                .addMonths(months)
                .addMounthlyPayment(monthlyPayment)
                .selectCheckBox("Ежемесячная капитализация", true)
                .checkAccuredInrest(percent)
                .checkRefill()
                .checkAmountWithdrawn();

    }
}
