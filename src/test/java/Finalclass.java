import com.opencsv.CSVWriter;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Finalclass extends Base {
static WebDriver driver;
static int num;
    List<String>hrefs = new ArrayList<>();
    static List<String>wikilinks = new ArrayList<String>();
    //Get the total count and unique count of the links
    static int total_count = wikilinks.size();
    static Set uniqueValues = new HashSet(total_count);
    static int unique_count = uniqueValues.size();



    public static void main(String[] args)
    {
        //Accepts a Wikipedia link - return/throw an error if the link is not a valid wiki link
        try {
            driver.get(url);

        } catch (Exception e) {
            System.out.println("The Link is not valid");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Input a valid integer between 1 and 10");
        num = sc.nextInt();
        if (!(num >= 1 && num <= 20)) {
            System.out.println("Not a valid choice");
        }
        Finalclass finalclass = new Finalclass();

        for (String wikilink : wikilinks) {
            wikilinks = finalclass.ScrapeWiki(wikilink, num);

        }


    }
    public void jsonwrite() {



// Write the results to a JSON file
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("wiki_links",wikilinks);
        jsonObject.put("Total Count", total_count);
        jsonObject.put("Unique Count", unique_count);
        try {
            FileWriter file = new FileWriter("C:\\Users\\rkum\\IdeaProjects\\WikiProject\\src\\main\\resources\\Link.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   // Write the results to a CSV file
   public static void writeDataLineByLine(String filePath)
   {
       // first create file object for file placed at location
       String filepath="C:\\Users\\rkum\\IdeaProjects\\WikiProject\\src\\main\\resources\\Link.csv";
       // specified by filepath
       File file = new File(filePath);
       try {
           // create FileWriter object with file as parameter
           FileWriter outputfile = new FileWriter(file);

           // create CSVWriter object filewriter object as parameter
           CSVWriter writer = new CSVWriter(outputfile);

           // adding header to csv
           String[] header = { "wiki_links", "Total Count", "Unique Count" };
           writer.writeNext(header);
           List<String>datalines=new ArrayList<>();
           // add data to csv
           datalines.add(wikilinks.toString());

           datalines.add(Integer.toString(total_count));
           datalines.add(Integer.toString(unique_count));
           // closing writer connection
           writer.close();
       }
       catch (IOException e) {
           e.printStackTrace();
       }
   }
    public List<String>ScrapeWiki(String url,int number) {

        //Initialize a list to store the wiki links
        List<WebElement> LinkList = driver.findElements(By.tagName("a"));
        for (WebElement linklist : LinkList) {

            hrefs.add(linklist.getAttribute("href"));
        }
        //Append the wiki links to the list
        for (String href : hrefs) {
            wikilinks.add("https://en.wikipedia.org" + href);
        }
        if (num == 0) {
            return wikilinks;
        } else {
            for (String href : hrefs) {
              List<String>newList = ScrapeWiki("https://en.wikipedia.org" + href, num - 1);
           wikilinks.addAll(newList);
                return wikilinks;
                     }

        }
        return wikilinks;
    }

}



