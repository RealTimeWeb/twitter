package realtimeweb.twittersearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import realtimeweb.stickyweb.EditableCache;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceNotFoundException;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceParseException;
import realtimeweb.stickyweb.exceptions.StickyWebInternetException;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidPostArguments;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidQueryString;
import realtimeweb.stickyweb.exceptions.StickyWebLoadDataSourceException;
import realtimeweb.stickyweb.exceptions.StickyWebNotInCacheException;
import realtimeweb.twittersearch.domain.Tweet;

public class TwitterSearchTest {

	@Test
	public void testTwitterSearchOnline() {
		TwitterSearch twitterSearch = new TwitterSearch();
        ArrayList<Tweet> tweets = twitterSearch.query("ebola", "recent", 25);
        assertEquals(tweets.size(), 25);
        for (Tweet t : tweets ){
        	 assertNotNull(t.getId_Str());
        	 assertNotNull(t.getCreated_At());
        	 assertNotNull(t.getUser().getName());
        	 assertNotNull(t.getUser().getScreen_Name());
        	 assertNotNull(t.getText());
        	 assertNotNull(t.getFavorited());
        	 assertNotNull(t.getFavorite_Count());
        	 assertNotNull(t.getRetweet_Count());
        	 assertNotNull(t.getEntities().getHashtags());
        	 assertNotNull(t.getEntities().getUrls());
        	 assertNotNull(t.getEntities().getMedia());
        	 assertNotNull(t.getCoordinates());
        	 assertNotNull(t.getPlace());
         }
	}
	
	@Test
	public void testTwitterSearchCache() {
		TwitterSearch twitterSearch = new TwitterSearch();
		EditableCache recording = new EditableCache();
		String[][] requests = {{"ebola","recent","10"},{"virginiatech","recent", "5"}};
        for (String[] request : requests) {
             try {
				recording.addData(twitterSearch.queryRequest(request[0], request[1], Integer.parseInt(request[2])));
			} catch (NumberFormatException e) {
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
		try {
			recording.saveToStream(new FileOutputStream("test-cache.json"));
		} catch (StickyWebDataSourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebDataSourceParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebLoadDataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		TwitterSearch twitterSearchFromCache = new TwitterSearch("test-cache.json");
		ArrayList<Tweet> ebola_tweets = twitterSearchFromCache.query("ebola", "recent", 10);
		ArrayList<Tweet> vt_tweets = twitterSearchFromCache.query("virginiatech","recent", 5);
		assertEquals(ebola_tweets.size(), 10);
		assertEquals(vt_tweets.size(), 5);
	}
}
