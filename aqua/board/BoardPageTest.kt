package dev.lect.aqua.board

import dev.lect.aqua.login.LoginPageTest
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class BoardPageTest : LoginPageTest() {

  @FindBy(css = "#new-btn")
  lateinit var buttonNew: WebElement

  @Test
  fun test() {
    login()

    navigate("http://localhost:9090/board")

    // ...
  }

}