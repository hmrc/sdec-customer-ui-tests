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

import org.openqa.selenium.{By, WebDriver}
import org.scalactic.Prettifier.default
import org.scalatest.matchers.must.Matchers.mustBe
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.driver.BrowserDriver

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.jdk.CollectionConverters.*
import scala.util.chaining.scalaUtilChainingOps

object ThreadDetails_SendAndRequestFilesReceivedPage extends BasePage with BrowserDriver {

  val pageTitle: String =
    "Thread details - Share Files Securely with HMRC - GOV.UK"

  override val pageUrl: String = s"${TestConfiguration.url("sdec-external-frontend").trim}/thread"

  def threadViewPageURL(threadReferenceNumber: String): String =
    s"$pageUrl/${threadReferenceNumber.trim}"

  private val threadReferenceNumber: By = By.cssSelector("span.govuk-caption-l")
  private val customerName:  By = By.cssSelector("#main-content .govuk-grid-column-two-thirds > h1.govuk-heading-l")
  private val statusMessage: By = By.cssSelector("#main-content .govuk-inset-text[role='status']")
  private val filesRequestedTimelineDate:     By = By.cssSelector("#main-content .hmrc-timeline__event-meta")
  private val filesRequestedTimelineMessage:  By = By.cssSelector(".hmrc-timeline__event-title")
  private val filesSubmissionDeadlineDate:    By = By.cssSelector(".sdec-requested-files .govuk-body > strong")
  private val filesSubmissionDeadlineMessage: By = By.cssSelector(".sdec-requested-files .govuk-body:first-of-type")
  private val helperTextBoxMessage:           By = By.cssSelector(".sdec-requested-files .govuk-body:nth-of-type(2)")
  private val message1BySDECUser:             By = By.cssSelector(".sdec-requested-files + p.govuk-body")
  private val message2BySDECUser:             By = By.cssSelector(".sdec-requested-files + p.govuk-body + p.govuk-body")
  private val guidelineMessage:               By =
    By.cssSelector(".sdec-requested-files + p.govuk-body + p.govuk-body + p.govuk-body")

  private val linkForAttachment: By = By.cssSelector(".govuk-link")
  private val replyButton:       By = By.cssSelector(".govuk-grid-column-two-thirds .govuk-button")

  def getThreadReferenceNumber(implicit driver: WebDriver): String = {
    val threadReferenceText: String = driver.findElement(threadReferenceNumber).getText
    threadReferenceText.replace("Thread reference number:", "").trim
  }

  def verifyCustomerIdentityDetails(
    expectedName:            String,
    expectedReferenceNumber: String,
    expectedStatus:          String
  ): Unit = {
    driver.findElement(customerName).getText.trim mustBe expectedName
    driver.findElement(threadReferenceNumber).getText.split(":").last.trim mustBe expectedReferenceNumber
    driver.findElement(statusMessage).getText.trim mustBe expectedStatus
  }

  def actualFileRequestedTimelineDate(implicit driver: WebDriver): String =
    driver.findElement(filesRequestedTimelineDate).getText.trim

  def fileRequestedTimelineMessage(implicit driver: WebDriver): String =
    driver.findElement(filesRequestedTimelineMessage).getText.trim.tap(println)

  def expectedFileRequestedDateTimeFourDaysAgo: String =
    LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' HH:mm", Locale.ENGLISH))

  def actualFilesSubmissionDeadlineDate(implicit driver: WebDriver): String =
    driver.findElement(filesSubmissionDeadlineDate).getText.trim

  def expectedFilesSubmissionDeadline: String =
    LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH))

  def FileSubmissionDeadlineMessage(implicit driver: WebDriver): String =
    driver.findElement(filesSubmissionDeadlineMessage).getText.trim.tap(println)

  def helperTextBoxMessage(implicit driver: WebDriver): String =
    driver.findElement(helperTextBoxMessage).getText.trim

  def bulletListItems: Seq[String] =
    driver
      .findElements(By.cssSelector(".sdec-requested-files ul.govuk-list li"))
      .asScala
      .map(_.getText.trim)
      .toSeq

  def message1BySDECUser(implicit driver: WebDriver): String =
    driver.findElement(message1BySDECUser).getText.trim.tap(println)

  def message2BySDECUser(implicit driver: WebDriver): String =
    driver.findElement(message2BySDECUser).getText.trim.tap(println)

  def guidelineMessage(implicit driver: WebDriver): String =
    driver.findElement(guidelineMessage).getText.trim.tap(println)

  def linkForAttachmentDisplayed(implicit driver: WebDriver): String =
    driver.findElement(linkForAttachment).getText.trim.tap(println)

  def replyButtonEnabled: Boolean = isElementEnabled(replyButton)

  def replyButtonClickable(): Unit =
    click(replyButton)
}
