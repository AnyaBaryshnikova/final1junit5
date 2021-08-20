package org.example.basetest;


import org.example.managers.DriverManager;
import org.example.managers.InitManager;
import org.example.managers.PageManager;
import org.example.managers.TestPropManager;
import org.example.utils.MyAllureListner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.example.utils.Constants.BASE_URL;

@ExtendWith(MyAllureListner.class)
public class BaseTest {

    /**
     * Менеджер страничек
     */
    protected PageManager app = PageManager.getPageManager();

    /**
     * Менеджер WebDriver
     *
     */
    private final DriverManager driverManager = DriverManager.getDriverManager();

    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get(TestPropManager.getTestPropManager().getProperty(BASE_URL));
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}
