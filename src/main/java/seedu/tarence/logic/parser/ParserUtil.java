package seedu.tarence.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.ArgumentPatterns.PATTERN_WEEKRANGE;
import static seedu.tarence.logic.parser.ParserMessages.MESSAGE_INVALID_WEEK_RANGE;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.commons.util.StringUtil;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.tutorial.TutName;

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
     * Parses a {@code String nusnetId} into a {@code NusnetId}.
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

    /**
     * Parses a {@code String modCode} into a {@code ModCode}.
     *
     * @param modCode User string.
     * @return ModCode object.
     * @throws ParseException if the given {@code modCode} doesn't match the regex.
     */
    public static ModCode parseModCode(String modCode) throws ParseException {
        requireNonNull(modCode);
        String trimmedModCode = modCode.trim();
        if (!ModCode.isValidModCode(trimmedModCode)) {
            throw new ParseException(ModCode.MESSAGE_CONSTRAINTS);
        }
        return new ModCode(trimmedModCode);
    }

    /**
     * Parses a {@code String modCode} into an {@code ModCode}.
     *
     * @param tutorialName User string.
     * @return TutName object.
     * @throws ParseException if the given {@code tutorialName} doesn't match the regex.
     */
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
        String[] normalFormDays = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
                                               "FRIDAY", "SATURDAY", "SUNDAY"};
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

    /**
     * Parses a {@code String localTime} into a {@code LocalTime}.
     *
     * @param localTime User String.
     * @return LocalTime object.
     * @throws ParseException if user String is not 4 chars in length.
     */
    public static LocalTime parseLocalTime(String localTime) throws ParseException {
        requireNonNull(localTime);
        if (localTime.length() != 4) {
            throw new ParseException("Time entered should be in 24HR format eg 0900");
        }

        // Converts a string from '1200' to '12:00:00'.
        localTime = localTime.substring(0, 2) + ":" + localTime.substring(2, 4) + ":00";

        return LocalTime.parse(localTime, DateTimeFormatter.ISO_TIME);
    }

    /**
     * Parses a {@code String weeks} into an ArrayList of Integers.
     *
     * @param weeks User string. Eg 1,2,7
     * @return ArrayList of Integers representing the weeks.
     * @throws ParseException if unable to parse the string into Integers.
     */
    public static ArrayList<Integer> parseWeeks(String weeks) throws ParseException {
        requireNonNull(weeks);
        ArrayList<Integer> listOfWeeks;

        // check for user input of "odd" or "even"
        if (weeks.toLowerCase().equals("odd")) { // weeks 3, 5, 7, 9, 11, 13
            listOfWeeks = new ArrayList<>(Arrays.asList(3, 5, 7, 9, 11, 13));
            return listOfWeeks;
        } else if (weeks.toLowerCase().equals("even")) { // weeks 3, 5, 7, 9, 11, 13
            listOfWeeks = new ArrayList<>(Arrays.asList(4, 6, 8, 10, 12));
            return listOfWeeks;
        }

        listOfWeeks = new ArrayList<>();

        // check for user input of range "x-y"
        Matcher m = PATTERN_WEEKRANGE.matcher(weeks);
        if (m.find()) {
            String[] weekRange = m.group().split("-");
            int start = Integer.parseInt(weekRange[0]);
            int end = Integer.parseInt(weekRange[1]);
            if (start < 1 || end > 13) {
                throw new ParseException(MESSAGE_INVALID_WEEK_RANGE);
            }
            for (int i = start; i <= end; i++) {
                listOfWeeks.add(i);
            }
            return listOfWeeks;
        }

        // default - assume user input of list of weeks
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

    /**
     * Parses a {@code String weeks} into a Duration object.
     *
     * @param duration User string of the duration in minutes. Eg 120
     * @return Duration object.
     * @throws ParseException if unable to parse the string into Integers.
     */
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
