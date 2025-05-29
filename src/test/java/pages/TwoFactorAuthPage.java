package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TwoFactorAuthPage extends BasePage {
    // Локаторы элементов страницы
    @FindBy(id = "otp-code")
    private WebElement otpCodeField;

    @FindBy(id = "login-otp-button")
    private WebElement loginOtpButton;

    @FindBy(xpath = "//div[contains(text(),'Отправили СМС код на ваш номер телефона')]")
    private WebElement confirmationText;

    public TwoFactorAuthPage(WebDriver driver) {
        super(driver);
    }

    // Проверка отображения страницы
    public boolean isTwoFactorAuthPageDisplayed() {
        return confirmationText.isDisplayed();
    }

    // Ввод кода подтверждения
    public void enterOtpCode(String code) {
        otpCodeField.clear();
        otpCodeField.sendKeys(code);
    }

    // Клик по кнопке подтверждения кода
    public MainPage clickLoginOtpButton() {
        loginOtpButton.click();
        return new MainPage(driver);
    }
}
