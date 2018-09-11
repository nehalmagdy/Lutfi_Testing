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

if(rows_count > 0){
	List<WebElement> Columns_row = rows_table.get(rows_count - 1).findElements(By.tagName('td'))
	String id = Columns_row[0].findElement(By.tagName("input")).getAttribute("value")
	
	//click on edit button
	Columns_row[2].findElement(By.tagName('a')).click()	
	
	String query = "select id,name,comission,mobile,email,country_id,city_id from agency where id = " +id;
	ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'(query)
	res.next()
	
	String name = res.getString(2).trim()
	String comission = res.getString(3).trim()
	String mobile = res.getString(4).trim()
	String email = res.getString(5).trim()
	
	String countryID = res.getString(6)
	String cityID = res.getString(7)
	
	String queryCity = "select name from city where id = " +cityID;
	ResultSet resCity = CustomKeywords.'com.database.lutfi.Database.executeQuery'(queryCity)
	resCity.next()
	String city = resCity.getString(1).split(',')[0].split(':')[1]
	city = city.substring(1,city.length()-1).trim()
	
	String queryCountry = "select name from country where id = " +countryID;
	ResultSet resCountry = CustomKeywords.'com.database.lutfi.Database.executeQuery'(queryCountry)
	resCountry.next()
	String country = resCountry.getString(1).split(',')[0].split(':')[1].trim()
	country = country.substring(1,country.length()-1).trim()
	
	String sCity="";
	String sCountry ="";
	
	List<WebElement> options = driver.findElement(By.xpath('//*[@id="country"]')).findElements(By.tagName('option'))
	for(int i=0;i<options.size();i++){
		
			sCountry = options[i].getAttribute('selected')
			if(sCountry!=null && sCountry.equalsIgnoreCase("true")){
				sCountry=options[i].getText().trim()
				break;
			}
		
	}
	
	
	 options = driver.findElement(By.xpath('//*[@id="city"]')).findElements(By.tagName('option'))
	 for(int i=0;i<options.size();i++){		 
			 sCity = options[i].getAttribute('selected')
			 if(sCity!=null && sCity.equalsIgnoreCase("true")){
				 sCity=options[i].getText().trim()
				 break;
			 }
	 }
	
	
	String sName = driver.findElement(By.xpath('//*[@id="name"]')).getAttribute('value').trim()
	
	String sComission = driver.findElement(By.xpath('//*[@id="comission"]')).getAttribute('value').trim()

	String sEmail = driver.findElement(By.xpath('//*[@id="email"]')).getAttribute('value').trim()

	String sMobile = driver.findElement(By.xpath('//*[@id="mobile"]')).getAttribute('value').trim()
	
		
	if(sCity.equalsIgnoreCase(city) && sCountry.equalsIgnoreCase(country) && sName.equalsIgnoreCase(name) && sComission.equalsIgnoreCase(comission) && sEmail.equalsIgnoreCase(email) && sMobile.equalsIgnoreCase(mobile))
	{
		log.logPassed("All Data is Shown Correctly")
	}
	else{
		log.logFailed("There is an Error in the shown data")
	}
	
}