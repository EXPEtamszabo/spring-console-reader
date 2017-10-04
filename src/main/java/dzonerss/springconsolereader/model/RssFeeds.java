package dzonerss.springconsolereader.model;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
public class RssFeeds {
    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "item")
    private List<FeedData> rssFeedData;

    public List<FeedData> getRssFeedData() {
        return rssFeedData;
    }

    public void setRssFeedData(List<FeedData> rssFeedData) {
        this.rssFeedData = rssFeedData;
    }
}
