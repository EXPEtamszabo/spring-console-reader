package dzonerss.springconsolereader.service;

import org.springframework.stereotype.Service;

@Service
public class OutputWriter {

    public void display(CommandResult result) {
        result.getRows().forEach(System.out::println);
    }
}
