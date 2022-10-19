package com.string.generator.assignment.model.job;

import java.util.ArrayList;

public class Validator
{
    private int charactersProvided;

    private final String ERROR_MSG_EXCEEDS_TOTAL_UNIQUE =
            "The expected return count exceeds the total possible unique strings: %o";
    private final String ERROR_MSG_ZERO_QTY = "If you don't want results, why even call this request? :)";

    private final String ERROR_MSG_MAX_LENGTH = "The expected length is bigger than qty of characters given: %o";
    private final String ERROR_MSG_MIN_LENGTH = "Minimum length cannot be smaller than maximum length";
    private final String ERROR_MSG_ZERO_LENGTH = "The length cannot be zero";

    private ArrayList<String> errorMessages;

    public Validator()
    {
        this.errorMessages = new ArrayList<String>();
    }

    /**
     * Invoke every validator which upon error, append the errorMessages list.
     * Returns valid if no errors are present in array
     *
     * @param characters characters provided by user
     * @param maxLength maximum length of generated string
     * @param minLength minimum length of generated string
     * @param expectedResults expected results to generate
     * @return
     */
    public boolean isValid(String characters, int maxLength, int minLength, int expectedResults)
    {
        this.charactersProvided = characters.length();

        this.validateExpectedResults(expectedResults);
        this.validateMaxLength(maxLength);
        this.validateMinLength(minLength, maxLength);

        return this.getErrorMessages().isEmpty();
    }

    /**
     *
     * @return all errors preventing the job creation
     */
    public ArrayList<String> getErrorMessages()
    {
        return this.errorMessages;
    }

    /**
     *
     * @param expectedLength The amount of chars provided by the user
     * @return permutation of expectedLength
     */
    private int countPossibleVariations(int expectedLength)
    {
        if (expectedLength == 0)
            return 1;
        if (expectedLength == 1)
            return 1;
        else
            return (expectedLength * countPossibleVariations(expectedLength - 1)) +
                    ((expectedLength - 1) * countPossibleVariations(expectedLength - 2));
    }

    /**
     * The expected qty cannot exceed total possible unique strings.
     * @param expectedResults expected qty of generated strings
     */
    private void validateExpectedResults(int expectedResults)
    {
        int possibleStringVariations = this.countPossibleVariations(this.charactersProvided);

        if (!(expectedResults <= possibleStringVariations)) {
            this.errorMessages.add(String.format(this.ERROR_MSG_EXCEEDS_TOTAL_UNIQUE,  possibleStringVariations));
        }
        if (expectedResults <= 0) {
            this.errorMessages.add(this.ERROR_MSG_ZERO_QTY);
        }
    }

    /**
     * Max length cannot exceed total qty of chars provided
     * @param maxLength max length of the generated string
     */
    private void validateMaxLength(int maxLength)
    {
        if (maxLength > this.charactersProvided) {
            this.errorMessages.add(String.format(this.ERROR_MSG_MAX_LENGTH, this.charactersProvided));
        }
        this.validateZeroLength(maxLength);
    }

    /**
     * The minimum length cannot be bigger than maximum
     *
     * @param minLength minimum length
     * @param maxLength maximum length
     */
    private void validateMinLength(int minLength, int maxLength)
    {
        if (minLength > maxLength) {
            this.errorMessages.add(this.ERROR_MSG_MIN_LENGTH);
        }
        this.validateZeroLength(minLength);
    }

    /**
     * Don't allow to generate strings with no length
     *
     * @param length length to validate
     */
    private void validateZeroLength(int length)
    {
        if (length <= 0) {
            this.errorMessages.add(this.ERROR_MSG_ZERO_LENGTH);
        }
    }

}