package seedu.tarence.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.commons.util.StringUtil;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String matricNum} into an {@code MatricNum}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code matricNum} is invalid.
     */
    public static MatricNum parseMatricNum(String matricNum) throws ParseException {
        requireNonNull(matricNum);
        String trimmedMatricNum = matricNum.trim();
        if (!MatricNum.isValidMatricNum(trimmedMatricNum)) {
            throw new ParseException(MatricNum.MESSAGE_CONSTRAINTS);
        }
        return new MatricNum(trimmedMatricNum);
    }

    /**
     * Parses a {@code String nusnetId} into an {@code NusnetId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nusnetId} is invalid.
     */
    public static NusnetId parseNusnetId(String nusnetId) throws ParseException {
        requireNonNull(nusnetId);
        String trimmedNusnetId = nusnetId.trim();
        if (!NusnetId.isValidNusnetId(trimmedNusnetId)) {
            throw new ParseException(MatricNum.MESSAGE_CONSTRAINTS);
        }
        return new NusnetId(trimmedNusnetId);
    }

    public static ModCode parseModCode(String modCode) throws ParseException {
        requireNonNull(modCode);
        String trimmedModCode = modCode.trim();
        if (!ModCode.isValidModCode(trimmedModCode)) {
            throw new ParseException(ModCode.MESSAGE_CONSTRAINTS);
        }
        return new ModCode(trimmedModCode);
    }

    public static TutName parseTutorialName(String tutorialName) throws ParseException {
        requireNonNull(tutorialName);
        String trimmedTutorialName = tutorialName.trim();
        if (!TutName.isValidTutName(trimmedTutorialName)) {
            throw new ParseException(TutName.MESSAGE_CONSTRAINTS);
        }
        return new TutName(trimmedTutorialName);
    }

    /**
     * Parses a {@code String tutorialDay} into an {@code DayOfWeek}.
     * Leading and trailing whitespaces will be trimmed.
     * Accepts both normal spelling eg MONDAY, monday and short forms ie MON, mon
     *
     * @throws ParseException if the given {@code tutorialDay} is invalid.
     */
    public static DayOfWeek parseDayOfWeek(String tutorialDay) throws ParseException {
        requireNonNull(tutorialDay);
        String trimmedTutorialDay = tutorialDay.trim().toUpperCase();

        // Converts short-form days to normal-form.
        String[] shortFormDays = new String[]{"MON", "TUES", "WED", "THURS", "FRI", "SAT", "SUN"};
        String[] normalFormDays = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY",
                "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        for (int i = 0; i < shortFormDays.length; i++) {
            if (trimmedTutorialDay.equals(shortFormDays[i])) {
                trimmedTutorialDay = normalFormDays[i];
            }
        }

        try {
            return DayOfWeek.valueOf(trimmedTutorialDay);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid day entered");
        }
    }

    public static LocalTime parseLocalTime(String localTime) throws ParseException {
        requireNonNull(localTime);
        if (localTime.length() != 4) {
            throw new ParseException("Time entered should be in 24HR format eg 0900");
        }

        // Converts a string from '1200' to '12:00:00'.
        localTime = localTime.substring(0,2) + ":" + localTime.substring(2,4) + ":00";

        return LocalTime.parse(localTime, DateTimeFormatter.ISO_TIME);
    }

    public static ArrayList<Integer> parseWeeks(String weeks) throws ParseException {
        requireNonNull(weeks);
        ArrayList<Integer> listOfWeeks = new ArrayList<Integer>();
        String[] weekNumbers = weeks.split(",");

        try {
            for (String weekNumber : weekNumbers) {
                listOfWeeks.add(Integer.parseInt(weekNumber));

            }
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid week numbers entered. Should contain only numbers");
        }
        return listOfWeeks;
    }

    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        try {
            Integer minutes = Integer.parseInt(duration);
            return Duration.ofMinutes(minutes);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid duration entered. Duration should contain only numbers");
        }
    }

}
