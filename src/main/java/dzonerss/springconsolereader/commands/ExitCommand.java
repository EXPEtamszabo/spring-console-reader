package dzonerss.springconsolereader.commands;

import dzonerss.springconsolereader.service.CommandResult;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements ICommand {

    @Override
    public CommandResult execute() {
        return new CommandResult("Bye");
    }
}
