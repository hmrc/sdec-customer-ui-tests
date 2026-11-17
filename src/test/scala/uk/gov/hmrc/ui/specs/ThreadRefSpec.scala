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
import org.scalatest.prop.TableDrivenPropertyChecks
import uk.gov.hmrc.ui.pages.{AuthLoginPage, ThreadReferencePage}
import uk.gov.hmrc.ui.specs.tags.AcceptanceTests
import uk.gov.hmrc.ui.testData.AuthTestData

class ThreadRefSpec extends BaseSpec with TableDrivenPropertyChecks {

  Feature("External User Journey - Thread Reference") {

    Scenario("Thread Reference page display", AcceptanceTests) {
      forAll(AuthTestData.userLoginDetails) { (userName, emailAddress, NINO) =>
        Given("User logs in")
        AuthLoginPage.login()
        AuthLoginPage.enterUserName(userName)
        AuthLoginPage.enterEmailAddress(emailAddress)
        AuthLoginPage.enterNino(NINO)
        AuthLoginPage.continue()

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
    }

    Scenario("Empty field validation of Thread Reference Number", AcceptanceTests) {
      forAll(AuthTestData.userLoginDetails) { (userName, emailAddress, NINO) =>
        Given("User logs in")
        AuthLoginPage.login()
        AuthLoginPage.selectConfidenceLevel("200")
        AuthLoginPage.enterUserName(userName)
        AuthLoginPage.enterEmailAddress(emailAddress)
        AuthLoginPage.enterNino(NINO)
        AuthLoginPage.continue()

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
    }

    Scenario("Invalid Format Thread Reference Number Validation", AcceptanceTests) {

      forAll(AuthTestData.userLoginDetails) { (userName, emailAddress, NINO) =>
        Given("User logs in")
        AuthLoginPage.login()
        AuthLoginPage.selectConfidenceLevel("200")
        AuthLoginPage.enterUserName(userName)
        AuthLoginPage.enterEmailAddress(emailAddress)
        AuthLoginPage.enterNino(NINO)
        AuthLoginPage.continue()

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
    }

    Scenario("Input field rules error validation", AcceptanceTests) {
      forAll(AuthTestData.userLoginDetails) { (userName, emailAddress, NINO) =>
        Given("User logs in")
        AuthLoginPage.login()
        AuthLoginPage.selectConfidenceLevel("200")
        AuthLoginPage.enterUserName(userName)
        AuthLoginPage.enterEmailAddress(emailAddress)
        AuthLoginPage.enterNino(NINO)
        AuthLoginPage.continue()

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
    }

    Scenario("User logs in with correct details and provides valid thread reference number", AcceptanceTests) {
      forAll(AuthTestData.userLoginDetails) { (userName, emailAddress, NINO) =>
        Given("User logs in")
        AuthLoginPage.login()
        AuthLoginPage.selectConfidenceLevel("200")
        AuthLoginPage.enterUserName(userName)
        AuthLoginPage.enterEmailAddress(emailAddress)
        AuthLoginPage.enterNino(NINO)
        AuthLoginPage.continue()

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
        ThreadReferencePage.clickContinueButton()

        And("user navigates to next page which says Dev In Progress")
        ThreadReferencePage.getUrl               shouldBe "http://localhost:4002/sdec/development-in-progress"
        ThreadReferencePage.getDevInProgressText shouldBe "Development in progress"

        // User is supposed to navigate to next page. Development in progress
      }
    }
  }
}
