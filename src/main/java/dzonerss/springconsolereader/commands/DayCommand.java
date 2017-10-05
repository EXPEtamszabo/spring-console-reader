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
public class DayCommand implements ICommand {

    private String phrase;

    static final String HTTPTAGS = "<.*?>|&.*?;";

    private RSSService rssService;

    public DayCommand(String phrase, RSSService rssService) {
        this.phrase = phrase;
        this.rssService = rssService;
    }

    @Override
    public CommandResult execute() {

        try {
            List<FeedData> rssFeedData = rssService.getRssData("http://feeds.dzone.com/home", RssFeeds.class).getRssFeedData();

            List<String> resultRows = searchFeedsByDate(phrase, rssFeedData);

            if (!resultRows.isEmpty()) {
                return new CommandResult(resultRows);
            } else {
                return new CommandResult("Don't get any match! Please try with other phrase.");
            }

        } catch (IOException | JAXBException je) {
            return new CommandResult("Unfortunately the dzone feed is not available");
        } catch (IllegalArgumentException ia) {
            return new CommandResult("Input don't match with the required form.");
        }
    }

    /**
     * Method for search with publication date in the feeds data
     *
     * @param date        searching criteria
     * @param rssFeedData input list that contains the rss feeds response data
     * @return with list titles of the feeds where the publication date match
     */
    public List<String> searchFeedsByDate(String date, List<FeedData> rssFeedData) {
        if (date.matches("([0-9]{4}_[0-9]{2}_[0-9]{2})")) {
            List<String> titles = new ArrayList<>();

            for (FeedData feedData : rssFeedData) {
                if (feedData.convertDate().contains(date)) {
                    titles.add(feedData.getTitle().replaceAll(HTTPTAGS, ""));
                }
            }

            return titles;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
