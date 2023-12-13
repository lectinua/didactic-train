package dev.lect.aqua

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class BaseTest(private val baseURL: String) {
  protected var timeout = Duration.ofSeconds(10L)
  
  companion object {
    lateinit var driver: WebDriver
    
    @BeforeAll
    @JvmStatic
    fun setUp() {
      val options = ChromeOptions()
      options.addArguments("--remote-allow-origins=*")
      driver = ChromeDriver(options)
      driver.manage().window().maximize()
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
    }
    
    @AfterAll
    @JvmStatic
    fun tearDown() {
      driver.quit()
    }
  }
  
  fun navigate(uri: String) {
    driver.navigate().to("$baseURL$uri")
  }
  
  fun <Page : BasePage> navigate(uri: String, clazz: Class<Page>): Page {
    navigate(uri)
    return PageFactory.initElements(driver, clazz)
  }
  
  fun waitSecond() {
    waitSeconds(1)
  }
  
  fun waitSeconds(seconds: Long) {
    Thread.sleep(seconds * 1000)
  }
  
  fun waitUntilUriEq(expectURI: String) {
    val message = "uri가 예상과 다름: ${driver.currentUrl} != $baseURL$expectURI"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(ExpectedConditions.urlToBe(expectURI))
  }
  
  fun waitUntilUrlNe(sourceURI: String) {
    val message = "url이 바뀌지 않음: ${sourceURI}"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(urlNotToBe("$baseURL$sourceURI"))
  }
  
  fun waitUntilValueEq(element: WebElement, text: String) {
    val message = "값이 예상과 다름: ${driver.currentUrl}"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(textEquals(element, text))
  }
  
  fun waitUntilFind(by: By) {
    val message = "요소를 찾지 못함: $by"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(ExpectedConditions.presenceOfElementLocated(by))
  }
  
  fun waitUntilCustom(condition: ExpectedCondition<Boolean>) {
    val message = "값이 예상과 다름: ${driver.currentUrl}"
    val wait = WebDriverWait(driver, timeout).withMessage(message)
    wait.until(condition)
  }
  
  fun executeScript(script: String) {
    val js: JavascriptExecutor = driver as JavascriptExecutor
    js.executeScript(script)
  }
  
  fun setInputValue(element: WebElement, vararg input: CharSequence) {
    element.click()
    element.sendKeys(*input)
  }
  
  fun getInputValue(element: WebElement): String {
    val js: JavascriptExecutor = driver as JavascriptExecutor
    return js.executeScript("return arguments[0].value", element) as String
  }
  
  fun customCondition(applier: (WebDriver) -> Boolean): ExpectedCondition<Boolean> {
    return ExpectedCondition<Boolean> { driver -> applier(driver) }
  }
  
  private fun urlNotToBe(url: String): ExpectedCondition<Boolean> {
    return object : ExpectedCondition<Boolean> {
      private var currentUrl: String? = ""
      
      override fun apply(driver: WebDriver): Boolean {
        this.currentUrl = driver.currentUrl
        return this.currentUrl != null && (this.currentUrl != url)
      }
    }
  }
  
  private fun textEquals(
    element: WebElement, text: String
  ): ExpectedCondition<Boolean> {
    return object : ExpectedCondition<Boolean> {
      override fun apply(driver: WebDriver): Boolean {
        try {
          val value = getInputValue(element)
          return value == text
        } catch (var3: StaleElementReferenceException) {
          return false
        }
      }
    }
  }
  
}