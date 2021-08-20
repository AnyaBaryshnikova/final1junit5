package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.basetest.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FirstTest extends BaseTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {"300 000", "50 000", 6, 12243.26},
                {"100 000", "5 000", 3, 1116.38 }
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
    @DisplayName("Проверка первого сценария")
    public void startTest() {
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
