package org.example.managers;


import org.example.pages.DepositPage;
import org.example.pages.MainPage;

public class PageManager {
    private static PageManager pageManager;

    private MainPage mainPage;
    private DepositPage depositPage;

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    private PageManager() {
    }

    public MainPage getMainPage() {
        if (mainPage == null)
            mainPage = new MainPage();
        return mainPage;
    }

    public DepositPage getDepositPage() {
        if (depositPage == null)
            depositPage = new DepositPage();
        return depositPage;
    }
}