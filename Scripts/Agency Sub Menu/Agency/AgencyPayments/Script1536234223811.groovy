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
import org.openqa.selenium.By as By
import java.sql.ResultSet as ResultSet
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger


//connect to db
CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')

WebUI.callTestCase(findTestCase('Valid_Login'), [('username') : 'nehal@gmail.com', ('password') : 'testernehal735'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('AgencyControlPage/AgencyMenuItem'))

WebUI.click(findTestObject('AgencyControlPage/AgencySubItem'))

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

	Columns_row[5].findElement(By.tagName('a')).click()
	
	String payQuery = "select amount, created_at, status from pay_to_agency where agency_id="+id+" and status=1"// and deleted_at is null"
	String cPayQuery = "select count(*) from pay_to_agency where agency_id="+id+" and status=1"// and deleted_at is null"
	String npayQuery = "select amount, created_at, status from pay_to_agency where agency_id="+id+" and status=0"// and deleted_at is null"
	String cnpayQuery = "select count(*) from pay_to_agency where agency_id="+id+" and status=0"// and deleted_at is null"
	
	ResultSet payRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(payQuery)
	ResultSet npayRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(npayQuery)
	
	ResultSet cpayRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(cPayQuery)
	cpayRes.next()
	int cPay = cpayRes.getInt(1)
	ResultSet cnpayRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(cnpayQuery)
	cnpayRes.next()
	int cnPay = cnpayRes.getInt(1)
	
	
/////////////////////////////////////////////////// Paid
	driver = DriverFactory.getWebDriver()
	tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div/div[1]/div/div/div[2]/table/tbody[2]'))
	WebUI.delay(10)
	rows_table = tbody.findElements(By.tagName('tr'))
	int p_rows_count = rows_table.size()

	if(p_rows_count==cPay){
		while(payRes.next()){
			boolean Found = false;
			for (int row = 0; row < rows_count; row++) {
				Columns_row = rows_table.get(row).findElements(By.tagName('td'))
				String cellAmount = Columns_row[0].getText()
				String cellDate = Columns_row[1].getText()
				String cellStatus = Columns_row[2].getText()
				
				if(cellStatus=="Not Paid")
					cellStatus = "0"
				else if(cellStatus=="Paid")
					cellStatus = "1"
					
				String DBAmount = payRes.getInt(1).toString()
				String DBDate = payRes.getDate(2).toString()
				String DBStatus = payRes.getInt(3).toString()
				
				if (cellAmount.equals(DBAmount) && cellDate.equals(DBDate) && cellStatus.equals(DBStatus)) {
					Found = true
					break
				}
			}
			if(!Found){
				//Fail: a get trasaction doesn't appear in the list
				AllFound = false;
				log.logFailed("Fail: a Pay Payments doesn't appear in the list")
				break;
			}
		}
	
	}
	else{
		//Fail: different result sizes
		AllFound = false;
		log.logFailed("Fail: Get different result sets sizes")
		
	}

////////////////////////////////////////////////////////////////////////////////////////
	
	if(AllFound){
		//Pass: the result is correct for the search query
		log.logPassed("Pass: PAY the Shown Data is complete")
	}
	else{
		//Fail: the result is incorrect for the search query
		log.logFailed("Fail: PAY the Shown Data is complete")
			
	}
	
	
	
	
///////////////////////////////////////////////////Not Paid	
	AllFound = true;
	driver = DriverFactory.getWebDriver()
	tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div/div[2]/div/div/div[2]/table/tbody[2]'))
	WebUI.delay(10)
	rows_table = tbody.findElements(By.tagName('tr'))
	int np_rows_count = rows_table.size()
	if(np_rows_count==cnPay){
		while(npayRes.next()){
			boolean Found = false;
			for (int row = 0; row < rows_count; row++) {
				Columns_row = rows_table.get(row).findElements(By.tagName('td'))
				String cellAmount = Columns_row[0].getText()
				String cellDate = Columns_row[1].getText()
				String cellStatus = Columns_row[2].getText()
				
				if(cellStatus=="Not Paid")
					cellStatus = "0"
				else if(cellStatus=="Paid")
					cellStatus = "1"
				
				String DBAmount = npayRes.getInt(1).toString()
				String DBDate = npayRes.getDate(2).toString()
				String DBStatus = npayRes.getInt(3).toString()
				
				if (cellAmount.equals(DBAmount) && cellDate.equals(DBDate) && cellStatus.equals(DBStatus)) {
					Found = true
					break
				}
			}
			if(!Found){
				//Fail: a get trasaction doesn't appear in the list
				AllFound = false;
				log.logFailed("Fail: a Not Pay Payments doesn't appear in the list")
				break;
			}
		}
	
	}
	else{
		//Fail: different result sizes
		AllFound = false;
		log.logFailed("Fail: Get different result sets sizes")
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	if(AllFound){
		//Pass: the result is correct for the search query
		log.logPassed("Pass: Not PAY the Shown Data is complete")
	}
	else{
		//Fail: the result is incorrect for the search query
		log.logFailed("Fail: Not PAY the Shown Data is complete")
			
	}
	
	
}
else{//there are no agencies to validate the scenario
	log.logInfo("The Agencies Table is empty: we cannot validate the Scenario")
}


CustomKeywords.'com.database.lutfi.Database.closeDatabaseConnection'()



