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
import org.openqa.selenium.Keys as Keys


KeywordLogger log = new KeywordLogger()

WebUI.callTestCase(findTestCase('Valid_Login'), [('username') : 'nehal@gmail.com', ('password') : 'testernehal735'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencyMenuItem'))

WebUI.click(findTestObject('AgencyControlPage/MenuItems/AgencyUserMenuItem'))

WebUI.waitForPageLoad(20)


//connect to db
CustomKeywords.'com.database.lutfi.Database.connectDB'('139.162.159.139', 'staging', '3306', 'nehal', 'xRETOHqqhrnNa85e')


data = findTestData('SearchAgencyUsers')

for (def index : (0..data.getRowNumbers() - 1)) {

	String from = data.internallyGetValue('monthF', index)+data.internallyGetValue('dayF', index)+data.internallyGetValue('yearF', index)
    String to = data.internallyGetValue('monthT', index)+data.internallyGetValue('dayT', index)+data.internallyGetValue('yearT', index)
    
	
	String name = data.internallyGetValue('name', index)
    String email = data.internallyGetValue('email', index)
	
		
	
	WebUI.clickOffset(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'),0,0)
	
	if(from.equalsIgnoreCase("")){
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'), Keys.chord('f', Keys.BACK_SPACE))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'), Keys.chord('f', Keys.ARROW_RIGHT))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'), Keys.chord('f', Keys.BACK_SPACE))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'), Keys.chord('f', Keys.ARROW_RIGHT))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'), Keys.chord('f', Keys.BACK_SPACE))
		//WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyPage/SearchCard/txt_From'), Keys.chord('f', Keys.ARROW_RIGHT))
	}
	else{
	WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_From'), from.replaceAll("/", ""))
	}
   
	 WebUI.clickOffset(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'),0,0)
	
	 if(to.equalsIgnoreCase("")){
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'), Keys.chord('f', Keys.BACK_SPACE))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'), Keys.chord('f', Keys.ARROW_RIGHT))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'), Keys.chord('f', Keys.BACK_SPACE))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'), Keys.chord('f', Keys.ARROW_RIGHT))
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'), Keys.chord('f', Keys.BACK_SPACE))
	}
	else{
		WebUI.sendKeys(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_to'), to.replaceAll("/", ""))
	}
	
	WebUI.setText(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_email'), email)
	WebUI.setText(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/txt_name'), name)

    WebUI.click(findTestObject('AgencyControlPage/AgencyUsersPage/SearchCard/btn_search'))
	
	boolean previousAdded = false;
	boolean whereAdded = false;
	
	String Query = "select id from users "
	String cQuery = "select count(*) from users "
	
	if(! name.equalsIgnoreCase("")){
		if(!whereAdded){
			Query+=" where "
			cQuery+=" where "
			whereAdded = true;
		}
		Query+= "name ='"+name+"'" 
		cQuery+= "name ='"+name+"'"
		previousAdded=true;
	}
	if(! email.equalsIgnoreCase("")){
		if(!whereAdded){
			Query+=" where "
			cQuery+=" where "
			whereAdded = true;
		}
		if(previousAdded){
			Query+=" and "
			cQuery+=" and "
		}
		Query+= "email = '"+email+"'"
		cQuery+= "email = '"+email+"'"
		previousAdded=true;
	}
	if(! to.equalsIgnoreCase("")){
		String toDB = data.internallyGetValue('yearT', index)+"-"+data.internallyGetValue('monthT', index)+"-"+data.internallyGetValue('dayT', index)
		
		if(!whereAdded){
			Query+=" where "
			cQuery+=" where "
			whereAdded = true;
		}
		if(previousAdded){
			Query+=" and "
			cQuery+=" and "
		}
		Query+= "date(created_at) <= '"+toDB+"'"
		cQuery+= "date(created_at) <= '"+toDB+"'"
		previousAdded=true;
	}
	if(! from.equalsIgnoreCase("")){
		String fromDB = data.internallyGetValue('yearF', index)+"-"+data.internallyGetValue('monthF', index)+"-"+data.internallyGetValue('dayF', index)
		
		if(!whereAdded){
			Query+=" where "
			cQuery+=" where "
			whereAdded = true;
		}
		if(previousAdded){
			Query+=" and "
			cQuery+=" and "
		}
		Query+= "date(created_at) >= '"+fromDB+"'"
		cQuery+= "date(created_at) >= '"+fromDB+"'"
		previousAdded=true;
	}
	if(! whereAdded) {
		Query+=" where "
		cQuery+=" where "
	}
	if(previousAdded){
		Query+=" and "
		cQuery+=" and "
	}
	Query +=" deleted_at is null and group_id =3"
	cQuery +=" deleted_at is null and group_id =3"
	
	ResultSet res = CustomKeywords.'com.database.lutfi.Database.executeQuery'(Query)
	//res.next()
	
	ResultSet cres = CustomKeywords.'com.database.lutfi.Database.executeQuery'(cQuery)
	cres.next()
	int count = cres.getInt(1)
		
	
	WebDriver driver = DriverFactory.getWebDriver()
	WebElement Table = driver.findElement(By.xpath('//*[@id=\'dataTableBuilder\']'))
	WebUI.delay(10)
	WebElement tbody = Table.findElement(By.tagName('tbody'))
	WebUI.delay(10)
	List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))
	int rows_count = rows_table.size()
	WebUI.delay(10)
	boolean AllFound = true;
	
	String EmptyGrid = WebUI.getText(findTestObject("AgencyControlPage/AgencyUsersPage/EmptyGridTD"))
	if(EmptyGrid.equalsIgnoreCase("No data available in table") && count==0){
		//pass
		log.logPassed("Correct Data")
	}
	else if (rows_count == count && count!=0) {
		while(res.next()){
			boolean Found = false;			
			for (int row = 0; row < rows_count; row++) {
				List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName('td'))		
				//int columns_count = Columns_row.size()
				String cellID = Columns_row[0].findElement(By.tagName("input")).getAttribute("value")
				String DBID = res.getInt(1)
		
				if (cellID.equalsIgnoreCase(DBID)) {
					Found = true
					break
				}
			}
			if(!Found){
				//Fail: an agency from the db search doesn't appear in the view
				AllFound = false;
				log.logFailed("Fail: an agency from the db search doesn't appear in the view "+index)
				break;
			}
		}
	}
	else{
		AllFound = false;
		//Fail: the count of the shown data is different from the db result count
		log.logFailed("Fail: the count of the shown data is different from the db result count "+index)
		
	}
	
	if(AllFound){
		//Pass: the result is correct for the search query
		log.logPassed("Pass: the result is correct for the search query "+index)
	}
	else{
		//Fail: the result is incorrect for the search query
		log.logFailed("Fail: the result is incorrect for the search query "+index)
			
	}
}

CustomKeywords.'com.database.lutfi.Database.closeDatabaseConnection'()