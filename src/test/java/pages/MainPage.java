package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPage extends BasePage {
    // Локаторы элементов главной страницы
    @FindBy(xpath = "//div[contains(@class, 'nav-user')]")
    private WebElement userHeader;

    @FindBy(xpath = "//*[@id='accounts-can-spend']/span[2]/span/span[2]/span[1]")
    private WebElement financialFreedomBlock;

    @FindBy(xpath = "//span[contains(text(), 'Финансовая свобода')]")
    private WebElement financialFreedomTitle;

    @FindBy(css = "div.pull-right.account-cards > a")
    private List<WebElement> allCards;

    @FindBy(css = "div.pull-right.account-cards")
    private WebElement cardsContainer;

    @FindBy(css = "a[data-content^='Travel']")
    private WebElement travelCard;

    @FindBy(css = "a[data-content^='Золотая']")
    private WebElement goldCard;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    // Проверка отображения блока с картами
    public boolean isCardsSectionDisplayed() {
        return cardsContainer.isDisplayed();
    }

    // Проверка отображения главной страницы
    public boolean isMainPageDisplayed() {
        return userHeader.isDisplayed();
    }

    // Проверка отображения блока "Финансовая свобода"
    public boolean isFinancialFreedomBlockDisplayed() {
        return financialFreedomTitle.isDisplayed() && financialFreedomBlock.isDisplayed();
    }

    // Получение суммы из блока "Финансовая свобода"
    public String getFinancialFreedomAmount() {
        return financialFreedomBlock.getText();
    }

    // Проверка формата суммы
    public boolean isFinancialFreedomAmountValid() {
        String amount = getFinancialFreedomAmount().trim();
        Pattern pattern = Pattern.compile("^\\s*\\d{1,3}( \\d{3})*\\.\\d{2}");
        Matcher matcher = pattern.matcher(amount);
        return matcher.matches();
    }

    public int getCardsCount() {
        return allCards.size();
    }

    public void hoverOverTravelCard() {
        hoverOverCard(travelCard);
    }

    public void hoverOverGoldCard() {
        hoverOverCard(goldCard);
    }

    private void hoverOverCard(WebElement card) {
        new Actions(driver)
                .moveToElement(card)
                .pause(Duration.ofMillis(500))
                .perform();

        waitForTooltip(card);
    }

    private void waitForTooltip(WebElement card) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !Objects.requireNonNull(card.getAttribute("data-content")).isEmpty());
    }

    public String getTravelCardTooltip() {
        return travelCard.getAttribute("data-content");
    }

    public String getGoldCardTooltip() {
        return goldCard.getAttribute("data-content");
    }

    public boolean verifyTravelCardTooltip() {
        String tooltip = getTravelCardTooltip();
        return tooltip.matches("Travel \\*\\d{4}");
    }

    public boolean verifyGoldCardTooltip() {
        String tooltip = getGoldCardTooltip();
        return tooltip.matches("Золотая \\*\\d{4}");
    }

    public WebElement getCardByPartialName(String partialName) {
        String selector = String.format("a[data-content*='%s']", partialName);
        return driver.findElement(By.cssSelector(selector));
    }
}
