package codingChallenge;

import codingChallenge.PageActions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import java.util.*;

public class GoldBarGameTest 
{
    static List<int[]> leftBowlPositions = new ArrayList<>();
    static List<int[]> rightBowlPositions = new ArrayList<>();
    static int fakeBar = -1;
    
    public static void main(String[] args)
    {
        WebDriver driver = PageActions.openPage();
        System.out.println("------------------------");
        System.out.println("Game Page Opened");
        System.out.println("Test Started");

        leftBowlPositions.add(new int[]{0,0});
        leftBowlPositions.add(new int[]{1,1});
        leftBowlPositions.add(new int[]{2,2});
        leftBowlPositions.add(new int[]{3,3});

        // fill the left bowl with 4 bars
        PageActions.loadBowl(driver, PageActions.left, leftBowlPositions);

        List<int[]> rightBowlPositions = new ArrayList<>();
        rightBowlPositions.add(new int[]{0,4});
        rightBowlPositions.add(new int[]{1,5});
        rightBowlPositions.add(new int[]{2,6});
        rightBowlPositions.add(new int[]{3,7});

        // fill the right bowl with remaining 4 bars
        PageActions.loadBowl(driver, PageActions.right, rightBowlPositions);

        // execute the loop until fakebar is found
        while(leftBowlPositions.size() > 1 || rightBowlPositions.size() > 1){
            PageActions.clickWeighButton(driver);
            if(PageActions.getResult(driver).equals("<")) {
                PageActions.reDistributeBowl(driver, PageActions.left, PageActions.right, leftBowlPositions, rightBowlPositions); // fake bar is in left bowl
            }
            else if(PageActions.getResult(driver).equals(">")){
                PageActions.reDistributeBowl(driver, PageActions.right, PageActions.left, rightBowlPositions, leftBowlPositions); // fake bar is in right bowl
            }
            else{
                fakeBar = 8; // last bar is the fake bar
                break;
            }
        }

        if(fakeBar != 8){
            PageActions.clickWeighButton(driver);
            if(PageActions.getResult(driver).equals("<")) {
                fakeBar = leftBowlPositions.get(0)[1];  // left bowl has the fake bar
            }
            else {
                fakeBar = rightBowlPositions.get(0)[1]; // right bowl has the fake bar
            }
        }

        // test the result
        String alertMessage = PageActions.clickFakeBarAndGetAlert(driver, fakeBar);
        Assert.assertTrue(PageActions.verifyAlertMessage(true, alertMessage));

        // output the result
        System.out.println("Test Completed");
        System.out.println("------------------------");
        System.out.println(String.format("Alert message is %s and expected is %s", alertMessage, PageActions.ALLERT_MESSAGE_RIGHT));
        System.out.println("Weighings list:");
        List<WebElement> weighings = PageActions.getWeighingsList(driver);
        for (int i = 0; i < weighings.size(); i++) {
            WebElement element = weighings.get(i);
            String text = element.getText();
            System.out.println("Weighing " + (i + 1) + ": " + text);
        }
        System.out.println("Number of weighings: " +weighings.size());
        System.out.println("Fakebar Number: " +fakeBar);
        System.out.println("------------------------");
        System.out.println("------------------------");
        driver.quit();
    }
}
