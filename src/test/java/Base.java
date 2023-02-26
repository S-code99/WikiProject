import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Base {
    public WebDriver driver;
    public static String url="https://en.m.wikipedia.org/wiki/Meleeka_Clary";
    @BeforeClass
    public void openBrowser()
    {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }
    @AfterClass
    public void closeBrowser()
    {
        driver.close();
    }
}


