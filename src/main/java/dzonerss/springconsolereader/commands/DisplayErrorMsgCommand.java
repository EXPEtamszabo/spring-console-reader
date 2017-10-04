package dzonerss.springconsolereader.commands;

import dzonerss.springconsolereader.service.CommandResult;
import org.springframework.stereotype.Component;

@Component
public class DisplayErrorMsgCommand implements ICommand {

    @Override
    public CommandResult execute() {
        return new CommandResult("Invalid command");
    }
}
