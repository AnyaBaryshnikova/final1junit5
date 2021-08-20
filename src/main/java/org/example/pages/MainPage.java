package org.example.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {

    //меню
    @FindBy(xpath = "//nav[@class='nav nav_primary']/ul[@class='nav__list']")
    List<WebElement> menu;

    // тоже меню
    @FindBy(xpath = "//div[@class='services services_main']//div[@class='service__title-text']")
    List<WebElement> listMenu;

    //кнопка "Выбрать вклад"
    @FindBy(xpath = "//a[contains(text(), 'Выбрать вклад')]")
    WebElement depositButton;

    /**
     * @param nameMenu название меню
     * @return ту же страничку
     */
    @Step("Выбираем {nameMenu}")
    public DepositPage choseMenu(String nameMenu) {

        if (checkIfElementPresent("//div[@class = 'main-screen__title']")) {
            for (WebElement menuItem : listMenu) {
                if (menuItem.getText().trim().equalsIgnoreCase(nameMenu)) {
                    waitUtilElementToBeClickable(menuItem.findElement(By.xpath("./../a[1]"))).click();
                    return pageManager.getDepositPage().checkDepositPageOpen();
                }
            }
            Assertions.fail("Меню '" + nameMenu + "' не было найдено на стартовой странице!");
            return pageManager.getDepositPage().checkDepositPageOpen();

        }

        for (WebElement menuItem : menu) {
            if (menuItem.getText().contains(nameMenu)) {
                waitUtilElementToBeClickable(menuItem).click();
                clickDepositButton(); // кликаем тут, тк, если откроется главная страница, которая выглядит по-другому, то там кнопки нет
                return pageManager.getDepositPage().checkDepositPageOpen();
            }
        }
        Assertions.fail("Меню '" + nameMenu + "' не было найдено на стартовой странице!");
        return pageManager.getDepositPage().checkDepositPageOpen();
    }

    /**
     * Кликаем на кнопку выбрать вклад
     *
     * @return возвращаем страницу со вкадами
     */
    @Step("Кликаем на кнопку 'выбрать вклад'")
    public DepositPage clickDepositButton() {

        depositButton.click();

        return pageManager.getDepositPage().checkDepositPageOpen();
    }
}
