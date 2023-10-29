package codingChallenge;

import java.util.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class PageActions {

    static String left = "left";
    static String right = "right";

    static String ALLERT_MESSAGE_RIGHT = "Yay! You find it!";
    static String ALLERT_MESSAGE_WRONG = "Oops! Try Again!";

    public static WebDriver openPage() {
        // maximizes the window and launches Google Chrome to the webpage for the scaling simulation
        
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("http://sdetchallenge.fetch.com/");
        driver.manage().window().maximize();

        return driver;
    }

    public static void clickWeighButton(WebDriver driver) {
        // clicks the weigh button
        
        int originalLengthOfWeighings = getWeighingsList(driver).size();

        WebElement button = driver.findElement(By.id("weigh"));
        button.click();

        // wait until weighing list is updated
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(d -> getWeighingsList(driver).size() > originalLengthOfWeighings);
    }

    public static void clickResetButton(WebDriver driver) {
        // clicks reset button

        WebElement button = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[4]/button"));
        button.click();
    }

    public static List<WebElement> getWeighingsList(WebDriver driver) {
        // gets the list of weighings from the webpage

        WebElement ol = driver.findElement(By.cssSelector("#root > div > div.game > div.game-info > ol"));
        List<WebElement> weighings = ol.findElements(By.tagName("li"));
        return weighings;
    }

    public static String getResult(WebDriver driver) {
        // gets the result (comparision operator in between boxes)

        WebElement button = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/button"));
        return button.getText();
    }

    public static void setBowlCellValue(WebDriver driver, String bowl, int boxNumber, int barNumber) {
        // fills the 'barNumber' in corresposnding 'boxNumber' of the 'bowl' (left or right).

        WebElement cell = driver.findElement(By.id(bowl + "_" + boxNumber));
        cell.sendKeys(String.valueOf(barNumber));

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementValue(cell, String.valueOf(barNumber)));
    }

    public static void loadBowl(WebDriver driver, String bowl, List<int[]> barPositions) {
        // fill the corresponding 'bowl' with elements in 'barPositions'

        for (int[] entry : barPositions) {
            int boxNumber = entry[0];
            int barNumber = entry[1];
            setBowlCellValue(driver, bowl, boxNumber, barNumber);
        }
    }

    public static void reDistributeBowl(WebDriver driver, String sourceBowl, String targetBowl, List<int[]> sourceBowlPositions, List<int[]> targetBowlPositions) {
        // Empties the destination bowl and redistributes the contents of the source bowl evenly, ensuring that half of the elements remain in the source bowl and the other half are transferred to the target bowl.

        clickResetButton(driver);

        targetBowlPositions.clear();
        int length = sourceBowlPositions.size();
        int mid = length/2;
        int targetStartPosition = 0;
        
        for(int i = length-1; i >= mid; i--){
            int[] position = sourceBowlPositions.get(i);
            sourceBowlPositions.remove(i);
            position[0] = targetStartPosition++;
            targetBowlPositions.add(position);
        }

        loadBowl(driver, sourceBowl, sourceBowlPositions);
        loadBowl(driver, targetBowl, targetBowlPositions);
    }


    public static String clickFakeBarAndGetAlert(WebDriver driver, int barNumber) {
        // clicks the fake gold bar and gets the alert message

        WebElement bar = driver.findElement(By.id("coin_" + barNumber));
        bar.click();

        String message = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();

        return message;
    }

    public static boolean verifyAlertMessage(boolean expectedAlert, String actualAlert) {
        // verifies alert message is right/wrong based on if fake bar number clicked is correct/incorrect. 

        String ALERT_MESSAGE_RIGHT = "Yay! You find it!";
        String ALERT_MESSAGE_WRONG = "Oops! Try Again!";

        if (expectedAlert) {
            return ALERT_MESSAGE_RIGHT.equals(actualAlert);
        } else {
            return ALERT_MESSAGE_WRONG.equals(actualAlert);
        }
    }
}
