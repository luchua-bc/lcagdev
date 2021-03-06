package uk.co.novinet.web

import geb.spock.GebSpec
import org.openqa.selenium.Keys

import static uk.co.novinet.e2e.TestUtils.dashboardHost
import static uk.co.novinet.e2e.TestUtils.dashboardPort
import static uk.co.novinet.e2e.TestUtils.insertUser
import static uk.co.novinet.e2e.TestUtils.setupDatabaseSchema
import static uk.co.novinet.web.GebTestUtils.switchToMemberTabIfNecessaryAndAssertGridHasNRows

class AdditionalGroupAssignmentIT extends GebSpec {

    static def GUEST_EMAIL_ADDRESS = "newguest@test.com"

    def setup() {
        setupDatabaseSchema()
    }

    def "can assign single additional group"() {
        given:
            insertUser(1, "newguest", GUEST_EMAIL_ADDRESS, "John Smith", 8, true)
            go("http://admin:lcag@${dashboardHost()}:${dashboardPort()}")
            at DashboardPage


        when:
            switchToMemberTabIfNecessaryAndAssertGridHasNRows(browser, 2)
            memberGridAdditionalGroupsSelectTds.find("select")[1].value("LCAG FFC Contributor")
            memberGridActionButtonTds.find("button").last().click()
            waitFor { toastSuccess.text() == "Updated successfully" }

        then:
        try {
            memberGridAdditionalGroupsSelectTds.find("select")[1].value() == ["LCAG FFC Contributor"]
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            memberGridAdditionalGroupsSelectTds.find("select")[1].value() == ["LCAG FFC Contributor"]
        }

    }


    def "can assign 2 additional group"() {
        given:
            insertUser(1, "newguest", GUEST_EMAIL_ADDRESS, "John Smith", 8, true)
            go("http://admin:lcag@${dashboardHost()}:${dashboardPort()}")
            at DashboardPage


        when:
            switchToMemberTabIfNecessaryAndAssertGridHasNRows(browser, 2)
            interact {
                keyDown(Keys.SHIFT)
                memberGridAdditionalGroupsSelectTds.find("select")[1].find("option").find { it.value() == "LCAG FFC Contributor" }.click()
                memberGridAdditionalGroupsSelectTds.find("select")[1].find("option").find { it.value() == "Moderators" }.click()
                keyUp(Keys.SHIFT)
            }
            memberGridActionButtonTds.find("button").last().click()
            waitFor { toastSuccess.text() == "Updated successfully" }

        then:
        try {
            memberGridAdditionalGroupsSelectTds.find("select")[1].value().containsAll(["LCAG FFC Contributor", "Moderators"])
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            memberGridAdditionalGroupsSelectTds.find("select")[1].value().containsAll(["LCAG FFC Contributor", "Moderators"])
        }
    }

}
