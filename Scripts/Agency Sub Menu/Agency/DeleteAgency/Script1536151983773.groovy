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

//Random rnd = new Random()
//int rowind = (1 + rnd.nextInt(rows_count))

List<WebElement> Columns_row = rows_table.get(rows_count - 1).findElements(By.tagName('td'))
String id = Columns_row[0].findElement(By.tagName("input")).getAttribute("value")

Columns_row[3].findElement(By.tagName('span')).click()

WebUI.click(findTestObject('AgencyControlPage/YesDelete'))

//validate the success card


//
 Table = driver.findElement(By.xpath('//*[@id=\'dataTableBuilder\']'))
 WebUI.delay(10)
 tbody = Table.findElement(By.tagName('tbody'))
 WebUI.delay(10)
 rows_table = tbody.findElements(By.tagName('tr'))
int nrows_count = rows_table.size()
Columns_row = rows_table.get(nrows_count - 1).findElements(By.tagName('td'))

String nid = Columns_row[0].findElement(By.tagName("input")).getAttribute("value")

if(nrows_count== rows_count-1 && nid!=id){
	log.logPassed("Passed: Deleted Successfully")
}
else{
	log.logFailed("Failed: Not Deleted")
}