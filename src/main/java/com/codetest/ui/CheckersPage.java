package com.codetest.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class CheckersPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://www.gamesforthebrain.com/game/checkers/");
    }

    public boolean isMovePromptDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).getText().contains("piece to move.");
    }

    public void makeMove(int fromX, int fromY, int toX, int toY) {
        String fromSpace = String.format("space%d%d", fromX, fromY);
        String toSpace = String.format("space%d%d", toX, toY);

        System.out.println("Moving from " + fromSpace + " to " + toSpace);

        WebElement fromCell = wait.until(ExpectedConditions.elementToBeClickable(By.name(fromSpace)));
        fromCell.click();

        WebElement toCell = wait.until(ExpectedConditions.elementToBeClickable(By.name(toSpace)));
        toCell.click();
    }

    public void restartGame() {
        driver.findElement(By.linkText("Restart...")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"), "Select an orange piece to move."));
    }

    public String getCellImage(int row, int col) {
        String spaceName = String.format("space%d%d", col, row);
        WebElement cell = driver.findElement(By.name(spaceName));
        return cell.getAttribute("src");
    }
}