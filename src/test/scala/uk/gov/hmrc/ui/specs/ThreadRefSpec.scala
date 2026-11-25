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

import org.scalatest.featurespec.AnyFeatureSpec
import uk.gov.hmrc.ui.pages.AuthWizardPage.assertOnPage
import uk.gov.hmrc.ui.pages.{AuthWizardPage, ThreadDetails_SendAndRequestFilesReceivedPage, ThreadReferencePage}
import uk.gov.hmrc.ui.specs.tags.AcceptanceTests

class ThreadRefSpec extends BaseSpec {

  Feature("External User Journey - Thread Reference") {

    Scenario("Thread Reference page display", AcceptanceTests) {
      Given("SDEC customer successfully navigates to sdec service page through 'Authority Wizard Page'")
      AuthWizardPage.loginAsSdecCustomer("John Smith", "user@example.com", "WM111111D")

      When("the thread reference page loads, system displays service name")
      ThreadReferencePage.getServiceNameText shouldBe "Share Files Securely with HMRC"

      And("verify url is correct for page")
      ThreadReferencePage.getUrl shouldBe "http://localhost:4002/sdec/thread-reference"

      Then("the system must display the thread reference input field and it is enabled")
      ThreadReferencePage.isThreadReferenceInputDisplayed shouldBe true
      ThreadReferencePage.isThreadReferenceInputEnabled   shouldBe true

      And("the system must display a continue button and it is enabled")
      ThreadReferencePage.isContinueButtonDisplayed shouldBe true
      ThreadReferencePage.isContinueButtonEnabled   shouldBe true

    }

    Scenario("Empty field validation of Thread Reference Number", AcceptanceTests) {

      Given("SDEC customer successfully navigates to sdec service page through 'Authority Wizard Page'")
      AuthWizardPage.loginAsSdecCustomer("John Smith", "user@example.com", "WM111111D")

      When("the thread reference page loads, system displays service name")
      ThreadReferencePage.getServiceNameText shouldBe "Share Files Securely with HMRC"

      And("verify url is correct for page")
      ThreadReferencePage.getUrl shouldBe "http://localhost:4002/sdec/thread-reference"

      Then("the system must display the thread reference input field and it is enabled")
      ThreadReferencePage.isThreadReferenceInputDisplayed shouldBe true
      ThreadReferencePage.isThreadReferenceInputEnabled   shouldBe true

      And("the system must display a continue button and it is enabled")
      ThreadReferencePage.isContinueButtonDisplayed shouldBe true
      ThreadReferencePage.isContinueButtonEnabled   shouldBe true

      And("the user clicks Continue without entering a value")
      ThreadReferencePage.clickContinueButton()

      Then("the system must display an error summary at the top of the page")
      ThreadReferencePage.getErrorTitleText shouldBe "There is a problem"

      And("""the system must display the error message "Enter the thread reference number"""")
      ThreadReferencePage.getFirstErrorMessage shouldBe "Enter the thread reference number"
      ThreadReferencePage.getSecondErrorMessage  should include("Enter the thread reference number")
    }

    Scenario("Invalid Format Thread Reference Number Validation", AcceptanceTests) {

      Given("SDEC customer successfully navigates to sdec service page through 'Authority Wizard Page'")
      AuthWizardPage.loginAsSdecCustomer("John Smith", "user@example.com", "WM111111D")

      When("the  thread reference page loads, system displays service name")
      ThreadReferencePage.getServiceNameText shouldBe "Share Files Securely with HMRC"

      And("verify url is correct for page")
      ThreadReferencePage.getUrl shouldBe "http://localhost:4002/sdec/thread-reference"

      Then("the system must display the thread reference input field and it is enabled")
      ThreadReferencePage.isThreadReferenceInputDisplayed shouldBe true
      ThreadReferencePage.isThreadReferenceInputEnabled   shouldBe true

      And("the system must display a continue button and it is enabled")
      ThreadReferencePage.isContinueButtonDisplayed shouldBe true
      ThreadReferencePage.isContinueButtonEnabled   shouldBe true

      And("the user provides invalid format thread reference number and clicks Continue")
      ThreadReferencePage.enterThreadReference("ABCD")

      Then("the system must display an error summary at the top of the page")
      ThreadReferencePage.getErrorTitleText shouldBe "There is a problem"

      And(
        "the system must display the error message - Enter a valid thread reference number"
      )
      ThreadReferencePage.getFirstErrorMessage shouldBe
        "Enter a valid thread reference number"
      ThreadReferencePage.getSecondErrorMessage should include
      "Enter a valid thread reference number"

    }

    Scenario("Input field rules error validation", AcceptanceTests) {

      Given("SDEC customer successfully navigates to sdec service page through 'Authority Wizard Page'")
      AuthWizardPage.loginAsSdecCustomer("John Smith", "user@example.com", "WM111111D")

      When("the  thread reference page loads, system displays service name")
      ThreadReferencePage.getServiceNameText shouldBe "Share Files Securely with HMRC"

      And("verify url is correct for page")
      ThreadReferencePage.getUrl shouldBe "http://localhost:4002/sdec/thread-reference"

      Then("the system must display the thread reference input field and it is enabled")
      ThreadReferencePage.isThreadReferenceInputDisplayed shouldBe true
      ThreadReferencePage.isThreadReferenceInputEnabled   shouldBe true

      And("the system must display a continue button and it is enabled")
      ThreadReferencePage.isContinueButtonDisplayed shouldBe true
      ThreadReferencePage.isContinueButtonEnabled   shouldBe true

      And("the user provides invalid format thread reference number and clicks Continue")
      ThreadReferencePage.enterThreadReference("ABCD$%&cv")
      ThreadReferencePage.clickContinueButton()

      Then("the system must display an error summary at the top of the page")
      ThreadReferencePage.getErrorTitleText shouldBe "There is a problem"

      And(
        "the system must display the error message - Enter a valid thread reference number"
      )
      ThreadReferencePage.getFirstErrorMessage shouldBe
        "Enter a valid thread reference number"
      ThreadReferencePage.getSecondErrorMessage should include
      "Enter a valid thread reference number"
    }

    Scenario("User logs in with correct details and provides valid thread reference number", AcceptanceTests) {

      Given("SDEC customer successfully navigates to sdec service page through 'Authority Wizard Page'")
      AuthWizardPage.loginAsSdecCustomer("John Smith", "user@example.com", "WM111111D")

      When("the  thread reference page loads, system displays service name")
      ThreadReferencePage.getServiceNameText shouldBe "Share Files Securely with HMRC"

      And("verify url is correct for page")
      ThreadReferencePage.getUrl shouldBe "http://localhost:4002/sdec/thread-reference"

      Then("the system must display the thread reference input field and it is enabled")
      ThreadReferencePage.isThreadReferenceInputDisplayed shouldBe true
      ThreadReferencePage.isThreadReferenceInputEnabled   shouldBe true

      And("the system must display a continue button and it is enabled")
      ThreadReferencePage.isContinueButtonDisplayed shouldBe true
      ThreadReferencePage.isContinueButtonEnabled   shouldBe true

      And("the user provides invalid format thread reference number and clicks Continue")
      ThreadReferencePage.enterThreadReference("KL897865432222")

      And("user navigates to 'Thread Details - Share Files Securely with HMRC-Gov UK' page")
      // ThreadReferencePage.getUrl               shouldBe "http://localhost:4002/sdec/thread-reference/KL897865432222"
      assertOnPage(
        ThreadDetails_SendAndRequestFilesReceivedPage.threadViewPageURL("KL897865432222"),
        ThreadDetails_SendAndRequestFilesReceivedPage.pageTitle
      )

    }
  }

}
