/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Select, Wait, WebDriverWait}
import org.openqa.selenium.{WebDriver, WebElement}
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.pages.AuthWizardPage.driver

import java.time.Duration
import java.util.regex.Pattern
import scala.jdk.CollectionConverters.*

trait BasePage extends Matchers with PageObject {

  val pageUrl: String
  val baseUrl: String = TestConfiguration.url("sdec-external-frontend").trim

  def assertOnPage(url: String, title: String): Unit =
    assertPage(url, title)

  private def assertPage(expectedPageUrl: String, expectedTitle: String): Unit = {
    assertUrl(expectedPageUrl)
    assertPageTitle(expectedTitle)
  }

  def assertUrl(url: String): Unit =
    fluentWait.until(ExpectedConditions.urlMatches(s"^${Pattern.quote(url)}.*"))

  def assertPageTitle(expectedTitle: String): Unit =
    fluentWait.until(ExpectedConditions.titleIs(expectedTitle))

  def selectDropdownById(id: By): Select = new Select(driver.findElement(id: By))

  val submitButtonId: By = By.id("submit-top")

  def fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
    .withTimeout(Duration.ofSeconds(5))
    .pollingEvery(Duration.ofSeconds(1))

  def continue(locator: By = submitButtonId): Unit = {
    assertLocatorPresent(locator)
    click(locator)
  }

  def navigateTo(url: String): Unit =
    Driver.instance.navigate().to(url)

  def getElement(locator: By): WebElement =
    fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator))

  def isElementDisplayed(locator: By): Boolean =
    driver.findElements(locator).asScala.nonEmpty &&
      getElement(locator).isDisplayed

  def isElementEnabled(locator: By): Boolean =
    driver.findElements(locator).asScala.nonEmpty &&
      getElement(locator).isEnabled

  def assertLocatorPresent(locator: By): Unit = {
    val elements = Driver.instance.findElements(locator).asScala
    require(
      elements.nonEmpty,
      s"Expected element with locator [$locator] to be present, but none was found"
    )
  }

  def webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15))
}
