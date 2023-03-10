package com.DS.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {
    //Creating a private constructor, so we are closing access to the object of this class from outside of any class
    private Driver(){}

    //making our 'driver' instance private, so that it is not reachable from outside of any class
    // We make static because we want it to run before anything else,
    // also we will use it in static method


    private static InheritableThreadLocal<WebDriver> driverPool= new InheritableThreadLocal<>();

   // private static WebDriver driver;


    // Create re-usable method which will return same driver instance when we call it

    public static WebDriver getDriver(){

        if(driverPool.get()== null){ // if driver/ browser was never opened

            String browserType=ConfigurationReader.getProperty("browser");

            switch (browserType){

                /*
                Depending on the browserType our switch statement will determine
                to open specific type of browser/driver
                 */

                case "chrome":
                    WebDriverManager.chromedriver().setup();
                 driverPool.set(new ChromeDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
                    break;



            }





        }
        //Same driver instance will be returned every time we call Driver.getDriver() method

        return driverPool.get();



    }

    public static void closeDriver(){
        if(driverPool.get() != null)
            driverPool.get().quit(); // this line will session value will no tbe null
       driverPool.remove();

    }






}

