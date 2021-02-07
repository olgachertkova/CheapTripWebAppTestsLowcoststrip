package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPageHelper;

public class MainPageValidation extends TestBase {
    MainPageHelper mainPageHelper;
    String cityFrom = "Tel-Aviv";
    String cityTo = "Sochi";
    String sloganText = "CHEAP TRIP. PAY LESS, VISIT MORE!";

    @BeforeMethod
    public void initTests(){
        mainPageHelper = PageFactory.initElements(driver, MainPageHelper.class);
    }

    @Test(priority = 1, groups = "UI")
    public void sloganContainsEnglishTextTest() {
        Assert.assertTrue(mainPageHelper.sloganContainsText(sloganText));
    }

    @Test (priority = 2, groups = "UI")
    public void homeButtonIsDisplayedTest(){
        Assert.assertTrue(mainPageHelper.isHomeButtonExist(), "The Home button is not displayed!");
    }

    @Test(priority = 2, groups = "UI")
    public void letsGoButtonIsDisplayedTest(){
        Assert.assertTrue(mainPageHelper.isLetsGoButtonExist(), "The LetsGo button is not displayed!");
    }

    @Test(priority = 2, groups = "UI")
    public void clearButtonIsDisplayedTest(){
        Assert.assertTrue(mainPageHelper.isClearButtonExist(), "The Clear button is not displayed!");
    }

    @Test(priority = 1, groups = "functional")
    public void clearSearchResultByClickingOnClearButtonTest(){
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.inputCityInFieldTo(cityTo);
        mainPageHelper.clickOnClearButton();
        Assert.assertTrue(mainPageHelper.isInputCityToFieldToClear() && mainPageHelper.isInputCityToFieldFromClear(),
                "The search result is not deleted by the Clear button!");
    }

    @Test(priority = 1,groups = "functional")
    public void clearCityByClickingOnStartPointIconTest(){
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.clickOnRemoveStartPointIcon();
        Assert.assertTrue(mainPageHelper.isInputCityToFieldToClear(),
                "City in the field City From is not deleted by clicking on Remove Start Point button!");
    }

    @Test(priority = 1, groups = "functional")
    public void clearCityByClickingOnEndPointIconTest(){
        mainPageHelper.inputCityInFieldTo(cityTo);
        mainPageHelper.clickOnRemoveEndPointIcon();
        Assert.assertTrue(mainPageHelper.isInputCityToFieldFromClear(),
                "City in the field City To is not deleted by clicking on Remove End Point button!");
    }


    @Test (priority = 1, groups = {"functional", "UI"})
    public void searchResultIsDisplayedTest() {
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.inputCityInFieldTo(cityTo);    
        mainPageHelper.clickOnLetsGoButton();
        Assert.assertTrue(mainPageHelper.isResultDisplayed(),"Search result is not displayed!");
    }

    @Test (priority = 1, groups = {"functional", "feedbackForm"})
    public void feedBackFormIsDisplayedTest() {
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.inputCityInFieldTo(cityTo);
        mainPageHelper.clickOnLetsGoButton();
        Assert.assertTrue(mainPageHelper.feedBackFormIsDisplayed(),"Feedback form is not displayed!");
    }

    @Test(priority = 5,groups = {"functional","searchResults"})
    public void dropDownListWithSelectedRouteIsOpenedTest()  {
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.inputCityInFieldTo(cityTo);
        mainPageHelper.clickOnLetsGoButton();
        mainPageHelper.clickOnButtonOpenDownList();
        Assert.assertTrue( mainPageHelper.dropDownListIsDisplayed());
    }
}
