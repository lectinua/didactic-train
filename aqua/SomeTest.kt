package dev.lect.aqua

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SomeTest : BaseTest("http://localhost:9090") {
  private val some = SomePageTest()
  
  @Test
  @Order(1)
  fun login() {
    some.로그인()
  }
  
  @Test
  @Order(2)
  fun fmd() {
    some.title = "테스트22"
    some.신규_등록()
    some.항목_조회()
  }
  
}
