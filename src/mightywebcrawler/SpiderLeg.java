package mightywebcrawler;
import java.util.*; 
import java.io.IOException;
import org.jsoup.Connection; 
import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements; 

/**
 *
 * @author vgordienko
 */
public class SpiderLeg 
{
    private List<String> links = new LinkedList<String>(); 
    private Document htmlDocument; //webpage
    private static final String USER_AGENT = "Mozilla/5.0(Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1"; 

    // makes http request based on parsed url
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT); 
            Document htmlDocument = connection.get(); 
            this.htmlDocument = htmlDocument; 
            
            if (connection.response().statusCode() == 200) //ok
            {
            System.out.println("\n**Visiting** received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html"))
            {
            System.out.println("\n**Failure** received something other than html"); 
            return false; 
            }
            
            Elements linksOnPage = htmlDocument.select("a[href]"); 
            System.out.println("Found (" + linksOnPage.size() + ") links");
            
            for(Element link: linksOnPage)
            {
                this.links.add(link.absUrl("href")); 
            }
            return true; 
        }
        catch(IOException ioe)
        {
        // HTTP request not successful
         System.out.println("Request not successful: " + ioe);    
         return false; 
        }
        
    }
    
    //looks for a specific word on the page
    public boolean searchForWord(String word)
    {
      System.out.println("Searching for " + word + "...");
      String bodyText = this.htmlDocument.body().text(); 
      return bodyText.toLowerCase().contains(word.toLowerCase());
    }
    
    //returns a list of all urls on the page
    public List<String> getLinks()
    {
        return this.links;
    }
            
    
}
