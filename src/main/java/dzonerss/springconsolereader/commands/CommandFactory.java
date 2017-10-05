package dzonerss.springconsolereader.commands;

import dzonerss.springconsolereader.service.RSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    private static final String EXIT = "EXIT";

    public static boolean isExitCommand(String rawCommand) {
        return rawCommand.toUpperCase().equals(EXIT);
    }

    @Autowired
    RSSService rssService;

    public ICommand create(String rawCommand) {
        if (rawCommand == null) {
            return null;
        }

        String[] commandAndPhrase = rawCommand.split(" ", 2);

        String command = commandAndPhrase[0].toUpperCase();

        switch (command) {
            case "ALL":
                return new AllCommand(rssService);
            case "SEARCH":
                return new SearchCommand(commandAndPhrase[1], rssService);
            case "CATEGORIES":
                return new CategoriesCommand(rssService);
            case "DAY":
                return new DayCommand(commandAndPhrase[1],rssService);
            case "DETAIL":
                return new DetailCommand(commandAndPhrase[1], rssService);
            case EXIT:
                return new ExitCommand();
            default:
                return new DisplayErrorMsgCommand();
        }
    }
}
