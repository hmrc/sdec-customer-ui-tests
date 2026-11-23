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

package uk.gov.hmrc.ui.pages
import org.openqa.selenium.By
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.driver.BrowserDriver

object AuthLoginPage extends BrowserDriver with BasePage {

  override val pageUrl: String = TestConfiguration.url("auth-login-stub") + "/gg-sign-in"

  private object Fields {
    val credId:             By = By.id("authorityId")
    val redirectionUrlById: By = By.id("redirectionUrl")
    val affinityGroupById:  By = By.id("affinityGroupSelect")
    val credentialRoleById: By = By.id("credential-role-select")
    val userName:           By = By.id("usersName")
    val userEmail:          By = By.id("email")
    val confidenceLevel:    By = By.id("confidenceLevel")
    val userNino:           By = By.id("nino")
    val authSubmitById:     By = By.id("submit-top")
  }

  private def navigateToAuthLoginPage(): Unit = {
    navigateTo(pageUrl)
    assertOnPage()
  }

  private def selectAffinityGroup(affinityGroup: String): Unit =
    selectDropdownById(Fields.affinityGroupById).selectByVisibleText(affinityGroup)

  private def selectCredentialRole(credentialRole: String): Unit =
    selectDropdownById(Fields.credentialRoleById).selectByVisibleText(credentialRole)

  private def addUserDetails(): Unit = {
    sendKeys(Fields.userName, "John Smith")
    sendKeys(Fields.userEmail, "user@example.com")
    sendKeys(Fields.userNino, "WM111111D")
  }

  private def submitAuthPage(): Unit = click(Fields.authSubmitById)

  private def submitAuth(affinityGroup: String, credentialRole: String, redirectUrl: String): Unit = {
    navigateToAuthLoginPage()
    sendKeys(Fields.redirectionUrlById, redirectUrl)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    addUserDetails()
    submitAuthPage()
  }

  def loginAsExternalUser(redirectUrl: String, affinityGroup: String, credentialRole: String): Unit =
    submitAuth(redirectUrl, affinityGroup, credentialRole)

}
