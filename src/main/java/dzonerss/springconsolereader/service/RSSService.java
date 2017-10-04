package dzonerss.springconsolereader.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;

@Service
public class RSSService {

    public RSSService() {
    }

    /**
     * Method for call the rss feeds page and parse the data
     *
     * @return with the list of rss feeds
     * @throws IOException
     * @throws JAXBException
     */
    public <T> T getRssData(String url, Class<T> rssClass) throws IOException, JAXBException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        //add request header
        request.addHeader("accept", "application/xml");
        HttpResponse response = client.execute(request);

        System.out.println("response Code: " + response.getStatusLine().getStatusCode() + "\n");

        HttpEntity httpEntity = response.getEntity();

        String rssOutput = EntityUtils.toString(httpEntity);

        JAXBContext jaxbContext = JAXBContext.newInstance(rssClass);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        //StringReader is not using any system resource, closing it is not crucial.
        StringReader reader = new StringReader(rssOutput);

        StreamSource xmlSource = new StreamSource(reader);

        T rssFeeds = unmarshaller.unmarshal(xmlSource, rssClass).getValue();

        return rssFeeds;
    }
}
