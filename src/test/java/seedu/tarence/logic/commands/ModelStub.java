package seedu.tarence.logic.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.logic.parser.PartialInput;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.NameContainsKeywordsPredicate;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * A default model stub that has all of the methods failing.
 */
class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getApplicationFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setApplicationFilePath(Path applicationFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setApplication(ReadOnlyApplication application) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyApplication getApplication() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(NameContainsKeywordsPredicate predicate) {

        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student) {
        return false;
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudentInTutorialAndModule(Name studName, TutName tutName, ModCode modCode) {
        return false;
    }

    @Override
    public boolean hasModule(Module module) {
        // TODO: Implement test for hasModule
        return false;
    }

    @Override
    public void addModule(Module module) {
        // TODO: Implement test for addModule
    }

    @Override
    public void deleteModule(Module module) {}

    @Override
    public void deleteTutorialsFromModule(Module module) {}

    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        // TODO: Implement test for hasTutorial
        return false;
    }

    @Override
    public void addTutorial(Tutorial tutorial) {
        // TODO: Implement test for addTutorial
    }

    @Override
    public void deleteTutorial(Tutorial tutorial) {
        // TODO: Implement test for deleteTutorial
    }

    @Override
    public void deleteStudentsFromTutorial(Tutorial tutorial) {}

    @Override
    public boolean hasModuleOfCode(ModCode modCode) {
        // TODO: implement
        return false;
    }

    @Override
    public void addTutorialToModule(Tutorial tutorial) {
        // TODO: implement
    }

    @Override
    public void addStudentToTutorial(Student student) {
        // TODO: implement
    }

    @Override
    public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
        // TODO: implement
        return false;
    }

    @Override
    public int getNumberOfTutorialsOfName(TutName tutName) {
        return 0;
    }

    @Override
    public void setAttendance(Tutorial tutorial, Week week, Student student) {
        // TODO: implement
    }

    @Override
    public void storePendingCommand(Command command) {};

    @Override
    public Command getPendingCommand() {
        return null;
    }

    @Override
    public Command peekPendingCommand() {
        return null;
    }

    @Override
    public boolean hasPendingCommand() {
        return false;
    }

    @Override
    public void storeSuggestedCommands(List<Command> l, String s) {};

    @Override
    public List<Command> getSuggestedCommands() {
        return new ArrayList<Command>();
    }

    @Override
    public String getSuggestedCorrections() {
        return "";
    }

    @Override
    public void deleteSuggestedCommands() {};

    @Override
    public void storeSuggestedCompletions(PartialInput partialInput) {}

    @Override
    public PartialInput getSuggestedCompletions() {
        return null;
    }

    @Override
    public void deleteSuggestedCompletions() {}

    @Override
    public boolean hasSuggestedCompletions() {
        return false;
    }

    @Override
    public void setInputChangedToTrue() {}

    @Override
    public void setInputChangedToFalse() {}

    @Override
    public boolean hasInputChanged() {
        return false;
    }
}
