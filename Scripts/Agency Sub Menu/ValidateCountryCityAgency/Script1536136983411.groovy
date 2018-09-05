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

KeywordLogger log = new KeywordLogger()

log.logInfo('Agency Data Test Case Started')

//connect to db
CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')

//Get Country Names
ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'('select name from country')

res.next()

WebUI.click(findTestObject('AgencyControlPage/AgencyMenuItem'))

WebUI.click(findTestObject('AgencyControlPage/AgencySubItem'))

WebUI.waitForPageLoad(20)

WebUI.click(findTestObject('AgencyControlPage/btn_AddAgency'))

//validate countryNames 
WebDriver driver = DriverFactory.getWebDriver()
WebUI.delay(10)

WebElement Table = driver.findElement(By.xpath('//*[@id=\'country\']'))
WebUI.delay(10)

List<WebElement> comboOptions = Table.findElements(By.tagName('option'))
WebUI.delay(10)

boolean notFound = false

String CountryAt0="";
for (int i = 0; i < comboOptions.size(); i++) {
    String DBCountryName = res.getString(1).split(',')[0].split(':')[1] //0--> En, 1--> country name
    DBCountryName = DBCountryName.substring(1, DBCountryName.length()-1).trim()
	String optionValue = comboOptions[i].getText().trim()

	if(i==0) CountryAt0 = optionValue
	
    if (!(DBCountryName.equals(optionValue))) {
        notFound = true
        break
    }
    
    res.next()
}

if (notFound) {
    log.logError('Not All Country names appear in the combo')
} else {
    log.logInfo('All Country names appeared successfully')
}


//select cities of the chosen country
// get the selected country text

res = CustomKeywords.'com.database.lutfi.Database.executeQuery'("SELECT city.name FROM staging.city inner join country on country.id = city.country_id where country.name like '%"+CountryAt0+"%'")
res.next()

//validate city names
driver = DriverFactory.getWebDriver()

Table = driver.findElement(By.xpath('//*[@id="city"]'))
WebUI.delay(10)

comboOptions = Table.findElements(By.tagName('option'))
WebUI.delay(10)

notFound = false

for (int i = 0; i < comboOptions.size(); i++) {
    String DBCityName = res.getString(1).split(',')[0].split(':')[1] //0--> En, 1--> country name
	DBCityName = DBCityName.substring(1,DBCityName.length()-1).trim()
	String displayValue = comboOptions[i].getAttribute("style");
	if(! displayValue.contains("display: none")){
	    String optionValue = comboOptions[i].getText().trim()
	
	    if (!(DBCityName.equals(optionValue))) {
	        notFound = true
	        break
	    }
		res.next()
	}
	    
}

if (notFound) {
    log.logError('Wrong City Names or not complete ')
	return false;
} else {
    log.logInfo('All City names appeared successfully')
	return true;
}

CustomKeywords.'com.database.lutfi.Database.closeDatabaseConnection'()
