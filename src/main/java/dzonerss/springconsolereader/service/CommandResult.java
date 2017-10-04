package dzonerss.springconsolereader.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommandResult {

    private final List<String> rows;

    public CommandResult(String row) {
        this(Arrays.asList(row));
    }

    public CommandResult(List<String> rows) {
        this.rows = rows;
    }

    public List<String> getRows() {
        return rows;
    }
}
