package pageLocators;

import Utils.SeleniumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Demo_Locators {

    public Demo_Locators(){
        PageFactory.initElements(SeleniumDriver.getDriver(), this);
    }


    @FindBy(xpath="//button[@id='start']")
    public WebElement Btn_Start;


}


