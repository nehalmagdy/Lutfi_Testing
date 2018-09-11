import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import java.sql.ResultSet as ResultSet
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import org.openqa.selenium.support.ui.Select as Select
import org.openqa.selenium.By as By

WebUI.callTestCase(findTestCase('Valid_Login'), [('username') : 'nehal@gmail.com', ('password') : 'testernehal735'], FailureHandling.STOP_ON_FAILURE)

KeywordLogger log = new KeywordLogger()

log.logInfo('Agency Bank Account Data Test Case Started')

//connect to db
CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')

//#of agencies that should appear
ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('SELECT agency_bank_account_details.id, account_number, agency.name, bank.name FROM staging.agency_bank_account_details inner join agency on agency.id = agency_bank_account_details.agency_id inner join bank on bank.id =agency_bank_account_details.bank_id where deleted_at is null')
res.next()

ResultSet cres = CustomKeywords.'com.database.lutfi.Database.executeQuery'('SELECT count(*) FROM staging.agency_bank_account_details inner join agency on agency.id = agency_bank_account_details.agency_id inner join bank on bank.id =agency_bank_account_details.bank_id where deleted_at is null')
cres.next()
int agencyBankCount = res.getInt(1)



boolean notfound = false

WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencyMenuItem'))

WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencyBankAccountMenuItem'))

WebUI.waitForPageLoad(20)

WebDriver driver = DriverFactory.getWebDriver()
WebElement Table = driver.findElement(By.xpath('//*[@id="dataTableBuilder"]'))
WebUI.delay(10)
WebElement tbody = Table.findElement(By.tagName('tbody'))
WebUI.delay(10)
List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))
int rows_count = rows_table.size()
WebUI.delay(10)
if (rows_count == agencyBankCount) {
	for (int row = 0; row < rows_count; row++) {
		List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName('td'))
		int columns_count = Columns_row.size()

		String cellID = Columns_row[1].findElement(By.tagName("input")).getAttribute("value")
		String cellAccountNumber = Columns_row.get(2).getText()
		String cellAgencyName = Columns_row.get(3).getText()
		String cellBankName = Columns_row.get(4).getText()
		
		String DBID = res.getString(1)
		String DBAccNum = res.getString(2)
		String DBAgencyName = res.getString(3)
		String DBBankName = res.getString(4)
		
		// as they appear in the same order as appears in the db
		if (!(cellID.equalsIgnoreCase(DBID) && cellAccountNumber.equalsIgnoreCase(DBAccNum) && cellAgencyName.equalsIgnoreCase(DBAgencyName) && cellBankName.equalsIgnoreCase(DBBankName))) {
			notfound = true
			break
		}
		
		res.next()
	}
	
	if (notfound) {
		// an agency name doesn't appear in the table
		log.logFailed('Some Agencies Bank Accounts didn\'t appear') // All names appeared
	} //wrong number od agencies
	else {
		log.logPassed('All Bank Accounts appeared successfully')
	}
} else {
	log.logFailed('Number of Agencies Bank Accounts Shown is not the same as the ones retrieved from the DB which is ' + agencyBankCount)
}

