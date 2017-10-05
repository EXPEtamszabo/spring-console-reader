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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SearchCommand implements ICommand {

    private String phrase;

    private RSSService rssService;

    static final String HTTPTAGS = "<.*?>|&.*?;";

    public SearchCommand(String phrase, RSSService rssService) {
        this.phrase = phrase;
        this.rssService = rssService;
    }

    @Override
    public CommandResult execute() {
        try {
            List<FeedData> rssFeedData = rssService.getRssData("http://feeds.dzone.com/home", RssFeeds.class).getRssFeedData();

            List<FeedData> titleList = searchFeeds(phrase, rssFeedData);

            if (titleList.isEmpty()) {
                return new CommandResult("Don't get any match! Please try with other phrase.");
            }

            List<String> resultRows = titleList.stream().map(FeedData::getTitle).map(title -> title.replaceAll(HTTPTAGS, "")).collect(Collectors.toList());
            return new CommandResult(resultRows);


        } catch (IOException | JAXBException je) {
            return new CommandResult("Unfortunately the dzone feed is not available");
        }
    }

    /**
     * Method for searc phrase in title with exact match
     *
     * @param phrase      string what we want to find in the list
     * @param rssFeedData input list that contains the rss feeds response data
     * @return list of the feed data what contains the phrase or with an empty list if dont find match
     */
    public List<FeedData> searchFeeds(String phrase, List<FeedData> rssFeedData) {
        String pattern = "\\b" + phrase + "\\b";
        Pattern p = Pattern.compile(pattern);
        List<FeedData> feedDataList = new ArrayList<>();
        for (FeedData feedData : rssFeedData) {
            if (p.matcher(feedData.getTitle()).find()) {
                feedDataList.add(feedData);
            }
        }
        return feedDataList;
    }
}
