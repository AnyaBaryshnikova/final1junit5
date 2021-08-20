package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.basetest.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SecondTest extends BaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {"500 000", "20 000", 12, 920.60},
                {"25 000", "1 000", 6, 13.64}
        });
    }

    @Parameterized.Parameter(0)
    public String depositAmount;

    @Parameterized.Parameter(1)
    public String monthlyPayment;

    @Parameterized.Parameter(2)
    public int months;

    @Parameterized.Parameter(3)
    public double percent;



    @Test
    @DisplayName("Проверка второго сценария")
    public void startTest() {
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
