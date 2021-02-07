package pages;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PageBase {


  protected WebDriver driver;

  public PageBase(WebDriver driver) {
    this.driver = driver;
  }

  public static String takeScreenShot(WebDriver webdriver) throws IOException {
    //Convert web driver object to TakeScreenshot
    TakesScreenshot scrShot =((TakesScreenshot)webdriver);

    //Call getScreenshotAs method to create image file
    File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

    //Create new file name
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());
    String curDate = formatter.format(date);
    String filePath = "screenshots/screenshot_" + curDate +".png";

    //Move image file to new destination
    File DestFile=new File(filePath);

    //Copy file at destination
    FileUtils.copyFile(SrcFile, DestFile);
    return filePath;

  }


  void waitUntilElementVisible (WebElement element, int time){
    try {
      new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(element));
    } catch (Exception e){
      e.printStackTrace();
    }
  }



  public void inputTextToField(WebElement element, String text) {
    try {
      element.click();
      element.clear();
      element.sendKeys(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
