package resolverAssessment;

import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeTest;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;


public class ResolverTest {
	WebDriver driver;                                // declaring instance variables to access in all methods
	private WebDriverWait wait;
	static ExtentReports report;
	static ExtentTest test;

	@Test(priority = 1, description = "Test 001 To validate the visibility and functionality of email and password field")
	public void validatingEmailAndPasswordFieldTest() throws IOException {
					test.log(LogStatus.INFO, "Test case 001 validating email and password started");
		WebElement emailField = driver.findElement(By.id("inputEmail")); // Locating Email Field
		WebElement passwordField = driver.findElement(By.id("inputPassword")); // Locating password field
		// 1. Assertion to check if the email field is displayed
		Assert.assertTrue(emailField.isDisplayed(), "The email field is displayed on the web page.");
		// 2. Assertion to check if the password field is displayed
		Assert.assertTrue(passwordField.isDisplayed(), "The password field is displayed on the web page.");
		// 3. Assert that the Sign in button is present
		WebElement signIn = driver.findElement(By.xpath("//button[text()='Sign in']"));
		Assert.assertTrue(signIn.isDisplayed(), "The sign in button is displayed on the web page.");
		// 4. Enter email address and password
		emailField.sendKeys("email@example.com");
		passwordField.sendKeys("password");
					test.log(LogStatus.PASS, "Test case 001 is passed");
	}

	@Test(priority = 2, description = "Test 002 To verify the functionality of listgroups")
	public void verifyingListGroups() {
					test.log(LogStatus.INFO, "Test case 002 verifying the functionality of listgroups started");
		List<WebElement> listGroups = driver.findElements(By.xpath("//li[contains(@class,'list-group-item')]")); // Locating all elements in the group and storing it to a java collection List
		// 1. To verify the size of list groups is 3
		Assert.assertEquals(listGroups.size(), 3);
		// 2. Assert that the second list item's value is set to "List Item 2"
		Assert.assertTrue(listGroups.get(1).getText().contains("List Item 2")); // Verifying that the inner text of the WebElement contains the expected String
		// 3. Assert that the second list item's badge value is 6
		WebElement badge = driver.findElement(By.xpath("//span[contains(@class,'badge-primary')]//following::span[1]")); // Locating badge element
		String badgeValue = badge.getText(); // Capturing the inner text value of WebElement and storing it in String variable
		int actualBadgeValue = Integer.parseInt(badgeValue); // converting the datatype of badgeValue from String to integer
		Assert.assertEquals(actualBadgeValue, 6);
					test.log(LogStatus.PASS, "Test case 002 is passed");
	}

	@Test(priority = 3, description = "Test 003 To verify the default selected value and functionality of drop down")
	public void verifyingDropDown() {
					test.log(LogStatus.INFO, "Test case 003 verifying the functionality of drop down started");
		WebElement test3 = driver.findElement(By.id("test-3-div"));
		JavascriptExecutor js = (JavascriptExecutor) driver; // to scroll the page to a specific element. Here the page is scrolled to the Title Test3
		js.executeScript("arguments[0].scrollIntoView();", test3);

		// 1. assert that "Option 1" is the default selected value
		WebElement selectElement = driver.findElement(By.id("dropdownMenuButton")); // Locating the default selected value
		Assert.assertEquals(selectElement.getText(), "Option 1");
		selectElement.click();                                                      // To click the option dropdown to display all options

		// 2. Select "Option 3" from the select list
		List<WebElement> allOptions = driver.findElements(By.xpath("//a[@class='dropdown-item']")); // Capturing all the options to a variable allOptions and storing it to a java collection List
		allOptions.get(2).click(); // Selecting Option 3 from allOptions
		Assert.assertEquals(selectElement.getText(), "Option 3");
					test.log(LogStatus.PASS, "Test case 003 is passed");
	}

