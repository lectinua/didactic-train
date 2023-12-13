package dev.lect.aqua

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
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds))
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

  private fun urlNotToBe(url: String): ExpectedCondition<Boolean> {
    return object : ExpectedCondition<Boolean> {
      private var currentUrl: String? = ""

      override fun apply(driver: WebDriver): Boolean {
        this.currentUrl = driver.currentUrl
        return this.currentUrl != null && (this.currentUrl != url)
      }

      override fun toString(): String {
        return String.format("url not to be \"%s\". Current url: \"%s\"", url, this.currentUrl)
      }
    }
  }

}