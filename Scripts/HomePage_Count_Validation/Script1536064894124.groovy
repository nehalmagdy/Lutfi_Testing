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
import com.kms.katalon.core.logging.KeywordLogger



boolean Failed = false;

KeywordLogger log = new KeywordLogger()

WebUI.callTestCase(findTestCase('Valid_Login'), [('username') : 'nehal@gmail.com', ('password') : 'testernehal735'], FailureHandling.STOP_ON_FAILURE)

log.logInfo("Test case started")

//connect to db
CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')

/*
//available driver
ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('')
res.next()
int ADriverCountDB = res.getInt(1)
String ADriverCount = WebUI.getText(findTestObject('HomePage/lbl_ADriverCount'))
if(ADriverCountDB != Integer.parseInt(ADriverCount)){
	//error
}
*/


//cancelled driver
ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select count(*) from trip where status=7')
res.next()
int CDriverCountDB = res.getInt(1)
String CDriverCount = WebUI.getText(findTestObject('HomePage/lbl_CDriverCount'))
if(CDriverCountDB != Integer.parseInt(CDriverCount)){
	//error
	log.logInfo("Cancelled drivers count is wrong should be "+CDriverCount)
	Failed = true;
}

//no driver found
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select count(*) from trip where status=8')
res.next()
int NoDriverCountDB = res.getInt(1)
String NoDriverCount = WebUI.getText(findTestObject('HomePage/lbl_CTripsCount'))
if(NoDriverCountDB != Integer.parseInt(NoDriverCount)){
	//error
	log.logError("No drivers found count is wrong should be "+NoDriverCountDB)
	Failed = true;
}

//passenger cancelled
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select count(*) from trip where status=6')
res.next()
int PassengerCancelledCountDB = res.getInt(1)
String PassengerCancelledCount = WebUI.getText(findTestObject('HomePage/lbl_PassengerCount'))
if(PassengerCancelledCountDB != Integer.parseInt(PassengerCancelledCount)){
	//error
	log.logError("Cancelled passengers count is wrong should be "+PassengerCancelledCountDB)
	Failed = true
}

//paid trips
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select count(*) from trip where status=9')
res.next()
int PaidTripsCountDB = res.getInt(1)
String PaidTripsCount = WebUI.getText(findTestObject('HomePage/lbl_PTripsCount'))
if(PaidTripsCountDB != Integer.parseInt(PaidTripsCount)){
	//error
	log.logError("Paid Trips count is wrong should be "+PaidTripsCountDB)
	Failed = true;
}

/*
//Need to pay to drivers
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('')
res.next()
int NTPDriverDB = res.getInt(1)
String NTPDriver = WebUI.getText(findTestObject('HomePage/lbl_NeedToPayToDriver'))
if(NTPDriverDB != Integer.parseInt(NTPDriver)){
	//error
}


//Need to pay to agency
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('')
res.next()
int NTPAgencyDB = res.getInt(1)
String NTPAgency = WebUI.getText(findTestObject('HomePage/lbl_NeedToPayToAgency'))
if(NTPAgencyDB != Integer.parseInt(NTPAgency)){
	//error
}


//taxes
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('')
res.next()
int TaxesCountDB = res.getInt(1)
String TaxesCount = WebUI.getText(findTestObject('HomePage/lbl_Taxes'))
if(TaxesCountDB != Integer.parseInt(TaxesCount)){
	//error
}



//company balance
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('')
res.next()
int companyBalancetDB = res.getInt(1)
String companyBalancet = WebUI.getText(findTestObject('HomePage/lbl_CompanyBalance'))
if(companyBalancetDB != Integer.parseInt(companyBalance)){
	//error
}
*/

//Most Used Devices count
res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select count(*) from devices')
res.next()
int MostUsedDevicesCountDB = res.getInt(1)
String MostUsedDevicesCount = WebUI.getText(findTestObject('HomePage/lbl_MostUsedDevices'))
if(MostUsedDevicesCountDB != Integer.parseInt(MostUsedDevicesCount)){
	//error
	log.logError("Most Used Devices Count is wrong should be "+MostUsedDevicesCountDB)
	Failed = true;
}


//Get table of devices names
boolean found = false;

res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('SELECT distinct(device_type) FROM devices')
while(res.next()){
	String type = res.getString(1)

	WebDriver driver = DriverFactory.getWebDriver()
	WebElement Table = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div[1]/div[2]/div/div/div[2]/div[2]/table"))
	List<WebElement> rows_table = Table.findElements(By.tagName('tr'))
	int rows_count = rows_table.size()
	for (int row = 0; row < rows_count; row++) {
		List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName('td'))
		int columns_count = Columns_row.size()
		String celltext = Columns_row.get(1).getText()

		if(celltext.equals(type)){
			found=true;
			break;
		}
	}
	
	//value from the db doesn't appear in the table
	if(!found){
		// error not all devices names appears in the graph
		//break from the loop 
		// log the names that doesn't appear
		log.logError("Some Device Names doesn't appear in the graph: "+ type)
		Failed = true;
	}

}


if(Failed){
	// test case failed
	log.logPassed("Home Page Count values are Correct")
}
else{
	//test case passed
	log.logFailed("Some Home Page Count values are InCorrect")
} 

log.logInfo("End of Home Page Testcase");

CustomKeywords.'com.database.lutfi.Database.closeDatabaseConnection'()

WebUI.closeBrowser()