	@Test(priority = 4, description = "Test 004 To check the Active and disabled status of buttons")
	public void testButtonStatus() {
					test.log(LogStatus.INFO, "Test case 004 to check the Active and disabled status of buttons started");
		WebElement divElement = driver.findElement(By.id("test-4-div")); // Getting the div element from DOM containing Test 4
		WebElement firstButton = divElement.findElement(By.xpath(".//button[1]")); // From the Test4 locator fetching the first button locator

		// 1. Assert that the first button is enabled
		Assert.assertTrue(firstButton.isEnabled(), "First button is not enabled.");
		WebElement secondButton = divElement.findElement(By.xpath(".//button[2]")); // From the Test4 locator fetching the second disabled button locator

		// 2. Assert that the second button is disabled
		Assert.assertFalse(secondButton.isEnabled(), "Second button is not disabled.");
					test.log(LogStatus.PASS, "Test case 004 is passed");
	}

	@Test(priority = 5, description = "Test 005 To validate the functionality of button with random delay")
	public void testButtonClickAndAssert() {
					test.log(LogStatus.INFO, "Test case 005 to validate the functionality of button with random delay started");
		WebElement divElement = driver.findElement(By.id("test-5-div")); // Locate the Element Test 5 in DOM structure containing the button
		WebElement button = divElement.findElement(By.tagName("button")); // Locate the button in Test 5

		// 1. Wait for the button to be displayed (with a random delay)
		int randomDelay = ThreadLocalRandom.current().nextInt(5, 15);     // provides a mechanism for generating random numbers in a concurrent environment
					test.log(LogStatus.WARNING, "Waiting for " + randomDelay + " seconds before clicking the button.");
		try {
			Thread.sleep(randomDelay * 1000);     // Pause the execution of the current thread for randomDelay seconds and converting seconds to milliseconds
		} catch (InterruptedException e) {
			e.printStackTrace();                  // Handle any potential InterruptedException that might be thrown
		}
		button.click();                           // Click the button

		// 2. Wait for success message to be displayed
		WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("test5-alert")));

		// Assert that the success message is displayed
		Assert.assertTrue(successMessage.isDisplayed(), "Success message is displayed.");

		// 3. Assert that the button is now disabled
		Assert.assertFalse(button.isEnabled(), "Button is disabled.");
					test.log(LogStatus.PASS, "Test case 005 is passed");
	}

	@Test(priority = 6, description = "Test 006 To verify the web table functionality and fetch the value from a particular row and column")
	public void testGridCellValue() {
					test.log(LogStatus.INFO, "Test case 006 to verify the web table functionality started");
		// 2. Use the method to find the value of the cell at coordinates 2, 2 (starting at 0 in the top left corner)
		WebElement cellValue = getGridCellValue(2, 2);                        // This statement calls the method getGridCellValue and WebElement cellValue holds the WebElement in the table at position (2,2)
		// 3. Assert that the value of the cell is "Ventosanzap"
		Assert.assertEquals(cellValue.getText(), "Ventosanzap", "Cell value matches the expected value");   //Verifying that the inner text of the WebElement at position (2,2) is same as the expected value
					test.log(LogStatus.PASS, "Test case 006 is passed");
	}

	// 1. Method to find the value of any cell on the grid
	private WebElement getGridCellValue(int row, int col) {
		String tableElements = "//table[contains(@class, 'table table-bordered table-dark')]/tbody/tr[" + (row + 1)+ "]/td[" + (col + 1) + "]";  //this general locator strategy is used to locate the elements in web table as the given row and column number.
		return driver.findElement(By.xpath(tableElements));      //this method returns the WebElement at specific row and column
	}

	@BeforeTest
	public void beforeTest() {

		WebDriverManager.chromedriver().setup(); // Set the path to the ChromeDriver executable
		driver = new ChromeDriver(); // Initialize the ChromeDriver
		wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Set an appropriate timeout
		// Navigating to home page
		driver.get("C:\\Users\\Sudheesh\\Downloads\\technicalassessmentresolver\\QE-index.html");
		driver.manage().window().maximize();// To maximize the window
        //to create Extent report
		report = new ExtentReports(System.getProperty("user.dir") + "\\report\\report.html", true);
					test = report.startTest("Extent Report for Resolver Technical Assessment");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));// declaring Implicit wait to give global wait for the entire duration of the WebDriver instance
	}

	@AfterTest
	public void tearDown() {
		// Close the browser after the test
		driver.quit();              //To ensure that all browser windows are properly closed
		report.endTest(test);       //To mark the end of the test
		report.flush();             //Flush the extent report to generate the report file

	}
}
