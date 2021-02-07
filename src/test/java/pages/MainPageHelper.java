package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPageHelper extends PageBase {
    public MainPageHelper(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "slogan")
    WebElement slogan;

    @FindBy(css = ".mat-button-wrapper .material-icons")
    WebElement homeButton;

    @FindBy(css = "#mat-input-0")
    WebElement fromField;

    @FindBy(css = "#mat-input-1")
    WebElement toField;

    @FindBy(css = "[mattooltip=\"Remove StartPoint\"]")
    WebElement startPoint;

    @FindBy(css = "[mattooltip=\"Remove EndPoint\"]")
    WebElement endPoint;
    
    @FindBy(id = "mat-autocomplete-0")
    WebElement submitCityFromDropDown;
    
    @FindBy(id = "mat-autocomplete-1")
    WebElement submitCityToDropDown;

    @FindBy(className = "aside")
    WebElement resultForm;

    @FindBy(tagName = "iframe")
    WebElement feedbackForm;

    @FindBy(tagName = "mat-chip")
    List<WebElement> totalCostList;

    @FindBy(css = ".actions .mat-primary")
    WebElement letsGoButton;

    @FindBy(css = ".actions .mat-accent")
    WebElement clearButton;

    @FindBy(css = ".mat-form-field-flex")
    WebElement toFieldClear;

    @FindBy(css =".mat-form-field-flex")
    WebElement fromFieldClear;

    @FindBy(className = "mat-expansion-indicator")
    WebElement openDropDownListButton;

    @FindBy(css = ".mat-expansion-panel")
    WebElement dropDownListWithResult;

    @FindBy(css = "section.ng-star-inserted")
    List<WebElement> searchResultsList;


    public boolean sloganContainsText(String text) {
        waitUntilElementVisible(slogan, 50);
        return slogan.getText().contains(text);
    }

    public void inputCityInFieldFrom(String city) {
        try {
            inputTextToField(fromField, city);
            waitUntilElementVisible(submitCityFromDropDown, 10);
            submitCityFromDropDown.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnRemoveStartPointIcon() {
        startPoint.click();
    }

    public void clickOnRemoveEndPointIcon() {
        endPoint.click();
    }

    public void inputCityInFieldTo(String city) {
        try {
            inputTextToField(toField, city);
            waitUntilElementVisible(submitCityToDropDown, 10);
            submitCityToDropDown.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean resultIsDisplayed() {
        waitUntilElementVisible(resultForm, 10);
        return resultForm.isDisplayed();
    }

    public boolean feedBackFormIsDisplayed() {
        waitUntilElementVisible(feedbackForm, 30);
        return feedbackForm.isDisplayed();
    }

    public boolean isHomeButtonExist() {
        return homeButton.isDisplayed();
    }

    public void clickOnLetsGoButton(){
        letsGoButton.click();
    }

    public boolean isLetsGoButtonExist() {
        return letsGoButton.isDisplayed();
    }

    public void clickOnClearButton() {
        clearButton.click();
    }

    public boolean isClearButtonExist() {
        return clearButton.isDisplayed();
    }

    public void returnToHomePage() {
        homeButton.click();
    }

    public boolean isResultDisplayed() {
        waitUntilElementVisible(resultForm, 10);
        return resultForm.isDisplayed();
    }

    public boolean isInputCityToFieldToClear() {
        return toFieldClear.isDisplayed();
    }

    public boolean isInputCityToFieldFromClear() {
        return fromFieldClear.isDisplayed();
    }

    public boolean resultValidation() {
        waitUntilElementVisible(resultForm, 10);
        int countOfRoutes = searchResultsList.size();
        int countOfValidPrice = 0;
        System.out.println("Count of routes = " + countOfRoutes);
        for (int i = 0; i< countOfRoutes; i++){
            System.out.println("Route " + i);
            WebElement searchResult = searchResultsList.get(i);

            System.out.println("Total information: ");
//            Getting list of cities
            List<WebElement> pointsElementList = searchResult.findElements(By.className("points"));
            for (WebElement pointElement: pointsElementList){
                System.out.println(pointElement.getText());
            }

//            Getting total price
            double totalPrice = Double.parseDouble(searchResult.findElement(By.tagName("mat-chip")).getText().substring(1));
            System.out.println("Total price: " + totalPrice);
            double sumPrice = 0.0;

//            Getting total time
            String totalTime = searchResult.findElement(By.cssSelector(".description>p:nth-child(2)>span:nth-child(1)")).getText();
            System.out.println("Total time: " + totalTime);

//            Getting transport type list
            List<WebElement> transportTypeElementsList = searchResult.findElements(By.cssSelector("mat-panel-title .material-icons"));
            for (WebElement transportTypeElement : transportTypeElementsList){
                System.out.println("Transport type: "
                        + transportTypeElement.getText().replaceAll("directions_",""));
            }

//            Open list result
            WebElement openDropList = searchResult.findElement(By.className("mat-expansion-indicator"));
            openDropList.click();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            //This will scroll the web page till end.
            js.executeScript("arguments[0].scrollIntoView();", searchResult);

            System.out.println("--- details---");
            List<WebElement> detailElementsList = searchResult.findElements(By.className("details"));
            waitUntilElementVisible(driver.findElement(By.className("details")), 10);

            int countOfParts = detailElementsList.size();
            System.out.println("countOfParts: " + countOfParts);
            for (WebElement detailElement : detailElementsList){
                System.out.println("Details of route ");

//                Getting city
                String detailCity = detailElement.findElement
                        (By.cssSelector(".details>p:nth-child(1)>span:nth-child(1)")).getText();
                System.out.println(detailCity);
//                //            Getting price
                double detailPrice = Double.parseDouble(detailElement.findElement
                        (By.cssSelector(".details>p:nth-child(2)>span:nth-child(3)")).getText().replaceAll("€", ""));
                System.out.println(detailPrice);
                sumPrice += detailPrice;
                System.out.println("sum of pricies: " + sumPrice);
//                Getting time
                String detailTime = detailElement.findElement
                        (By.cssSelector(".details>p:nth-child(2)>span:nth-child(1) ")).getText();
                System.out.println(detailTime);


            }
            if (Math.abs(totalPrice-sumPrice)< totalPrice*0.1 ) countOfValidPrice ++;
            System.out.println("Count of price = " + countOfValidPrice);

            openDropList.click();


        }
        return countOfValidPrice == countOfRoutes;
    }


    public void clickOnButtonOpenDownList() {
        waitUntilElementVisible(openDropDownListButton, 10);
        openDropDownListButton.click();
    }
    public boolean dropDownListIsDisplayed() {

        waitUntilElementVisible(dropDownListWithResult,10);
        return dropDownListWithResult.isDisplayed();
    }



}
