package org.example.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DepositPage extends BasePage {

    @FindBy(xpath = "//h1")
    WebElement title;

    //Валюта
    @FindBy(xpath = "//div[@class='calculator__currency-content']")
    WebElement currency;

    //Сумма вклада
    @FindBy(xpath = "//input[@name='amount']")
    WebElement depositAmount;
    //Есть сейчас
    @FindBy(xpath = "//span[@class='js-calc-amount']")
    WebElement amountNow;



    // Ежемесячный платеж
    @FindBy(xpath = "//input[@name='replenish']")
    WebElement monthlyPayment;

    //Пополнение за все месяцы
    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    WebElement replenishment;

    // капитализация
    @FindBy(xpath = "//span[contains(text(), 'капитализация')]/../..//div[contains(@class, 'calculator__check')]")
    WebElement capitalization;

    //частичное снятие
    @FindBy(xpath = "//span[contains(text(), 'Частичное снятие')]/../..//div[contains(@class, 'calculator__check')]")
    WebElement partialWithdraw;

    // Начисленный %
    @FindBy(xpath = "//span[@class='js-calc-earned']")
    WebElement accuredInrest;


    //сумма к снятию

    @FindBy(xpath = "//span[@class='js-calc-result']")
    WebElement amountWithdarwn;

    // выбранное количество месяцев
    @FindBy(xpath = "//div[@class='jq-selectbox__select-text']")
    WebElement months;

    @FindBy(xpath = "//div[@class='jq-selectbox__trigger']")
    WebElement monthsBtn;

    private int monthlyPay = 0;    //ежемесячное пополнение
    private int firstPay = 0;       //первое пополнение
    private int monthsDeposit = 0;  //количество месяцев


    public DepositPage checkDepositPageOpen() {
        waitUtilElementToBeVisible(title);
        Assertions.assertTrue( title.getText().contains("Вклады"), "Мы не на странице со вкладами");
        return this;

    }

    /**
     * Выбираем валюту
     *
     * @param name название валюты
     * @return возвращаем ту же страничку
     */
    @Step("Выбираем валюту {name} на странице со вкладами")
    public DepositPage choseCurrency(String name) {
        scrollWithOffset(currency, 0, -500);
        if (name.equals("Рубли")) {
            WebElement rubCurr = currency.findElement(By.xpath("./label[1]"));
            rubCurr.click();
            return this;
        }
        if (name.equals("Доллары США")) {
            WebElement dolCurr = currency.findElement(By.xpath("./label[2]"));
            dolCurr.click();
            return this;
        }
        Assertions.fail("Валюта '" + name + "' не найдена!");
        return this;
    }

    /**
     * Вводим сумму вклада
     *
     * @param money сумма
     * @return возвращаем ту же страничку
     */
    @Step("Вводим сумму вклада {money}")
    public DepositPage addDepositAmount(String money) {
        firstPay = Integer.parseInt(money.replace(" ", ""));

        fillFields(depositAmount, money.replace(" ", ""));
        wait.until(ExpectedConditions.textToBePresentInElement(amountNow, money));
        return this;
    }

    /**
     * Добавляем срок депозита
     *
     * @param amount количество месяцев, срок
     * @return
     */
    @Step()
    public DepositPage addMonths(int amount) {
        monthsDeposit = amount;
        int index = ((int)( amount / 3) + amount % 3);
        waitUtilElementToBeClickable(monthsBtn).click();
        String el = String.format("//li[contains(text(), 'месяц')][%s]", index);


        try {
            WebElement month = driverManager.getDriver().findElement(By.xpath(el));
            waitUtilElementToBeClickable(month).click();
        } catch (Exception e) {
            Assertions.fail("Невозможно указать срок депозита равный " + amount + "мес.");

        }
        return this;
    }

    /**
     * Вводим сумму ежемесячного пополнения
     *
     * @param money сумма
     * @return возвращаем ту же страничку
     */
    @Step("Вводим сумму ежемесячного платежа {money}")
    public DepositPage addMounthlyPayment(String money) {
        String text = accuredInrest.getText();
        monthlyPay = Integer.parseInt(money.replace(" ", ""));

        fillFields(monthlyPayment, money);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(accuredInrest, text)));
        return this;
    }

    /**
     * Поставить или убрать галочку
     *
     * @param strElement название элемента
     * @param flag       значение
     * @return
     */
    @Step("Ставим галочку на поле {strElement}")
    public DepositPage selectCheckBox(String strElement, boolean flag) {
        String text = accuredInrest.getText();
        switch (strElement) {
            case "Ежемесячная капитализация":
                checkCheckBox(capitalization, flag);
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(accuredInrest, text)));
                break;
            case "Частичное снятие":
                checkCheckBox(partialWithdraw, flag);
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(accuredInrest, text)));
                break;
            default:
                Assertions.fail("Поле с наименованием '" + strElement + "' отсутствует на странице");
        }
        return this;
    }


    /**
     * Проверяем начисленный процент
     *
     * @param expected процент, который ожидаем увидеть
     * @return
     */
    public DepositPage checkAccuredInrest(double expected) {
        double actual = Double.parseDouble(accuredInrest.getText().replace(",", ".").replace(" ", ""));
        Assertions.assertTrue(actual == expected, "Начисленный процент, который ожидали увидеть " + expected + ""
                + " не совпадает с фактическим: " + actual);

        return this;

    }

    /**
     * Проверяем пополнение за все месяцы
     *
     * @return
     */
    public DepositPage checkRefill() {

        int actual = Integer.parseInt(replenishment.getText().replace(" ", ""));

        Assertions.assertTrue(monthlyPay * (monthsDeposit - 1) == actual, "Пополнение за все месяцы не совпадает с ожидаемым");

        return this;

    }

    /**
     * Проверяем сумму к снятию
     *
     * @return
     */
    public DepositPage checkAmountWithdrawn() {

        //начисленный процент
        double proc = Double.parseDouble(accuredInrest.getText().replace(",", ".").replace(" ", ""));

        double expected = firstPay + monthlyPay * (monthsDeposit - 1) + proc;

        double actual = Double.parseDouble(amountWithdarwn.getText().replace(",", ".").replace(" ", ""));


        Assertions.assertTrue(actual == expected, "Сумма к снятию в конце не совпадает с ожидаемой");
        return this;

    }

}
