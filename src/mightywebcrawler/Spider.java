package mightywebcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author vgordienko
 * 
 * using Set to insure that all visited pages are unique = no duplicates
 * using list to append newly discovered pages to that list
 * 
 */
public class Spider 
{
    private static final int MAX_PAGES_TO_SEARCH = 10; 
    private Set<String> pages_visited = new HashSet<String>();
    private List<String> pages_to_visit = new LinkedList<String>(); 
    
    private String nextUrl()
    {
        String next_url; 

        do
        {
            next_url = this.pages_to_visit.remove(0); 
        }
        while (this.pages_visited.contains(next_url));
        this.pages_visited.add(next_url); 

        return next_url; 
    }   
    
    public void search(String url, String searchWord)
    {
    while (this.pages_visited.size() < MAX_PAGES_TO_SEARCH)
        {
            String current_url; 
            SpiderLeg leg = new SpiderLeg(); 
            if(this.pages_to_visit.isEmpty())
            {
                current_url = url; 
                this.pages_visited.add(url); 
            }
            else 
            {
                current_url = this.nextUrl();
            }
            
            leg.crawl(current_url);
            
            boolean susses = leg.searchForWord(searchWord); 
            
            if (susses)
            {
                System.out.println(String.format("**Success**Word %s found at %s", searchWord, current_url));
                break; 
            }
            this.pages_to_visit.addAll(leg.getLinks()); 
        }
    
    System.out.println(String.format("**Done**Visited %s web page(s)", this.pages_visited.size())); 
    }
}
