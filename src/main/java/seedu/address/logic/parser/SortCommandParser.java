package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.model.attribute.Attribute.MESSAGE_CONSTRAINTS_FOR_NAME;
import static seedu.address.model.attribute.Attribute.PROHIBITED_CHARACTERS;

import java.util.List;

import seedu.address.logic.commands.LexSortCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser for a sort command.
 * Subclasses should provide a method to return a SortCommand given the attribute to sort.
 */
public abstract class SortCommandParser implements Parser<SortCommand> {
    public static final String MESSAGE_ONLY_ONE_PARAMETER =
            "Note that this command accepts exactly ONE attribute as a parameter.";
    public static final String MESSAGE_EMPTY_ATTRIBUTE_NAME =
            "The attribute name cannot be empty!";

    private boolean isContainProhibitedCharacters(String str) {
        return str.chars().anyMatch(c -> PROHIBITED_CHARACTERS.chars().anyMatch(ng -> ng == c));
    }

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);

        List<String> attributes = argMultimap.getAllValues(PREFIX_ATTRIBUTE);

        if (attributes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LexSortCommand.MESSAGE_USAGE));
        }

        if (attributes.size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_ONLY_ONE_PARAMETER + "\n" + LexSortCommand.MESSAGE_USAGE));
        }

        String attributeName = attributes.get(0);

        attributeName = attributeName.trim();

        if (attributeName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_EMPTY_ATTRIBUTE_NAME));
        }

        if (isContainProhibitedCharacters(attributeName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_CONSTRAINTS_FOR_NAME));
        }

        return createSortCommand(attributes.get(0));
    }

    public abstract SortCommand createSortCommand(String attribute);
}
