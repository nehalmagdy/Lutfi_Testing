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
	
	    Columns_row[4].findElement(By.tagName('a')).click()
	
		
		///////////////////////Validate amounts
		String agencyBalance = WebUI.getText(findTestObject('AgencyControlPage/TransactionsPage/lbl_AgencyBalance')).split(":")[1].trim().split(" ")[0].trim()
		
		String getAmount = WebUI.getText(findTestObject('AgencyControlPage/TransactionsPage/lbl_getTransactions'))
		getAmount=getAmount.substring(getAmount.size()-2,getAmount.size()-1)
		
		String payAmount = WebUI.getText(findTestObject('AgencyControlPage/TransactionsPage/lbl_payTransactions'))
		
		payAmount=payAmount.substring(payAmount.size()-2,payAmount.size()-1)
	
		//query amounts from db 
		
		String getSumQuery = "select sum(amount) from transactions where to_id="+id+" and to_type='App\\Application\\Model\\Agency' and deleted_at is null"
		
		String paySumQuery = "select sum(amount) from transactions where from_id="+id+" and from_type='App\\Application\\Model\\Agency' and deleted_at is null"
		
		String balanceQuery = "select balance from agency where id="+id;
		
		
		//
		String getQuery = "select amount, created_at from transactions where to_id="+id+" and to_type='App\\Application\\Model\\Agency' and deleted_at is null"
		String cgetQuery = "select count(*) from transactions where to_id="+id+" and to_type='App\\Application\\Model\\Agency' and deleted_at is null"
		
		ResultSet getRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(getQuery)
		ResultSet cgetRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(cgetQuery)
		cgetRes.next();
		int getCount = cgetRes.getInt(1)
		
		String payQuery = "select amount, created_at from transactions where from_id="+id+" and from_type='App\\Application\\Model\\Agency' and deleted_at is null"
		String cpayQuery = "select count(*) from transactions where from_id="+id+" and from_type='App\\Application\\Model\\Agency' and deleted_at is null"
		
		ResultSet payRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(payQuery)
		ResultSet cpayRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(cpayQuery)
		cpayRes.next();
		int payCount = cpayRes.getInt(1)
		
		ResultSet bRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(balanceQuery)
		bRes.next()
		int bAmount = bRes.getInt(1)
			
		ResultSet gRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(getSumQuery)	
		gRes.next()
		int gAmount = gRes.getInt(1)
		
		ResultSet pRes = CustomKeywords.'com.database.lutfi.Database.executeQuery'(paySumQuery)
		pRes.next()
		int pAmount = pRes.getInt(1)
		
		boolean trueAmounts = false;
		if(bAmount== agencyBalance && pAmount==payAmount && gAmount==getAmount){
			trueAmounts=true;
			//pass: amounts are correct
			log.logPassed("Pass: Amounts are correct(get, pay and balance) ")
		}
		else{
			//Fail: amounts are incorrect
			log.logFailed("Fail: some Amounts are incorrect(get, pay and balance) ")
		}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// validate the tables of get
		driver = DriverFactory.getWebDriver()             
		WebElement gTable = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/table'))
		WebUI.delay(10)
		
		tbody = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/table/tbody[2]'))
		WebUI.delay(10)
		rows_table = tbody.findElements(By.tagName('tr'))	
		int g_rows_count = rows_table.size()
			
			if(g_rows_count==getCount){	
				while(gRes.next()){
					boolean Found = false;			
					for (int row = 0; row < rows_count; row++) {
						Columns_row = rows_table.get(row).findElements(By.tagName('td'))		
						String cellAmount = Columns_row[0].getText()
						String cellDate = Columns_row[1].getText()
						
						String DBAmount = gRes.getInt(1).toString()
						String DBDate = gRes.getDate(2).toString()
						
						if (cellAmount.equals(DBAmount) && cellDate.equals(DBDate)) {
							Found = true
							break
						}
					}
					if(!Found){
						//Fail: a get trasaction doesn't appear in the list
						AllFound = false;
						log.logFailed("Fail: a get trasaction doesn't appear in the list")
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
			
			if(p_rows_count==payCount){
				while(pRes.next()){
					boolean Found = false;
					for (int row = 0; row < rows_count; row++) {
						Columns_row = rows_table.get(row).findElements(By.tagName('td'))
						String cellAmount = Columns_row[0].getText()
						String cellDate = Columns_row[1].getText()
						
						String DBAmount = pRes.getInt(1).toString()
						String DBDate = pRes.getDate(2).toString()
						
						if (cellAmount.equals(DBAmount) && cellDate.equals(DBDate)) {
							Found = true
							break
						}
					}
					if(!Found){
						//Fail: a get trasaction doesn't appear in the list
						AllFound = false;
						log.logFailed("Fail: a pay trasaction doesn't appear in the list")
						break;
					}
				}
			
			}
			else{
				//Fail: different result sizes
				AllFound = false;
				log.logFailed("Fail: Pay different result sets sizes")
				
			}
		
		/////////////////////////////////////////////////////////////////////////////
	
			if(AllFound){
				//Pass: the result is correct for the search query
				log.logPassed("Pass: PAY the Shown Data is complete")
			}
			else{
				//Fail: the result is incorrect for the search query
				log.logFailed("Fail: PAY the Shown Data is complete")
					
			}
			
				
	}
	else{//there are no agencies to validate the scenario
		log.logInfo("The Agencies Table is empty: we cannot validate the Scenario")	
	}
	
	
	CustomKeywords.'com.database.lutfi.Database.closeDatabaseConnection'()
	
	
	
	
	
	
