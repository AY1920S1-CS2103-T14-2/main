package seedu.tarence.model.module;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.tarence.model.tutorial.Tutorial;

/**
 * Represents a Module.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    protected final ModCode modCode;
    protected List<Tutorial> tutorials;

    /**
     * Every field must be present and not null.
     */
    public Module(ModCode modCode, List<Tutorial> tutorials) {
        requireAllNonNull(modCode, tutorials);
        this.modCode = modCode;
        this.tutorials = tutorials;
    }

    public ModCode getModCode() {
        return modCode;
    }

    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    /**
     * Returns true if both modules have the same module code.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModCode().equals(getModCode())
                && otherModule.getTutorials().equals(getTutorials());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(modCode, tutorials);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModCode())
                .append(" Tutorials: ")
                .append(getTutorials());
        return builder.toString();
    }


    /**
     * Returns true if both modules have the same code.
     *
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }
        return otherModule != null
                && otherModule.getModCode().equals(getModCode());
    }

}
