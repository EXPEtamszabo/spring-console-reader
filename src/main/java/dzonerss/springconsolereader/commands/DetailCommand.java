package dzonerss.springconsolereader.commands;

import dzonerss.springconsolereader.model.FeedData;
import dzonerss.springconsolereader.model.RssFeeds;
import dzonerss.springconsolereader.service.CommandResult;
import dzonerss.springconsolereader.service.RSSService;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DetailCommand implements ICommand {
    private String phrase;

    private RSSService rssService;

    private static final String HTTPTAGS = "<.*?>|&.*?;";

    public DetailCommand(String phrase, RSSService rssService) {
        this.phrase = phrase;
        this.rssService = rssService;
    }

    @Override
    public CommandResult execute() {
        try {

            List<FeedData> rssFeedData = rssService.getRssData("http://feeds.dzone.com/home", RssFeeds.class).getRssFeedData();

            List<FeedData> detailList = searchFeedsPartialMatch(phrase, rssFeedData);

            if (detailList.isEmpty()) {
                return new CommandResult("Don't get any match! Please try with other phrase.");
            }

            List<String> resultRows = new ArrayList<>();

            for (FeedData feedData : detailList) {
                resultRows.add(feedData.getTitle().replaceAll(HTTPTAGS, ""));
                resultRows.add(feedData.getPubDate());
                resultRows.add(feedData.getLink());
                resultRows.add(feedData.getDescription().replaceAll(HTTPTAGS, "") + "\n");
            }

            return new CommandResult(resultRows);

        } catch (IOException | JAXBException je) {
            return new CommandResult("Unfortunately the dzone feed is not available");
        }
    }

    /**
     * Method for search in title with partial match
     *
     * @param phrase      string what we want to find in the list
     * @param rssFeedData input list that contains the rss feeds response data
     * @return list of the feed data what contains the phrase or with an empty list if dont find match
     */
    public List<FeedData> searchFeedsPartialMatch(String phrase, List<FeedData> rssFeedData) {
        List<FeedData> feedDataList = new ArrayList<>();
        for (FeedData feedData : rssFeedData) {
            if (feedData.getTitle().toLowerCase().contains(phrase.toLowerCase())) {
                feedDataList.add(feedData);
            }
        }
        return feedDataList;
    }
}
