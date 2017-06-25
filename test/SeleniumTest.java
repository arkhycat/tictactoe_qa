/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import junit.framework.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SeleniumTest extends TestCase {
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/home/venefica/NetBeansProjects/QALab_3/chromedriver");
        this.driver = new ChromeDriver();
    }

    @Test
    public void testUnfilledForm() throws Exception {
        this.driver.get("localhost:8080/QALab_3");
        assertEquals("Game setup", this.driver.getTitle());
        driver.findElement(By.name("submit")).click();
        assertEquals("http://localhost:8080/QALab_3/", driver.getCurrentUrl());
    }
    
    
    @Test
    public void testFilledForm() throws Exception {
        this.driver.get("localhost:8080/QALab_3");
        driver.findElement(By.name("board_size")).sendKeys("2");
        driver.findElement(By.name("winning_length")).sendKeys("2");
        driver.findElement(By.name("submit")).click();
        assertNotEquals("http://localhost:8080/QALab_3/", driver.getCurrentUrl());
    }
    
    @Test
    public void testBestMove() throws Exception {
        this.driver.get("http://localhost:8080/QALab_3/myservlet?board_size=2&winning_length=2&first_player=O&submit=Submit&new_game=true");
        driver.findElement(By.id("bestmove")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        assertNotEquals("", driver.findElement(By.name("x")).getAttribute("value"));
        assertNotEquals("", driver.findElement(By.name("y")).getAttribute("value"));
        
    }

    @Test
    public void testMakeMove() throws Exception {
        this.driver.get("http://localhost:8080/QALab_3/myservlet?board_size=2&winning_length=2&first_player=O&submit=Submit&new_game=true");
        driver.findElement(By.name("x")).sendKeys(String.valueOf(0));
        driver.findElement(By.name("y")).sendKeys(String.valueOf(0));
        driver.findElement(By.id("makemove")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        assertEquals("", driver.findElement(By.name("x")).getAttribute("value"));
        assertEquals("", driver.findElement(By.name("y")).getAttribute("value"));
    }
    
    @Test
    public void testPlayerChange() throws Exception {
        this.driver.get("http://localhost:8080/QALab_3/myservlet?board_size=2&winning_length=2&first_player=O&submit=Submit&new_game=true");
        driver.findElement(By.name("x")).sendKeys(String.valueOf(0));
        driver.findElement(By.name("y")).sendKeys(String.valueOf(0));
        driver.findElement(By.id("makemove")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        assertTrue(driver.getPageSource().contains("X's"));
    }
    
    @After
    public void tearDown() throws Exception {
        this.driver.quit();
    }
}