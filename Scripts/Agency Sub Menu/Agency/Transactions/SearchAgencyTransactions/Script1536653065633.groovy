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
	
	
	//connect to db
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
		Columns_row[4].findElement(By.tagName('a')).click()
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		WebUI.setText(findTestObject("AgencyControlPage/TransactionsPage/txt_searchDate"), searchDate)
		WebUI.click(findTestObject("AgencyControlPage/TransactionsPage/btn_search"))
			
		// validate the tables of get
		driver = DriverFactory.getWebDriver()
		WebElement gTable = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/table'))
		WebUI.delay(10)
		
		tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/table/tbody[2]'))
		WebUI.delay(10)
		rows_table = tbody.findElements(By.tagName('tr'))
		int g_rows_count = rows_table.size()
		
		boolean incorrect = false;	
		for (int row = 0; row < rows_count; row++) {
			Columns_row = rows_table.get(row).findElements(By.tagName('td'))
			String cellDate = Columns_row[1].getText()
			
			if (! cellDate.equalsIgnoreCase(searchDate)) {
				incorrect = true
				break
			}
		}
		if(incorrect){
			//Fail: a get trasaction doesn't appear in the list
			AllFound = false;
			log.logFailed("Fail: a get trasaction has different date")
			break;
		}
	
	
		////////////////////////////////////////////////////////////////////////////////////////
			
		if(AllFound){
			//Pass: the result is correct for the search query
			log.logPassed("Pass: Get All Data has correct date")
		}
		else{
			//Fail: the result is incorrect for the search query
			log.logFailed("Fail: Get The Shown Data is not compatiable with the search date")
				
		}
				
		/////////////////////////////////////////////////////////////////////////////PAY
		
			AllFound = true;
			
		// validate the tables of pay
		driver = DriverFactory.getWebDriver()
		WebElement pTable = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/table'))
		WebUI.delay(10)
		
		tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div[2]/div[2]/div/div/div[2]/table/tbody[2]'))
		
		WebUI.delay(10)
		rows_table = tbody.findElements(By.tagName('tr'))
		int p_rows_count = rows_table.size()
		
		incorrect = false;
		for (int row = 0; row < rows_count; row++) {
			Columns_row = rows_table.get(row).findElements(By.tagName('td'))
			
			String cellDate = Columns_row[1].getText()
			
			
			if (! cellDate.equalsIgnoreCase(searchDate)) {
				incorrect = true
				break
			}
		}
		if(incorrect){
			//Fail: a get trasaction doesn't appear in the list
			AllFound = false;
			log.logFailed("Fail: a pay trasaction has different date")
			break;
		}

		/////////////////////////////////////////////////////////////////////////////
	
		if(AllFound){
			//Pass: the result is correct for the search query
			log.logPassed("Pass: PAY the Shown Data is Correct")
		}
		else{
			//Fail: the result is incorrect for the search query
			log.logFailed("Fail: Pay The Shown Data is not compatiable with the search date")
				
		}
				
	}
	else{//there are no agencies to validate the scenario
		log.logInfo("The Agencies Table is empty: we cannot validate the Scenario")
	}
	
	
	CustomKeywords.'com.database.lutfi.Database.closeDatabaseConnection'()
	
	
	
	
	
	
