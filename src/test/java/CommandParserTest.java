import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import eunai.command.CommandParser;


public class CommandParserTest {

    @Test
    public void testParseCommand() {
        assertEquals(CommandParser.Command.TODO, CommandParser.parseCommand("todo read book"));
        assertEquals(CommandParser.Command.DEADLINE, CommandParser.parseCommand("deadline return book /by 2024-06-01"));
        assertEquals(CommandParser.Command.EVENT, CommandParser.parseCommand("event meeting /from Monday /to Tuesday"));
        assertEquals(CommandParser.Command.LIST, CommandParser.parseCommand("list"));
        assertEquals(CommandParser.Command.INVALID, CommandParser.parseCommand("random text"));
    }

    @Test
    public void testParseCommandEdgeCases() {
        assertEquals(CommandParser.Command.INVALID, CommandParser.parseCommand("")); // empty string
        assertEquals(CommandParser.Command.INVALID, CommandParser.parseCommand("   ")); // spaces only
        assertEquals(CommandParser.Command.TODO, CommandParser.parseCommand("todo")); // no description
        assertEquals(CommandParser.Command.LIST, CommandParser.parseCommand("list ")); // trailing space
    }
}

