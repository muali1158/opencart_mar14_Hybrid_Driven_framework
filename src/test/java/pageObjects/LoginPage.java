package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement textEmailAddress;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement textPassword;
	
	@FindBy(xpath="//input[@class='btn btn-primary']")
	WebElement btnLogin;
	
	
	public void setEmail(String email){
		textEmailAddress.sendKeys(email);
	}
	
	public void setPassword(String pwd){
		textPassword.sendKeys(pwd);
	}
	
	public void clickLogin(){
		btnLogin.click();
	}
}
