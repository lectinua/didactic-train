package dev.lect.aqua.login

import dev.lect.aqua.BasePage
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class LoginPage(driver: WebDriver) : BasePage(driver) {

  @FindBy(css = "input[name='username']")
  lateinit var inputUsername: WebElement

  @FindBy(css = "input[name='password']")
  lateinit var inputPassword: WebElement

  @FindBy(css = "html > body > div:nth-of-type(4) > div > div > form > div > label:nth-of-type(3)")
  lateinit var labelSaveId: WebElement

  @FindBy(css = "button[type='submit']")
  lateinit var buttonLogin: WebElement

}