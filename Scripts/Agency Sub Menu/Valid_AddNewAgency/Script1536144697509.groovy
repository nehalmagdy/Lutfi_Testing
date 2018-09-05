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

boolean correct = WebUI.callTestCase(findTestCase('Agency Sub Menu/ValidateCountryCityAgency'), [:], FailureHandling.STOP_ON_FAILURE)

KeywordLogger log = new KeywordLogger()

System.out.println(correct)

boolean Failed = false;

//if (correct) {
           
String c = country
String ci = city  
       WebUI.selectOptionByLabel(findTestObject('AgencyControlPage/cmbo_Country'), country,false,FailureHandling.OPTIONAL)

	   WebUI.delay(10)
        WebUI.selectOptionByLabel(findTestObject('AgencyControlPage/combo_City'), city, false,
            FailureHandling.OPTIONAL)

		WebUI.delay(10)
		
        WebUI.setText(findTestObject('AgencyControlPage/txt_AgencyName'), name)

        WebUI.setText(findTestObject('AgencyControlPage/txt_AgencyCommission'), comission)

        WebUI.setText(findTestObject('AgencyControlPage/txt_AgencyEmail'), mail)

        WebUI.setText(findTestObject('AgencyControlPage/txt_AgencyMobile'), mobile)

        WebUI.click(findTestObject('AgencyControlPage/btn_SaveAgency'))
		
		String url = WebUI.getUrl();
		if(url== "https://staging.lutfi-car.com/en/admin/agency")
		{
			//validate the successfully card
		
			
			
			//go to the edit page to validate that the data is correct
			WebDriver driver = DriverFactory.getWebDriver()			
			WebElement Table = driver.findElement(By.xpath('//*[@id=\'dataTableBuilder\']'))
			WebUI.delay(10)
			WebElement tbody = Table.findElement(By.tagName('tbody'))
			WebUI.delay(10)
			List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))
			int rows_count = rows_table.size()
			
			List<WebElement> Columns_row = rows_table.get(rows_count-1).findElements(By.tagName('td'))
			Columns_row[2].findElement(By.tagName("a")).click();
			
			WebUI.waitForPageLoad(10)
			
			
			String ename = WebUI.getAttribute(findTestObject('AgencyControlPage/txt_AgencyName'),'value')
	
			String ecom =  WebUI.getAttribute(findTestObject('AgencyControlPage/txt_AgencyCommission'),'value')
	
			String email =  WebUI.getAttribute(findTestObject('AgencyControlPage/txt_AgencyEmail'),'value')
	
			String emob =  WebUI.getAttribute(findTestObject('AgencyControlPage/txt_AgencyMobile'),'value')
	
			if(ename.equals(name) && ecom.equals(comission) && email.equals(mail)&&emob.equals(emob))
			{
				log.logPassed("All shown Values are correct")
			}
			else{
				log.logFailed("Not All Values appeared correct")
				Failed=true;
			}			
		}
		else
		{
			//wrong page navigation		
			log.logFailed("Wrong Page Navigation")
			Failed = true;
		}

	if(Failed){
		log.logFailed("Test Case Failed")
			
	}
	else{
		log.logPassed("Test case passed")
	}
//}
	
	WebUI.closeBrowser()