package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Displays the selected tutorial attendance
 */
public class DisplayAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "displayAttendance";

    public static final String MESSAGE_SUCCESS = "Attendance is displayed!";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase()};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the tutorial attendance identified by the tutorial name and module code of the tutorial.\n"
            + "Full format: \n"
            + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME "
            + PREFIX_MODULE + "MODULE_CODE \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL_NAME + "Lab 02 " + PREFIX_MODULE + "CS2040 \n"
            + "Shortcut format: \n"
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL_INDEX + "1";


    private static final ModCode DEFAULT_MOD_CODE = new ModCode("MC1010");
    private static final TutName DEFAULT_TUT_NAME = new TutName("notARealTutorial");
    private static final Index DEFAULT_INDEX = Index.fromOneBased(1);

    private ModCode modCode;
    private TutName tutName;
    private Index index;

    /**
     * Default Constructor with module code and tutorial name provided
     */
    public DisplayAttendanceCommand(ModCode modCode, TutName tutName) {
        this.modCode = modCode;
        this.tutName = tutName;
        this.index = DEFAULT_INDEX;
    }

    /**
     * Constructor based on shortcut index format
     */
    public DisplayAttendanceCommand(Index index) {
        this.index = index;
        this.modCode = DEFAULT_MOD_CODE;
        this.tutName = DEFAULT_TUT_NAME;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();

        if (tutName.equals(DEFAULT_TUT_NAME)) {
            Tutorial tutorial = lastShownTutorialList.get(index.getZeroBased());
            return new CommandResult(String.format(MESSAGE_SUCCESS, tutorial), tutorial);
        }

        Tutorial tutorialToDisplay = getTutorial(lastShownTutorialList);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialToDisplay), tutorialToDisplay);
    }

    /**
     * Retrieves tutorial based on module code and tutorial name provided by user
     * @throws CommandException if no such module code or tutorial name is found
     */
    private Tutorial getTutorial(List<Tutorial> lastShownTutorialList) throws CommandException {
        for (Tutorial tutorial : lastShownTutorialList) {
            if ((tutorial.getTutName().equals(tutName)) && (tutorial.getModCode().equals(modCode))) {
                return tutorial;
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
    }

    @Override
    public boolean needsInput() {
        return false;
    }

    @Override
    public boolean needsCommand(Command command) {
        return false;
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof DisplayAttendanceCommand // instanceof handles nulls
                && (tutName.equals(((DisplayAttendanceCommand) other).tutName))
                && (modCode.equals(((DisplayAttendanceCommand) other).modCode))
                && (index.equals(((DisplayAttendanceCommand) other).index)))); // state check
    }
}
