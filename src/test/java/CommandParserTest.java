import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import eunAI.command.CommandParser;


public class CommandParserTest {

    @Test
    public void testParseCommand() {
        assertEquals(CommandParser.Command.TODO, CommandParser.parseCommand("todo read book"));
        assertEquals(CommandParser.Command.DEADLINE, CommandParser.parseCommand("deadline return book /by 2024-06-01"));
        assertEquals(CommandParser.Command.EVENT, CommandParser.parseCommand("event meeting /from Monday /to Tuesday"));
        assertEquals(CommandParser.Command.LIST, CommandParser.parseCommand("list"));
        assertEquals(CommandParser.Command.INVALID, CommandParser.parseCommand("random text"));
    }
}

