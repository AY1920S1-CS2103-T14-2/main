package seedu.tarence.model;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.tarence.logic.commands.Command;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.module.UniqueModuleList;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.person.UniquePersonList;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.UniqueTutorialList;

/**
 * Wraps all data at the application level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Application implements ReadOnlyApplication {

    private final UniquePersonList persons;
    private final UniquePersonList students;
    private final UniqueModuleList modules;
    private final UniqueTutorialList tutorials;

    private Command pendingCommand;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        students = new UniquePersonList();
        modules = new UniqueModuleList();
        tutorials = new UniqueTutorialList();
    }

    public Application() {}

    /**
     * Creates an application using the Persons in the {@code toBeCopied}
     */
    public Application(ReadOnlyApplication toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// ============================== list overwrite operations    =================================================

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code persons} must not contain duplicate students.
     */
    public void setStudents(List<Person> students) {
        this.students.setPersons(students);
    }

    /**
     * Replaces the contents of the module list with {@code Module}.
     * {@code Module} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the tutorials list with {@code Tutorial}.
     * {@code Tutorial} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials.setTutorials(tutorials);
    }

    /**
     * Resets the existing data of this {@code Application} with {@code newData}.
     */
    public void resetData(ReadOnlyApplication newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setModules(newData.getModuleList());
        setTutorials(newData.getTutorialList());
    }

    ////=================== person-level operations    =================================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the application.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the application.
     * The person must not already exist in the application.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the application.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code Application}.
     * {@code key} must exist in the application.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Adds a student to their associated tutorial in its associated module.
     */
    public void addStudentToTutorial(Student student) {
        requireNonNull(student);
        Module targetModule = null;
        for (Module module : modules) {
            if (module.getModCode().equals(student.getModCode())) {
                targetModule = module;
                break;
            }
        }
        for (Tutorial tutorial : targetModule.getTutorials()) {
            if (tutorial.getTutName().equals(student.getTutName())) {
                tutorial.addStudent(student);
                break;
            }
        }
    }
    ////=================== student-level operations    ================================================================
    /**
     * Returns true if a student with the same identity as {@code student} exists in the application.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the application.
     * The student must not already exist in the application.
     */
    public void addStudent(Student s) {
        students.add(s);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the application.
     * The person identity of {@code editedStudent} must not be the same as another existing student in the application.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);
        persons.setPerson(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code Application}.
     * {@code key} must exist in the application.
     */
    public void removeStudent(Student key) {
        persons.remove(key);
    }

    ////=================== module-level operations    =================================================================

    /**
     * Adds a module to the application.
     * The module must not already exist in the application.
     */
    public void addModule(Module newModule) {
        requireNonNull(newModule);
        modules.add(newModule);
    }

    /**
     * Returns true if a module with the same identity as {@code module} exists in the application.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Returns true if a module of the given code exists. Used to check whether a tutorial command contains a valid
     * module code.
     */
    public boolean hasModuleOfCode(ModCode modCode) {
        requireNonNull(modCode);
        return modules.getModuleByCode(modCode).isPresent();
    }

    /**
     * Deletes a module from the application. Assumes the module exists in the application.
     */
    public void removeModule(Module module) {
        requireNonNull(module);
        modules.remove(module);
    }

    /**
     * Adds a tutorial to its associated module. Assumes that a module of the given code exists.
     */
    public void addTutorialToModule(Tutorial tutorial) {
        requireNonNull(tutorial);
        Module targetModule = modules.getModuleByCode(tutorial.getModCode()).get();
        targetModule.addTutorial(tutorial);
    }
    ////=================== tutorial-level operations    ==============================================================

    /**
     * Adds a tutorial to the application.
     * The tutorial must not already exist in the application.
     */
    public void addTutorial(Tutorial newTutorial) {
        requireNonNull(newTutorial);
        tutorials.add(newTutorial);
    }

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the application.
     */
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorials.contains(tutorial);
    }

    /**
     * Returns true if a module with a tutorial of the given name exists. Used to check whether a student command
     * contains a valid module code and tutorial name.
     */
    public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
        requireAllNonNull(modCode, tutName);
        Optional<Module> module = modules.getModuleByCode(modCode);
        if (module.isEmpty()) {
            return false;
        }
        boolean hasTut = false;
        for (Tutorial tutorial : module.get().getTutorials()) {
            if (tutorial.getTutName().equals(tutName)) {
                hasTut = true;
                break;
            }
        }
        return hasTut;
    }

    /**
     * Returns number of tutorials with equal names exists in the application.
     */
    public int getNumberOfTutorialsOfName(TutName tutName) {
        requireNonNull(tutName);
        int tutCount = 0;
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutName().equals(tutName)) {
                tutCount++;
            }
        }
        return tutCount;
    }

    /**
     * Deletes a tutorial from the application. Assumes the tutorial exists.
     */
    public void removeTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        tutorials.remove(tutorial);
    }
    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tutorial> getTutorialList() {
        return tutorials.asUnmodifiableObservableList();
    }

    /**
     * Stores a command for later execution, pending user confirmation.
     */
    public void storePendingCommand(Command command) {
        pendingCommand = command;
    }

    /**
     * Removes pending command from application and returns it for execution.
     */
    public Command retrievePendingCommand() {
        requireNonNull(pendingCommand);
        Command command = pendingCommand;
        pendingCommand = null;
        return command;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Application // instanceof handles nulls
                && persons.equals(((Application) other).persons)
                && students.equals(((Application) other).students)
                && modules.equals(((Application) other).modules)
                && tutorials.equals(((Application) other).tutorials));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
