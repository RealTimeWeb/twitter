package realtimeweb.twittersearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import realtimeweb.twittersearch.domain.*;
import realtimeweb.stickyweb.EditableCache;
import realtimeweb.stickyweb.StickyWeb;
import realtimeweb.stickyweb.StickyWebRequest;
import realtimeweb.stickyweb.StickyWebResponse;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceNotFoundException;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceParseException;
import realtimeweb.stickyweb.exceptions.StickyWebInternetException;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidPostArguments;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidQueryString;
import realtimeweb.stickyweb.exceptions.StickyWebJsonResponseParseException;
import realtimeweb.stickyweb.exceptions.StickyWebLoadDataSourceException;
import realtimeweb.stickyweb.exceptions.StickyWebNotInCacheException;

/**
 * Get Information about tweets
 */
public class TwitterSearch {
    private StickyWeb connection;
	private boolean online;
    
    public static void main(String[] args) {
        TwitterSearch twitterSearch = new TwitterSearch();
        ArrayList<Tweet> tweets = twitterSearch.query("ebola", "recent", 5);
        for (Tweet t : tweets ){
     	   System.out.println("---------live--------------");
             System.out.format("id:%s\n",t.getId_Str());
             System.out.format("created at:%s\n",t.getCreated_At());
             System.out.format("user: %s\n",t.getUser().getName());
             System.out.format("text: %s\n",t.getText());
             System.out.format("hashtags: %s\n",t.getEntities().getHashtags());
             System.out.format("urls: %s\n",t.getEntities().getUrls());
             System.out.format("media: %s\n",t.getEntities().getMedia());
             System.out.format("retweets: %s\n",t.getRetweet_Count());
             System.out.format("coordinates: %s\n",t.getCoordinates());
        }
       
       
        //Test load data from cache
        twitterSearch = new TwitterSearch("cache.json");
        tweets = twitterSearch.query("virginiatech", "popular", 5);
        for (Tweet t : tweets ){
     	   System.out.println("---------from cache--------------");
             System.out.format("id:%s\n",t.getId_Str());
             System.out.format("created at:%s\n",t.getCreated_At());
             System.out.format("user: %s\n",t.getUser().getName());
             System.out.format("text: %s\n",t.getText());
             System.out.format("hashtags: %s\n",t.getEntities().getHashtags());
             System.out.format("urls: %s\n",t.getEntities().getUrls());
             System.out.format("media: %s\n",t.getEntities().getMedia());
             System.out.format("retweets: %s\n",t.getRetweet_Count());
             System.out.format("coordinates: %s\n",t.getCoordinates());
        }
        
        // The following pre-generated code demonstrates how you can
		// use StickyWeb's EditableCache to create data files.
		try {
            // First, you create a new EditableCache, possibly passing in an FileInputStream to an existing cache
			EditableCache recording = new EditableCache();
			 String[][] requests = {{"ebola","recent","5"},{"virginiatech","popular", "5"}};
             for (String[] request : requests) {
                  System.out.println("keyword:"+request[0]+" type:"+request[1]+" count:"+request[2]);
                  recording.addData(twitterSearch.queryRequest(request[0], request[1], Integer.parseInt(request[2])));              
             }
            // You can add a Request object directly to the cache.
			// recording.addData(twitterSearch.queryRequest(...));
            // Then you can save the expanded cache, possibly over the original
			recording.saveToStream(new FileOutputStream("cache.json"));
		} catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("The given FileStream was not able to be found.");
		} catch (StickyWebDataSourceParseException e) {
			System.err.println("The given FileStream could not be parsed; possibly the structure is incorrect.");
		} catch (StickyWebLoadDataSourceException e) {
			System.err.println("The given data source could not be loaded.");
		} catch (FileNotFoundException e) {
			System.err.println("The given cache.json file was not found, or could not be opened.");
		}
        // ** End of how to use the EditableCache
 catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebNotInCacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebInternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebInvalidQueryString e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebInvalidPostArguments e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
     * Create a new, online connection to the service
     */
	public TwitterSearch() {
        this.online = true;
        String consumerKey = "" ;
        String consumerSecret ="" ;
        String token = "" ;
        String tokenSecret = "" ;
        try {
             this.connection = new StickyWeb( null);
             this.connection .setAuthentication(consumerKey , consumerSecret , token , tokenSecret );
		} catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("The given datastream could not be loaded.");
		} catch (StickyWebDataSourceParseException e) {
			System.err.println("The given datastream could not be parsed");
		} catch (StickyWebLoadDataSourceException e) {
			System.err.println("The given data source could not be loaded");
		}
	}
	
    /**
     * Create a new, offline connection to the service.
     * @param cache The filename of the cache to be used.
     */
	public TwitterSearch(String cache) {
        // TODO: You might consider putting the cache directly into the jar file,
        // and not even exposing filenames!
        try {
            this.online = false;
            this.connection = new StickyWeb(new FileInputStream(cache));
        } catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("The given data source could not be found.");
            System.exit(1);
		} catch (StickyWebDataSourceParseException e) {
			System.err.println("Could not read the data source. Perhaps its format is incorrect?");
            System.exit(1);
		} catch (StickyWebLoadDataSourceException e) {
			System.err.println("The given data source could not be read.");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println("The given cache file could not be found. Make sure it is in the right folder.");
			System.exit(1);
		}
	}
    
    
    /**
     * These are the functions exposed to the student programmer.
     *
     * This version of the function meant for instructors to capture a
     * StickyWebRequest object which can be put into an EditableCache and then
     * stored to a "cache.json" file.
     * 
     * @param keyword query term
     * @return a StickyWebRequest
     * @param type Example Values: mixed, recent, popular
     * @return a StickyWebRequest
     * @param count number of tweet results
     * @return a StickyWebRequest
     */
    private StickyWebRequest queryRequest(String keyword, String type, Integer count) {
        try {
            /*
            * Perform any user parameter validation here. E.g.,
            * if the first argument can't be zero, or they give an empty string.
            */
            
            // Build up query string
            final String url = String.format("https://api.twitter.com/1.1/search/tweets.json");
            
            // Build up the query arguments that will be sent to the server
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("q", String.valueOf(keyword));
            parameters.put("result_type", String.valueOf(type));
            parameters.put("count", String.valueOf(count));
            
            // Build up the list of actual arguments that should be used to
            // create the local cache hash key
            ArrayList<String> indexList = new ArrayList<String>();
            indexList.add("q");
            indexList.add("result_type");
            indexList.add("count");
            
            
            // Build and return the connection object.
            return connection.get(url, parameters)
                            .setOnline(online)
                            .setIndexes(indexList);
        
        } catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("Could not find the data source.");
		}
        return null;
    }
    
    /**
     * These are the functions exposed to the student programmer.
    
     * @param keyword query term
     * @param type Example Values: mixed, recent, popular
     * @param count number of tweet results
     * @return a Tweet[]
     */
	public ArrayList<Tweet> query(String keyword, String type, Integer count) {
        
        // Again, comments hidden in source.
        try {
			StickyWebRequest request =  queryRequest(keyword, type, count);
            
            ArrayList<Tweet> result = new ArrayList<Tweet>();
            StickyWebResponse response = request.execute();
            // TODO: Validate the output here using response.isNull, response.asText, etc.
            if (response.isNull())
                return result;
            Iterator<Object> resultIter = ((ArrayList<Object>) ((Map<String, Object>) response.asJSON()).get("statuses")).iterator();
            while (resultIter.hasNext()) {
                result.add(new Tweet((Map<String, Object>)resultIter.next()));
            }
            return result;
		} catch (StickyWebNotInCacheException e) {
			System.err.println("There is no query in the cache for the given inputs. Perhaps something was mispelled?");
		} catch (StickyWebInternetException e) {
			System.err.println("Could not connect to the web service. It might be your internet connection, or a problem with the web service.");
		} catch (StickyWebInvalidQueryString e) {
			System.err.println("The given arguments were invalid, and could not be turned into a query.");
		} catch (StickyWebInvalidPostArguments e) {
			System.err.println("The given arguments were invalid, and could not be turned into a query.");
        
        } catch (StickyWebJsonResponseParseException e) {
            System.err.println("The response from the server couldn't be understood.");
        
		}
		return null;
	}
    
}