package tests;

import config.ConfigTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.TwoFactorAuthPage;

import static org.junit.Assert.assertTrue;

public class LoginTest extends ConfigTest {
    private WebDriver driver;

    @Before
    public void init() {
        ConfigTest.initConfig();
        driver = ConfigTest.driver;
    }

    @Test
    public void testFullScenario() {
        // Шаг авторизации
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.performLogin("demo", "demo");

        // Проверка отображения формы двухфакторной аутентификации
        TwoFactorAuthPage twoFactorAuthPage = new TwoFactorAuthPage(driver);
        assert twoFactorAuthPage.isTwoFactorAuthPageDisplayed() :
                "Страница двухфакторной аутентификации не отображается";

        // Шаг ввода кода подтверждения
        twoFactorAuthPage.enterOtpCode("0000");
        MainPage mainPage = twoFactorAuthPage.clickLoginOtpButton();

        // Проверка успешного входа
        assert mainPage.isMainPageDisplayed() : "Главная страница не отображается после входа";

        // Шаг проверки блока "Финансовая свобода"
        assert mainPage.isFinancialFreedomBlockDisplayed() :
                "Блок 'Финансовая свобода' не отображается на странице";

        assert mainPage.isFinancialFreedomAmountValid() :
                "Сумма в блоке 'Финансовая свобода' не соответствует формату '123 456 789.00 ₽'";
        System.out.println("Сумма финансовой свободы: " + mainPage.getFinancialFreedomAmount());

        // Шаг проверки подсказок карт
        // Verify cards section
        assertTrue("Отсутствует секция с картами", mainPage.isCardsSectionDisplayed());
        assertTrue("Карты не найдены", mainPage.getCardsCount() >= 3);

        // Test Travel card
        mainPage.hoverOverTravelCard();
        assertTrue("Travel card tooltip format incorrect",
                mainPage.verifyTravelCardTooltip());
        System.out.println("Travel card: " + mainPage.getTravelCardTooltip());

        // Test Gold card
        mainPage.hoverOverGoldCard();
        assertTrue("Gold card tooltip format incorrect",
                mainPage.verifyGoldCardTooltip());
        System.out.println("Gold card: " + mainPage.getGoldCardTooltip());
    }

    // Завершение работы после каждого теста
    @After
    public void tearDown() {
        ConfigTest.quit();
    }
}
