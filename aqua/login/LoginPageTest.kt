package dev.lect.aqua.login

import dev.lect.aqua.BaseTest
import org.junit.jupiter.api.Test

class LoginPageTest : BaseTest<LoginPage>("http://localhost:9090/login", LoginPage::class.java) {

  @Test
  fun login() {
    page.inputUsername.sendKeys("system")
    page.inputPassword.sendKeys("1234")
    page.saveIdLabel.click()
    page.loginButton.click()

    page.wait4load()
  }

}