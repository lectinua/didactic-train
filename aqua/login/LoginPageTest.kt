package dev.lect.aqua.login

import dev.lect.aqua.BaseTest
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

open class LoginPageTest : BaseTest("http://localhost:9090") {
  
  @Test
  @Order(1)
  fun login() {
    val page = navigate("/login", LoginPage::class.java)
    
    page.inputUsername.sendKeys("system")
    page.inputPassword.sendKeys("1234")
    page.labelSaveId.click()
    page.buttonLogin.click()
    
    waitUntilUrlNe("http://localhost:9090/login")
    waitAndToast(5)
  }
  
  protected fun waitAndToast(
    seconds: Long,
    message: String = "Wait for next test"
  ) {
    executeScript("Util.toast(\"$message\", ${1000 * seconds})")
    waitSeconds(seconds)
  }
  
}