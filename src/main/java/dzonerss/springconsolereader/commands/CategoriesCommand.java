package dzonerss.springconsolereader.commands;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
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
public class CategoriesCommand implements ICommand{

    private Multiset<String> myMultiset = HashMultiset.create();

    private RSSService rssService;

    public CategoriesCommand(RSSService rssService) {
        this.rssService = rssService;
    }

    @Override
    public CommandResult execute() {
        try {
            List<FeedData> rssFeedData = rssService.getRssData("http://feeds.dzone.com/home", RssFeeds.class).getRssFeedData();

            Multiset<String> categoryList = getAllCategoryCount(rssFeedData);
            List<String> resultRows = new ArrayList<>();

            for (String category : Multisets.copyHighestCountFirst(categoryList).elementSet()) {
                resultRows.add("[" + category + "] occures: (" + categoryList.count(category) + ") times.");
            }
            return new CommandResult(resultRows);

        } catch (IOException | JAXBException je) {
            return new CommandResult("Unfortunately the dzone feed is not available");
        }
    }

    /**
     * Method for listing out all category with every occurrence
     *
     * @param rssFeedData input list that contains the categories
     * @return Multiset with all category witout order
     */
    public Multiset<String> getAllCategoryCount(List<FeedData> rssFeedData) {
        for (FeedData feedData : rssFeedData) {
            myMultiset.addAll(feedData.getCategoryList());
        }
        return myMultiset;
    }
}
