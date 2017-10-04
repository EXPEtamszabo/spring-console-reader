package dzonerss.springconsolereader;

import dzonerss.springconsolereader.commands.CommandFactory;
import dzonerss.springconsolereader.commands.ICommand;
import dzonerss.springconsolereader.configuration.AppConfig;
import dzonerss.springconsolereader.service.CommandResult;
import dzonerss.springconsolereader.service.OutputWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class SpringConsoleReaderApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        CommandFactory commandFactory = ctx.getBean(CommandFactory.class);
        OutputWriter outputWriter = ctx.getBean(OutputWriter.class);

        Scanner sc = new Scanner(System.in);
        String consoleInput = "";
        while (!CommandFactory.isExitCommand(consoleInput)) {
            consoleInput = sc.nextLine();

            ICommand command = commandFactory.create(consoleInput);

            CommandResult result = command.execute();

            outputWriter.display(result);
        }
    }
}
