package dev.lect.aqua.login

import dev.lect.aqua.BaseTest
import org.junit.jupiter.api.Test

open class LoginPageTest : BaseTest("http://localhost:9090") {
  
  @Test
  fun login() {
    val page = navigate("/login", LoginPage::class.java)
    
    page.inputUsername.sendKeys("system")
    page.inputPassword.sendKeys("1234")
    page.labelSaveId.click()
    page.buttonLogin.click()
    
    waitUntilUrlNe("http://localhost:9090/login")
    executeScript("Util.toast(\"Wait for exit\", 5000)")
    waitSeconds(5)
  }
  
}