package pageLocators;

import Utils.SeleniumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Evershop_Locators {

    public Evershop_Locators(){
        PageFactory.initElements(SeleniumDriver.getDriver(), this);
    }

    @FindBy(xpath="//a[@href='/account/login']")
    public WebElement btn_Login;

    @FindBy(xpath="//a[@href='/account/register']")
    public WebElement btn_Create_Account;

    @FindBy(xpath="//input[@name='full_name']")
    public WebElement lbl_UserName;

    @FindBy(xpath="//input[@name='email']")
    public WebElement lbl_Email;

    @FindBy(xpath="//input[@name='password']")
    public WebElement lbl_Password;

    @FindBy(xpath="//button[@class='button primary']//span[text()='SIGN UP']")
    public WebElement btn_SignUP;

    @FindBy(xpath="//a[@href='/account']")
    public WebElement btn_Profile_Account;

    @FindBy(xpath="//div[@class='account-details-email flex gap-1']//div[2]")
    public WebElement txt_Email_Verification;

    @FindBy(xpath="//button[@class='button primary']//span[text()='SIGN IN']")
    public WebElement btn_SignIN;

    @FindBy(xpath="//a[@href='/category/men']")
    public WebElement btn_MenCategory;

    @FindBy(xpath="//a[@href='/category/kids']")
    public WebElement btn_KidsCategory;

    @FindBy(xpath="//a[@href='/category/women']")
    public WebElement btn_WomenCategory;

    @FindBy(xpath="//h1[@class='product-single-name']")
    public WebElement txt_Product_Validation;

    @FindBy(xpath="//input[@name='qty']")
    public WebElement lbl_Quantity;

    @FindBy(xpath="//h1[@class='category-name mt-25']")
    public WebElement txt_Category_Validation;

    @FindBy(xpath="//button[@class='button primary outline']//span[text()='ADD TO CART']")
    public WebElement btn_AddToCart;

    @FindBy(xpath="//div[text()='JUST ADDED TO YOUR CART']")
    public WebElement txt_AddToCart_Validation;

    @FindBy(xpath="//a[@class='mini-cart-icon']")
    public WebElement btn_miniCart;

    @FindBy(xpath="//a[@class='button primary']//span[text()='CHECKOUT']")
    public WebElement btn_CheckOut;


    //CHECKOUT INFORMATION
    @FindBy(xpath="//input[@name='address[full_name]']")
    public WebElement lbl_fullnameCheckout;

    @FindBy(xpath="//input[@name='address[telephone]']")
    public WebElement lbl_telephoneCheckout;

    @FindBy(xpath="//input[@name='address[address_1]']")
    public WebElement lbl_addressCheckout;

    @FindBy(xpath="//input[@name='address[city]']")
    public WebElement lbl_cityCheckout;

    @FindBy(xpath="//select[@name='address[country]']")
    public WebElement cmb_countryCheckout;

    @FindBy(xpath="//select[@name='address[province]']")
    public WebElement cmb_provinceCheckout;

    @FindBy(xpath="//input[@name='address[postcode]']")
    public WebElement lbl_postcodeCheckout;

    @FindBy(xpath="//input[@name='method']")
    public WebElement chk_freeshipCheckout;

    @FindBy(xpath="//button[@class='button primary']//span[text()='Continue to payment']")
    public WebElement btn_payment;

    @FindBy(xpath="//span[@class='checkbox-checked' or @class='checkbox-unchecked']")
    public WebElement chk_addressShipping;

    @FindBy(xpath="(//form[@id=\"checkoutPaymentForm\"]//a[@href])[3]")
    public WebElement btn_visaOption;

    @FindBy(xpath="//input[@name='cardnumber']")
    public WebElement lbl_cardNumber;

    @FindBy(xpath="//input[@name='exp-date']")
    public WebElement lbl_cardExpDate;

    @FindBy(xpath="//input[@name='cvc']")
    public WebElement lbl_cardCVC;

    @FindBy(xpath="//button[@type='button']//span[text()='Place Order']")
    public WebElement btn_placeOrder;

    @FindBy(xpath="//div[text()='Customer information']")
    public WebElement txt_PlaceOrderVerificationMsg;






}


