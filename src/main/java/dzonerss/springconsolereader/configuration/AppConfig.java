package dzonerss.springconsolereader.configuration;

import dzonerss.springconsolereader.commands.CommandFactory;
import dzonerss.springconsolereader.service.OutputWriter;
import dzonerss.springconsolereader.service.RSSService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CommandFactory commandFactory(){
        return new CommandFactory();
    }

    @Bean
    public OutputWriter outputWriter(){
        return new OutputWriter();
    }

    @Bean
    public RSSService rssService(){
        return new RSSService();
    }
}
