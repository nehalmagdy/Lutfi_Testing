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

//Random rnd = new Random()
//int rowind = (1 + rnd.nextInt(rows_count))
if (rows_count > 0) {
    List<WebElement> Columns_row = rows_table.get(rows_count - 1).findElements(By.tagName('td'))

    String id = Columns_row[0].findElement(By.tagName('input')).getAttribute('value')

    //click on edit button
    Columns_row[2].findElement(By.tagName('a')).click() 

	// Change the values of the fields 
	WebUI.waitForPageLoad(10)
	
	String sName = driver.findElement(By.xpath('//*[@id="name"]')).getAttribute('value').trim()
	 
	WebUI.delay(2)
	
	 String sComission = driver.findElement(By.xpath('//*[@id="comission"]')).getAttribute('value').trim()
	 WebUI.delay(2)
	 
	 String sEmail = driver.findElement(By.xpath('//*[@id="email"]')).getAttribute('value').trim()
	 WebUI.delay(2)
	 
	 String sMobile = driver.findElement(By.xpath('//*[@id="mobile"]')).getAttribute('value').trim()
	 WebUI.delay(2)
	 
 
	
		WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyName'), nName+sName)
		WebUI.delay(2)
		
		WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyCommission'), ncomission)
		WebUI.delay(2)
		
		WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyMobile'), nmobile )
		WebUI.delay(2)
		
		WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyEmail'), nEmail+sEmail)
		WebUI.delay(2)
		
		WebUI.selectOptionByLabel(findTestObject('AgencyControlPage/AddAgencyPage/cmbo_Country'), nCountry, false, FailureHandling.OPTIONAL)
		WebUI.delay(2)
		
		
		WebUI.selectOptionByLabel(findTestObject('AgencyControlPage/AddAgencyPage/combo_City'), nCity, false, FailureHandling.OPTIONAL)
		WebUI.delay(2)
		
		WebUI.click(findTestObject('AgencyControlPage/AddAgencyPage/btn_SaveAgency'))
		
		WebUI.delay(2)
		
// validate that the db is updated with the new data
		String query = "select agency.name,mobile,city.name,country.name,email,comission from agency inner join city on agency.city_id = city.id inner join country on country.id = agency.country_id where agency.id = "+id
		ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'(query)
		res.next()
		
		/*String s=res.getString(1)+":::::"+nName+sName
		//String s2 = res.getString(2)+":::::"+sMobile+nmobile
		String s3= res.getString(3)+":::::::"+nCity
		String s4= res.getString(4)+":::::::"+nCountry
		String s5= res.getString(5)+":::::::"+nCity
		String s6= res.getString(6)+":::::::"+ncomission
		*/
		 String DBCity = res.getString(3).split(',')[0].split(':')[1]
		 DBCity = DBCity.substring(1,DBCity.length()-1).trim()
		
		 String DBCountry = res.getString(4).split(',')[0].split(':')[1]
		 DBCountry = DBCountry.substring(1,DBCountry.length()-1).trim()
		
		if(res.getString(1).equalsIgnoreCase(nName+sName) &&
			res.getString(2).equalsIgnoreCase(nmobile) &&
			DBCity.equalsIgnoreCase(nCity) &&
			DBCountry.equalsIgnoreCase(nCountry) && 
			res.getString(5).equalsIgnoreCase(nEmail+sEmail) &&
			res.getString(6).equalsIgnoreCase(ncomission) )
		{
			log.logPassed("Pass: Data is Updated Successfully")
		}
		else
		{
			log.logFailed("Fail: Data is not Updated")
		}
				
}
