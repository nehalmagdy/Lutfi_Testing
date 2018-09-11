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

import org.openqa.selenium.support.ui.Select as Select
import org.junit.After
import org.openqa.selenium.By as By
import java.sql.ResultSet as ResultSet
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger

CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')

WebUI.callTestCase(findTestCase('Valid_Login'), [('username') : 'nehal@gmail.com', ('password') : 'testernehal735'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencyMenuItem'))

WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencySubItem'))

WebUI.waitForPageLoad(20)

KeywordLogger log = new KeywordLogger()

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//*[@id=\'dataTableBuilder\']'))

WebUI.delay(10)

WebElement tbody = Table.findElement(By.tagName('tbody'))

WebUI.delay(10)

List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))

int rows_count = rows_table.size()

boolean AllFound = true;

if (rows_count > 0) {
	List<WebElement> Columns_row = rows_table.get(rows_count - 1).findElements(By.tagName('td'))

	String id = Columns_row[0].findElement(By.tagName('input')).getAttribute('value')

	//click on the payments
	Columns_row[5].findElement(By.tagName('a')).click()
	
	
	//get the paid rows
	driver = DriverFactory.getWebDriver()
	tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div/div[2]/div/div/div[2]/table/tbody[2]'))
	WebUI.delay(10)
	rows_table = tbody.findElements(By.tagName('tr'))
	int p_rows_count = rows_table.size()

	if(p_rows_count>0){
		//test on the first row
			Columns_row = rows_table.get(0).findElements(By.tagName('td'))
			String pcellAmount = Columns_row[0].getText()
			String pcellDate = Columns_row[1].getText()
			
			Columns_row[2].findElement(By.tagName('a')).click()
			
			// check if the row is transfered to the not paid table
			// get the not paid table rows
			driver = DriverFactory.getWebDriver()
			tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div/div[1]/div/div/div[2]/table/tbody[2]'))
			WebUI.delay(10)
			rows_table = tbody.findElements(By.tagName('tr'))
			int np_rows_count = rows_table.size()
			boolean found = false;
			for (int row = 0; row < np_rows_count; row++) {
				Columns_row = rows_table.get(row).findElements(By.tagName('td'))
				String cellAmount = Columns_row[0].getText()
				String cellDate = Columns_row[1].getText()
				String cellStatus = Columns_row[2].getText()
		
				if(cellAmount.equals(pcellAmount) && pcellDate.equals(cellDate) && cellStatus == "Not Paid"){
					found=true;
					break;
				}
			}
			if(!found){
				// it was not transfered
				log.logFailed("Failed: The Entry wasnot moved to the not paid table")
			}
			else{
				log.logPassed("Passed: The Entry is moved successfully to the not paid table")
			}

	}
}