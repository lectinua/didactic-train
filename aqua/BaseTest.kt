package dev.lect.aqua

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.PageFactory
import java.time.Duration

open class BaseTest<Page : BasePage>(private val url: String, private val clazz: Class<Page>) {
  protected lateinit var driver: WebDriver
  protected lateinit var page: Page

  @BeforeEach
  fun setUp() {
    val options = ChromeOptions() // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*")
    driver = ChromeDriver(options)
    driver.manage().window().maximize()
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
    driver.get(url)

    page = PageFactory.initElements(driver, clazz)
  }

  @AfterEach
  fun tearDown() {
    driver.quit()
  }

  fun navigate(url: String) {
    driver.navigate().to(url)
    PageFactory.initElements(driver, this)
  }

}