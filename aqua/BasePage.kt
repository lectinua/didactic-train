package dev.lect.aqua

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


open class BasePage(private val driver: WebDriver) {
  private var timeout = Duration.ofSeconds(10L)
  
  @FindBy(css = "#content")
  lateinit var content: WebElement
  
  fun wait4sec(seconds: Long = 1) {
    Thread.sleep(seconds * 1000)
  }
  
  fun wait4load(expectURL: String) {
    val message = "url이 예상과 다름: ${driver.currentUrl} != $expectURL"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(ExpectedConditions.urlToBe(expectURL))
  }
  
  fun wait4load() {
    val message = "url이 바뀌지 않음: ${driver.currentUrl}"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(urlNotToBe(driver.currentUrl))
  }
  
  fun wait4text(element: WebElement, text: String) {
    val message = "값이 예상과 다름: ${driver.currentUrl}"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(textEquals(element, text))
  }
  
  fun script(script: String) {
    val js: JavascriptExecutor = driver as JavascriptExecutor
    js.executeScript(script)
  }
  
  fun inputValue(element: WebElement): String {
    val js: JavascriptExecutor = driver as JavascriptExecutor
    return js.executeScript("return arguments[0].value", element) as String
  }
  
  private fun urlNotToBe(url: String): ExpectedCondition<Boolean> {
    return object : ExpectedCondition<Boolean> {
      private var currentUrl: String? = ""
      
      override fun apply(driver: WebDriver): Boolean {
        this.currentUrl = driver.currentUrl
        return this.currentUrl != null && (this.currentUrl != url)
      }
      
      override fun toString(): String {
        return "url not to be \"$url\". Current url: \"${this.currentUrl}\""
      }
    }
  }
  
  private fun textEquals(
    element: WebElement, text: String
  ): ExpectedCondition<Boolean> {
    return object : ExpectedCondition<Boolean> {
      override fun apply(driver: WebDriver): Boolean {
        try {
          val value = inputValue(element)
          return value == text
        } catch (var3: StaleElementReferenceException) {
          return false
        }
      }
      
      override fun toString(): String {
        return "text ('$text') to be the value of element $element"
      }
    }
  }
  
}