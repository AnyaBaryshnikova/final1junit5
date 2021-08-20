package org.example.pages;

import org.example.managers.DriverManager;
import org.example.managers.PageManager;
import org.example.managers.TestPropManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected final DriverManager driverManager = DriverManager.getDriverManager();

    protected PageManager pageManager = PageManager.getPageManager();

    protected Actions action = new Actions(driverManager.getDriver());

    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);

    private final TestPropManager props = TestPropManager.getTestPropManager();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     */
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видимым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
        return element;
    }

    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }

    public void fillFields(WebElement element, String data) {
        scrollWithOffset(element, 0, -500);
        element.click();
        element.sendKeys(data);
    }

    /**
     * Проверяем есть ли элемент на странице
     *
     * @param xPath xPath к элементу
     * @return
     */
    public boolean checkIfElementPresent(String xPath) {
        try {
            driverManager.getDriver().findElement(By.xpath(xPath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Поставить галочку, если надо
     * @param element элемент
     * @param checked ставим галочку, если тру
     */
    protected void checkCheckBox(WebElement element, boolean checked){
        String elChecked = element.getAttribute("class");

        if(elChecked.contains("checked") & checked || !elChecked.contains("checked") & !checked)
            return;
        element.click();
    }

}
