package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPageHelper;


public class SearchValidation extends TestBase {
    MainPageHelper mainPageHelper;

    @BeforeMethod
    public void initTests(){
        mainPageHelper = PageFactory.initElements(driver, MainPageHelper.class);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "usingCSVFile", groups = "functional")
    public void searchResultIsDisplayedTest(String cityFrom, String cityTo){
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.inputCityInFieldTo(cityTo);
        mainPageHelper.clickOnLetsGoButton();
        Assert.assertTrue(mainPageHelper.resultIsDisplayed(), "Search result for cities:" + cityFrom + ", " +cityTo + " is not displayed!");
    }

    @Test(priority = 2, dataProviderClass = DataProviders.class, dataProvider = "usingCSVFile", groups = "functional")
    public void checkTheTotalCostOfRouteTestWithDataProvider(String cityFrom, String cityTo){
        mainPageHelper.inputCityInFieldFrom(cityFrom);
        mainPageHelper.inputCityInFieldTo(cityTo);
        mainPageHelper.clickOnLetsGoButton();
        Assert.assertTrue(mainPageHelper.resultValidation(), "Search result for cities: " + cityFrom + ", " +cityTo + " is not valid!");
    }


}
