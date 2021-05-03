
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.xml.bind.annotation.W3CDomHandler;
import java.util.Random;

public class GittiGidiyor {

    static final Logger logger = LogManager.getLogger(GittiGidiyor.class);
    String basePage = "https://www.gittigidiyor.com/";
    String mail = "Mail adresi giriniz";
    String password = "Şifre giriniz";
    String searchword ="bilgisayar";

    static WebDriver driver;

    @BeforeClass
    public  static  void ChromeOpen(){
       System.setProperty("webdriver.chrome.driver","C:/Users/trkmn/Desktop/Yeni klasör/chromedriver.exe");
    driver = new ChromeDriver();
    //
    }

    @Test
    public void Login() throws InterruptedException {
        driver.manage().window().maximize();
        //Siteye girme ve Kontrol
        driver.get(basePage);
        Assert.assertEquals(driver.getTitle(), "GittiGidiyor - Türkiye'nin Öncü Alışveriş Sitesi");

        //Üye girişi
        driver.get("https://www.gittigidiyor.com/uye-girisi");
        driver.findElement(By.id("L-UserNameField")).sendKeys(mail);
        driver.findElement(By.id("L-PasswordField")).sendKeys(password);
        driver.findElement(By.id("gg-login-enter")).click();
        Thread.sleep(3000);
        logger.info("Üye işlemi gerçekleşti");

        //Arama işlemi
        WebElement search = driver.findElement(By.name("k"));
        search.sendKeys(searchword);
        search.click();
        Thread.sleep(3000);
        logger.info("Arama gerçekleşti");

        //2.sayfaya gitme
        driver.get(basePage+"arama/?k=bilgisayar&sf=2");
        logger.info("2. sayfaya girildi");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");
        Thread.sleep(3000);

        //Rastgele ürün seçmek
        int rastgeleürün = new Random().nextInt(48);
        driver.findElement(By.cssSelector(".products-container > li[product-index='"+rastgeleürün+"']")).click();
        logger.info("2.Sıradaki ürün seçildi.");

        //Sepete ekleme
        driver.findElement(By.cssSelector("a[class='policy-alert-close btn-alert-close']")).click();
        WebElement element = driver.findElement(By.id("add-to-basket"));
        element.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(3000);
        logger.info("Sepete eklendi");

        String sayfaFiyatı = driver.findElement(By.id("sp-price-lowPrice")).getText();
        logger.info("ürünün sayfa fiyatı "+ sayfaFiyatı);

        //Sepete gitme
        driver.get(basePage+"sepetim");
        String sepetFiyatı=driver.findElement(By.cssSelector("div[class='total-price']")).getText();
        logger.info("Sepetimdeki fiyat " +sepetFiyatı);
        Assert.assertEquals(sayfaFiyatı,sepetFiyatı);
        Thread.sleep(3000);

        //2 Adet yapma
        driver.findElement(By.cssSelector("select.amount>:nth-child(2)")).click();
        logger.info("2 adet eklendi");

        //Sepeti boşaltma
        Thread.sleep(2000);
        driver.findElement(By.className("btn-delete")).click();
        logger.info("Sepet boşaltıldı");

        //Sepet Kontrolü
        Thread.sleep(2000);
        Assert.assertEquals("Sepetinizde ürün bulunmamaktadır.", "Sepetinizde ürün bulunmamaktadır.");
        logger.info("Sepet Kontrol edildi.");









    }


}
