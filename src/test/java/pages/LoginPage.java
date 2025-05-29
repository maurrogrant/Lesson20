package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    // Элементы страницы авторизации
    @FindBy(id = "login-button")
    private WebElement btnLogin;

    @FindBy(id = "username")
    private WebElement inputUsername;

    @FindBy(name = "password")
    private WebElement inputPassword;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Метод для открытия страницы авторизации
    public void navigateTo() {
        driver.get("https://idemo.bspb.ru");
    }

    // Метод для ввода логина
    public void fillUsername(String username) {
        inputUsername.clear();
        inputUsername.sendKeys(username);
    }

    // Метод для ввода пароля
    public void fillPassword(String password) {
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    // Метод для нажатия кнопки входа
    public void submitLogin() {
        btnLogin.click();
    }

    // Метод для выполнения полного процесса авторизации
    public void performLogin(String username, String password) {
        fillUsername(username);
        fillPassword(password);
        submitLogin();
    }
}
