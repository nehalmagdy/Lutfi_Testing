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

WebUI.click(findTestObject('AgencyControlPage/AgencyMenuItem'))

WebUI.click(findTestObject('AgencyControlPage/AgencySubItem'))

WebUI.waitForPageLoad(20)

KeywordLogger log = new KeywordLogger()

WebUI.click(findTestObject('AgencyControlPage/btn_ReFill'))

String from = WebUI.getText(findTestObject('AgencyControlPage/SearchCard/txt_From'))

String to =  WebUI.getText(findTestObject('AgencyControlPage/SearchCard/txt_To'))

String comission =  WebUI.getText(findTestObject('AgencyControlPage/SearchCard/txt_comission'))

String mobile = WebUI.getText(findTestObject('AgencyControlPage/SearchCard/txt_mobile'))

String name = WebUI.getText(findTestObject('AgencyControlPage/SearchCard/txt_Name'))

if(name.equals("")&&from.equals("")&&to.equals("")&&mobile.equals("")&&comission.equals("")){
	log.logPassed("All Fields are clear")
}
else{
	log.logFailed("Not All Fields are Clear")
}

//connect to db
CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')

//#of agencies that should appear
ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select count(*) from agency where deleted_at is null')
res.next()
int agencyCount = res.getInt(1)

//get agency grid Table count
WebDriver driver = DriverFactory.getWebDriver()
WebElement Table = driver.findElement(By.xpath('//*[@id=\'dataTableBuilder\']'))
WebUI.delay(10)
WebElement tbody = Table.findElement(By.tagName('tbody'))
WebUI.delay(10)
List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))
int rows_count = rows_table.size()
WebUI.delay(10)
if (rows_count == agencyCount) {
	log.logPassed("Data is Refilled Correctly")
}
else{
	log.logFailed("Data is Refilled InCorrectly")
}