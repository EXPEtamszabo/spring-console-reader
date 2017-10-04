package dzonerss.springconsolereader.model;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@XmlAccessorType(XmlAccessType.FIELD)
@Component
public class FeedData {
    private String title;
    private String link;
    private String description;

    @XmlElement(name = "category")
    private List<String> categoryList;

    private String pubDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Method for converting the feeds response date for matching with the search criteria format
     *
     * @return with the formatted publication date
     */
    public String convertDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd");

        try {
            Date date = dateFormat.parse(pubDate);
            pubDate = dateFormat2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return pubDate;
    }
}
