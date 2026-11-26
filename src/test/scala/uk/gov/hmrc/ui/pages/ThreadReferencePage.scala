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
import uk.gov.hmrc.ui.conf.TestConfiguration

object ThreadReferencePage extends BasePage {
  override val pageUrl: String = s"${TestConfiguration.url("sdec-customer-frontend").trim}/thread-reference"
  val pageTitle:        String = "Enter the thread reference number - Share Files Securely with HMRC - GOV.UK"

  val serviceNameLocator:              By = By.cssSelector("section div div span a")
  val threadReferenceTextFieldLocator: By = By.id("thread-reference")
  val continueButtonLocator:           By = By.xpath("//button[@type='submit']")
  val errorTitleLocator:               By = By.cssSelector("div.govuk-error-summary  div  h2")
  val firstErrorMessageLocator:        By = By.cssSelector("div.govuk-error-summary  div  div  ul  li  a")
  val secondErrorMessageLocator:       By = By.id("thread-reference-error")
  val devInProgressTextLocator:        By = By.cssSelector("div  h1")

  def getServiceNameText:              String  = getText(serviceNameLocator)
  def isThreadReferenceInputDisplayed: Boolean = isElementDisplayed(threadReferenceTextFieldLocator)
  def isThreadReferenceInputEnabled:   Boolean = isElementEnabled(threadReferenceTextFieldLocator)
  def isContinueButtonDisplayed:       Boolean = isElementDisplayed(continueButtonLocator)
  def isContinueButtonEnabled:         Boolean = isElementEnabled(continueButtonLocator)
  def clickContinueButton():           Unit    = click(continueButtonLocator)
  def getErrorTitleText:               String  = getText(errorTitleLocator)
  def getDevInProgressText:            String  = getText(devInProgressTextLocator)
  def getFirstErrorMessage:            String  = getText(firstErrorMessageLocator)
  def getSecondErrorMessage:           String  = getText(secondErrorMessageLocator)
  def getUrl:                          String  = getCurrentUrl

  def enterThreadReference(threadReferenceNumber: String): Unit = {
    sendKeys(threadReferenceTextFieldLocator, threadReferenceNumber)
    clickContinueButton()
  }
}
