import com.codetest.ui.CheckersPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CheckersTest {
    WebDriver driver;
    CheckersPage checkersPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        checkersPage = new CheckersPage(driver);
    }

    @Test
    public void playCheckersAndRestart() {
        checkersPage.open();

        String originalPiece = checkersPage.getCellImage(2, 0);

        int[][] moves = {
                {6, 0, 5, 1},
                {2, 2, 3, 3},
                {6, 2, 5, 3},
                {2, 4, 3, 5},
                {6, 4, 5, 5}
        };

        for (int[] move : moves) {
            checkersPage.makeMove(move[0], move[1], move[2], move[3]);
        }

        checkersPage.restartGame();

        String afterRestartPiece = checkersPage.getCellImage(2, 0);
        assertEquals(afterRestartPiece, originalPiece, "Restart didn't reset the board");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
    }
}