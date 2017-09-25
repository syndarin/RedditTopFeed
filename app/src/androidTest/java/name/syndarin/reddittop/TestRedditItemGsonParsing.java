package name.syndarin.reddittop;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.entity.RedditTopResponse;
import name.syndarin.reddittop.entity.RedditTopResponseData;
import name.syndarin.reddittop.entity.RedditTopResponseItem;

import static org.junit.Assert.*;

/**
 * Created by syndarin on 9/25/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestRedditItemGsonParsing {

    private String rawContent;
    private Gson gson = new Gson();

    @Before
    public void prepareContent() {
        Context context = InstrumentationRegistry.getTargetContext();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("top_sample.json")))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            rawContent = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parsing() {
        RedditTopResponse response = gson.fromJson(rawContent, RedditTopResponse.class);
        assertNotNull(response);
        RedditTopResponseData data = response.getData();
        assertNotNull(data);
        assertEquals("t3_7296dp", data.getAfter());
        assertNotNull(data.getChildren());
        assertTrue(!data.getChildren().isEmpty());
        RedditTopResponseItem item = data.getChildren().get(0);
        assertNotNull(item.getItem());
        RedditItem redditItem = item.getItem();
        assertNotNull(redditItem.getAuthor());
        assertNotNull(redditItem.getTitle());
        assertTrue(redditItem.getCreatedAt() > 0);
        assertNotNull(redditItem.getPreview().getFirstImageUrl());
    }

}
