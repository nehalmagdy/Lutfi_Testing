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
import sun.print.ServiceDialog.AppearancePanel

import org.openqa.selenium.support.ui.Select as Select
import org.openqa.selenium.By as By
import java.sql.ResultSet as ResultSet
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger

boolean AllFound = true;
boolean correct = WebUI.callTestCase(findTestCase('Agency Sub Menu/Agency/ValidateCountryCityAgency'), [:], FailureHandling.STOP_ON_FAILURE)
//WebUI.callTestCase(findTestCase('Valid_Login'), [('username') : 'nehal@gmail.com', ('password') : 'testernehal735'], FailureHandling.STOP_ON_FAILURE)
//WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencyMenuItem'))
//WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencySubItem'))
//WebUI.waitForPageLoad(20)
//WebUI.click(findTestObject('AgencyControlPage/AgencyPage/btn_AddAgency'))

KeywordLogger log = new KeywordLogger()

System.out.println(correct)

data = findTestData('AddAgency')

if (correct) {
    for (def index : (0..data.getRowNumbers() - 1)) {
		 WebUI.selectOptionByLabel(findTestObject('AgencyControlPage/AddAgencyPage/cmbo_Country'), data.internallyGetValue('Country', index), 
            false,FailureHandling.OPTIONAL)

        WebUI.selectOptionByLabel(findTestObject('AgencyControlPage/AddAgencyPage/combo_City'), data.internallyGetValue('City', index), 
            false,FailureHandling.OPTIONAL)

        WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyName'), data.internallyGetValue('Name', index))

        WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyCommission'), data.internallyGetValue('Comission', index))

        WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyEmail'), data.internallyGetValue('Email', index))

        WebUI.setText(findTestObject('AgencyControlPage/AddAgencyPage/txt_AgencyMobile'), data.internallyGetValue('Mobile', index))

        WebUI.click(findTestObject('AgencyControlPage/AddAgencyPage/btn_SaveAgency'))
		
		
		String ErrorMessages = data.internallyGetValue('Error Messages', index)
		
		
		WebDriver driver = DriverFactory.getWebDriver()
		WebUI.delay(10)
		
		// need to check the presence of the item on the page first as an error may not appear at it navigates to the next page
		// 
		
		if(WebUI.verifyElementPresent(findTestObject("AgencyControlPage/AddAgencyPage/ErrorsDiv"), 4,FailureHandling.OPTIONAL)){
		
				WebElement errorsDiv = driver.findElement(By.xpath('/html/body/div[2]/div[3]/div/div[1]/div/div/div[2]/div'))
				WebUI.delay(10)
				
				//appears on web
				List<WebElement> errorsItems = errorsDiv.findElements(By.tagName('li'))
				WebUI.delay(10)
				
			
				//comes from the file
				String[] arr = ErrorMessages.split("\n")
					//if(arr.count() == errorsItems.size()){
				
						for(int i=0;i<arr.length;i++){
							boolean found = false;
							for(int j=0;j<errorsItems.size();j++){
								String itemText = errorsItems[j].getText();
								if(itemText.equals(arr[i])){
									//
									found=true;
									break;
								}
							}
							if(!found){
								//some error messages doesn't appear arr[i]
								log.logError("Error Message didn't appear "+arr[i])
								log.logFailed("Case # "+ index +" Failed")
								AllFound=false;
							}
						}
					//}
					//else{//different Error messages size
						
					//}
	    	}
			else{
				AllFound = false;
				log.logFailed("Case # "+ index +" Failed")
			}			
    }
	
	if(AllFound){
		log.logPassed("Test Case Passed")
	}
	else
	{
		log.logFailed("Test Case Failed")
	}
	
}

WebUI.closeBrowser()