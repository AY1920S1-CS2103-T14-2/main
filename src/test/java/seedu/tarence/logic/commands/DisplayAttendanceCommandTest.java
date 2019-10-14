package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;
import static seedu.tarence.testutil.TypicalModules.getTypicalApplication;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;


/**
 * Contains integration tests (interaction with the Model) and unit tests for DisplayAttendanceCommand.
 */
public class DisplayAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
    private List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();
    private ModCode validModCode = new ModCode(VALID_MODCODE);
    private TutName validTutName = new TutName(VALID_TUTORIAL_NAME);

    @Test
    public void execute_constructor_showsSameSuccessOutput() {
        Tutorial tutorial = lastShownTutorialList.get(INDEX_FIRST_IN_LIST.getZeroBased());
        DisplayAttendanceCommand displayFirstAttendanceCommand =
                new DisplayAttendanceCommand(tutorial.getModCode(), tutorial.getTutName());
        DisplayAttendanceCommand displaySecondAttendanceCommand =
                new DisplayAttendanceCommand(tutorial.getModCode(), tutorial.getTutName());

        try {
            assertEquals(displayFirstAttendanceCommand.execute(model), displaySecondAttendanceCommand.execute(model));
        } catch (CommandException e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void equals() {
        Tutorial tutorial = lastShownTutorialList.get(INDEX_FIRST_IN_LIST.getZeroBased());
        ModCode modCode = tutorial.getModCode();
        TutName tutName = tutorial.getTutName();
        DisplayAttendanceCommand displayFirstAttendanceCommand = new DisplayAttendanceCommand(modCode, tutName);

        // same object -> returns true
        assertTrue(displayFirstAttendanceCommand.equals(displayFirstAttendanceCommand));

        ModCode modCode1 = new ModCode(modCode.toString());
        TutName tutName1 = new TutName(tutName.toString());
        DisplayAttendanceCommand displayFirstAttendanceCommand2 = new DisplayAttendanceCommand(modCode1, tutName1);
        // same values -> returns true
        assertTrue(displayFirstAttendanceCommand.equals(displayFirstAttendanceCommand2));

        // different types -> returns false
        assertFalse(displayFirstAttendanceCommand.equals(1));

        // null -> returns false
        assertFalse(displayFirstAttendanceCommand.equals(null));

        Tutorial tutorial2 = lastShownTutorialList.get(INDEX_SECOND_IN_LIST.getZeroBased());
        ModCode modCode2 = tutorial2.getModCode();
        TutName tutName2 = tutorial2.getTutName();
        DisplayAttendanceCommand displaySecondAttendanceCommand = new DisplayAttendanceCommand(modCode2, tutName2);
        // Different value - returns false
        assertFalse(displayFirstAttendanceCommand.equals(displaySecondAttendanceCommand));
    }

    @Test
    public void execute_differentCommandWord_showSuccess() {
        String validCommand = "displayAttendance";
        String validCommand2 = "DISPLAYATTENDANCE";

        DisplayAttendanceCommand displayFirstAttendanceCommand =
                new DisplayAttendanceCommand(validModCode, validTutName);

        // Correct word
        assertTrue(displayFirstAttendanceCommand.isMatchingCommandWord(validCommand));
        assertTrue(displayFirstAttendanceCommand.isMatchingCommandWord(validCommand2));

        // Incorrect word
        String invalidCommand1 = "DISPLAY";
        String invalidCommand2 = "DisplayAtt";

        assertFalse(displayFirstAttendanceCommand.isMatchingCommandWord(invalidCommand1));
        assertFalse(displayFirstAttendanceCommand.isMatchingCommandWord(invalidCommand2));
    }
}
