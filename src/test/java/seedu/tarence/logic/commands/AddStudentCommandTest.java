package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Application;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.ModuleBuilder;
import seedu.tarence.testutil.StudentBuilder;
import seedu.tarence.testutil.TutorialBuilder;

public class AddStudentCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String VALID_TUT_NAME = "T02";
    public static final Integer VALID_TUT_INDEX = 1;

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    @Test
    public void indexConstructor_nullPerson_throwsNullPointerException() {
        Student validStudent = new StudentBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(validStudent, null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        final String validModCode = "ES1601";
        final String validTutName = "T02";
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        modelStub.addModule(new ModuleBuilder().withModCode(validModCode).build());
        modelStub.addTutorial(new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        modelStub.addTutorialToModule(
                new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        Student validStudent = new StudentBuilder().withModCode(validModCode).withTutName(validTutName).build();

        CommandResult commandResult = new AddStudentCommand(validStudent).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class,
            AddStudentCommand.MESSAGE_DUPLICATE_STUDENT, () -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void execute_studentAcceptedByIndexFormat_addSuccessful() throws Exception {
        final String validModCode = "ES1601";
        final String validTutName = "T02";
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        modelStub.addModule(new ModuleBuilder().withModCode(validModCode).build());
        modelStub.addTutorial(new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        modelStub.addTutorialToModule(
                new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        Student validStudent = new StudentBuilder().withModCode(validModCode).withTutName(validTutName).build();

        CommandResult commandResult = new AddStudentCommand(validStudent, VALID_TUT_INDEX).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_tutorialIndexOutOfBounds_throwsCommandException() {
        final String validModCode = "ES1601";
        final String validTutName = "T02";
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        modelStub.addModule(new ModuleBuilder().withModCode(validModCode).build());
        modelStub.addTutorial(new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        modelStub.addTutorialToModule(
                new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());

        Student bob = new StudentBuilder().withName("Bob").build();
        Integer outOfBoundsTutorialIndex = -1;
        AddStudentCommand addStudentCommand = new AddStudentCommand(bob, outOfBoundsTutorialIndex);
        String tutorialIndexOutOfBoundsMessage =
                String.format(AddStudentCommand.MESSAGE_TUTORIAL_IDX_OUT_OF_BOUNDS, outOfBoundsTutorialIndex);

        assertThrows(CommandException.class,
                tutorialIndexOutOfBoundsMessage, () -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public boolean hasModule(Module module) {
            return false;
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            return false;
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            // todo: Implement test for addTutorial
        }

        @Override
        public void deleteTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasModuleOfCode(ModCode modCode) {
            return false;
        }

        @Override
        public void addTutorialToModule(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudentToTutorial(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
            return false;
        }

        @Override
        public int getNumberOfTutorialsOfName(TutName tutName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Student> studentsAdded = new ArrayList<>();
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            studentsAdded.add(student);
        }

        @Override
        public void addStudentToTutorial(Student student) {
            requireNonNull(student);
            for (int i = 0; i < tutorials.size(); i++) {
                if (tutorials.get(i).getTutName().equals(student.getTutName())) {
                    tutorials.get(i).addStudent(student);
                }
            }
        }

        public void addModule(Module module) {
            requireNonNull(module);
            modules.add(module);
        }

        public void addTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            tutorials.add(tutorial);
        }

        @Override
        public void addTutorialToModule(Tutorial tutorial) {
            requireNonNull(tutorial);
            for (int i = 0; i < modules.size(); i++) {
                if (modules.get(i).getModCode().equals(tutorial.getModCode())) {
                    modules.get(i).addTutorial(tutorial);
                }
            }
        }

        @Override
        public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
            requireAllNonNull(modCode, tutName);
            boolean hasMod = false;
            for (Module module : modules) {
                if (module.getModCode().equals(modCode)) {
                    hasMod = true;
                    break;
                }
            }
            if (!hasMod) {
                return false;
            }
            boolean hasTut = false;
            for (Tutorial tutorial : tutorials) {
                if (tutorial.getTutName().equals(tutName)) {
                    hasTut = true;
                    break;
                }
            }
            return hasTut;
        }

        @Override
        public ReadOnlyApplication getApplication() {
            return new Application();
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return FXCollections.observableArrayList(tutorials);
        }
    }

}
