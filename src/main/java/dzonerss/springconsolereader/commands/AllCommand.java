package dzonerss.springconsolereader.commands;

import dzonerss.springconsolereader.model.FeedData;
import dzonerss.springconsolereader.model.RssFeeds;
import dzonerss.springconsolereader.service.CommandResult;
import dzonerss.springconsolereader.service.RSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllCommand implements ICommand {

    private static final String HTTPTAGS = "<.*?>|&.*?;";

    @Autowired
    private RSSService rssService;

    public AllCommand(RSSService rssService) {
        this.rssService = rssService;
    }

    @Override
    public CommandResult execute() {
        try {
            List<FeedData> rssFeedData = rssService.getRssData("http://feeds.dzone.com/home", RssFeeds.class).getRssFeedData();

            List<String> titles = rssFeedData.stream().map(FeedData::getTitle).map(title -> title.replaceAll(HTTPTAGS, "")).collect(Collectors.toList());

            return new CommandResult(titles);

        } catch (IOException | JAXBException je) {
            return new CommandResult("Unfortunately the dzone feed is not available");
        }
    }
}


