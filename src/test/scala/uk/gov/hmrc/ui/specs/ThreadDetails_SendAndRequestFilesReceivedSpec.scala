/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.ui.specs
import org.scalactic.Prettifier.default
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import uk.gov.hmrc.selenium.webdriver.Driver.instance
import uk.gov.hmrc.ui.pages.AuthWizardPage.assertOnPage
import uk.gov.hmrc.ui.pages.{AuthWizardPage, ThreadDetails_SendAndRequestFilesReceivedPage, ThreadReferencePage}
import uk.gov.hmrc.ui.specs.tags.AcceptanceTests

import scala.language.postfixOps

class ThreadDetails_SendAndRequestFilesReceivedSpec extends BaseSpec {

  Feature("View thread communication details for 'file send and reply requested' on 'Thread Details Page") {

    val happyPathData = Table(
      ("scenario", "customerName", "customerEmailId", "customerNino", "threadReferenceNumber"),
      (
        "Success : SDEC customer can view communication details accurately on the Thread Details page",
        "John Doe",
        "user@example.com",
        "WM111111D",
        "KL897865432211"
      )
    )
    forAll(happyPathData) { (scenario, customerName, customerEmailId, customerNino, customerReferenceNumber) =>
      Scenario(scenario, AcceptanceTests) {

        Given("SDEC customer successfully navigates to sdec service page through 'Authority Wizard Page'")
        AuthWizardPage.loginAsSdecCustomer(customerName, customerEmailId, customerNino)

        And("SDEC customer enters thread reference number on 'Enter the thread reference number' page")
        assertOnPage(ThreadReferencePage.pageUrl, ThreadReferencePage.pageTitle)
        ThreadReferencePage.enterThreadReference(customerReferenceNumber)

        When("SDEC customer navigates to 'Thread Details - Share Files Securely with HMRC-Gov UK' page")
        assertOnPage(
          ThreadDetails_SendAndRequestFilesReceivedPage.threadViewPageURL(customerReferenceNumber),
          ThreadDetails_SendAndRequestFilesReceivedPage.pageTitle
        )

        Then("SDEC customer can view their correct identity details along with corresponding HMRC status")
        ThreadDetails_SendAndRequestFilesReceivedPage
          .verifyCustomerIdentityDetails(customerName, customerReferenceNumber, "HMRC is waiting for your reply.")

        And("Files requested timeline instruction is displayed correctly with timeline date")
        ThreadDetails_SendAndRequestFilesReceivedPage.fileRequestedTimelineMessage mustBe "File(s) sent and reply requested by HMRC"
        ThreadDetails_SendAndRequestFilesReceivedPage.actualFileRequestedTimelineDate mustBe ThreadDetails_SendAndRequestFilesReceivedPage.expectedFileRequestedDateTimeFourDaysAgo

        And("Files submission deadline instruction is displayed correctly with deadline date")
        ThreadDetails_SendAndRequestFilesReceivedPage.FileSubmissionDeadlineMessage mustBe "You must provide the following by " + ThreadDetails_SendAndRequestFilesReceivedPage.expectedFilesSubmissionDeadline

        And("Helper text block content is displayed correctly")
        ThreadDetails_SendAndRequestFilesReceivedPage.helperTextBoxMessage mustBe "If you cannot provide any or all of the file(s) requested, press Reply and select the option which applies to you."

        And("Bullet list of mandatory items are displayed exactly as requested by SDEC user")
        ThreadDetails_SendAndRequestFilesReceivedPage.bulletListItems mustBe Seq(
          "Payslip for June 2026",
          "Proof of address"
        )

        And("Messages are displayed exactly as entered by SDEC user")
        ThreadDetails_SendAndRequestFilesReceivedPage.message1BySDECUser mustBe "Please provide your payslip for June 2026 and a proof of address."
        ThreadDetails_SendAndRequestFilesReceivedPage.message2BySDECUser mustBe "Proof of address can include a range of documents including a recent bank or building society statement, utility bill, Council Tax bill, mortgage statement, P45 or P60, benefit entitlement letter, pension or financial statement, bank account opening letter, or a driving licence showing their current address."

        And("Guideline message displayed correctly")
        ThreadDetails_SendAndRequestFilesReceivedPage.guidelineMessage mustBe "Any document provided should clearly show your full name and current address."

        And("Attachment section displays hyperlinks for the files uploaded by SDEC user")
        ThreadDetails_SendAndRequestFilesReceivedPage.linkForAttachmentDisplayed mustBe "Proof of Address Guidance Sheet.docx"

        And("reply button is enable for SDEC customer to click")
        ThreadDetails_SendAndRequestFilesReceivedPage.replyButtonClickable()

      }
    }
  }

}
