package name.syndarin.reddittop.repository;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.entity.RedditTopResponse;
import name.syndarin.reddittop.entity.RedditTopResponseData;
import name.syndarin.reddittop.entity.RedditTopResponseItem;
import name.syndarin.reddittop.reddit.RedditClient;

/**
 * Created by syndarin on 9/25/17.
 */

public class RedditTopRepository {

    private static final int COUNT = 10;

    private RedditClient redditClient;

    private String afterParam;

    private List<RedditItem> redditItemsCache;

    private Gson gson;

    public RedditTopRepository(RedditClient redditClient, Gson gson) {
        this.redditClient = redditClient;
        this.gson = gson;
        this.redditItemsCache = new ArrayList<>();
    }

    public Single<List<RedditItem>> reloadItems() {
        if (redditItemsCache.isEmpty()) {
            return requestRedditTop();
        } else {
            return Single.just(redditItemsCache);
        }
    }

    public Single<List<RedditItem>> loadNextChunk() {
        return requestRedditTop();
    }

    private Single<List<RedditItem>> requestRedditTop() {
        return redditClient.loadTop(afterParam, COUNT)
                .map(rawResponse -> gson.fromJson(rawResponse, RedditTopResponse.class))
                .map(RedditTopResponse::getData)
                .map(data -> {
                    afterParam = data.getAfter();
                    return data.getChildren();
                })
                .flatMapObservable(Observable::fromIterable)
                .map(RedditTopResponseItem::getItem)
                .toList()
                .flatMap(redditItems -> {
                    redditItemsCache.addAll(redditItems);
                    return Single.just(redditItems);
                });
    }
}
