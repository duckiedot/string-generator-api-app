package com.string.generator.assignment.validator;

import com.string.generator.assignment.model.job.Job;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Validator extends AbstractValidator
{
    private int charactersProvided;

    private final String ERROR_MSG_EXCEEDS_TOTAL_UNIQUE =
            "The expected return count exceeds the total possible unique strings: %o";
    private final String ERROR_MSG_ZERO_QTY = "If you don't want results, why even call this request? :)";

    private final String ERROR_MSG_MAX_LENGTH = "The expected length is bigger than qty of characters given: %o";
    private final String ERROR_MSG_MIN_LENGTH = "Minimum length cannot be smaller than maximum length";
    private final String ERROR_MSG_ZERO_LENGTH = "The length cannot be zero";
    private final String ERROR_CHARACTERS_REPEAT = "Character: %s occurs more than once";


    public Validator()
    {
        this.errorMessages = new ArrayList<String>();
    }

    /**
     * Invoke every validator which upon error, append the errorMessages list.
     * Returns valid if no errors are present in array
     *
     */
    public boolean isValid(Job job)
    {
        this.errorMessages.clear();
        this.charactersProvided = (job.getAllowedCharacters() != null) ? job.getAllowedCharacters().length() : 0;

        this.validateExpectedResults(job.getExpectedResults());
        this.validateMaxLength(job.getMaximumLength());
        this.validateMinLength(job.getMinimumLength(), job.getMaximumLength());
        this.validateCharactersNotRepeat(job.getAllowedCharacters());

        return this.getErrorMessages().isEmpty();
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

    private void validateCharactersNotRepeat(String allowedCharacters)
    {
        char[] allCharsInString = allowedCharacters.toCharArray();

        for (int i = 0; i <allCharsInString.length; i++) {
            int count = 1;
            for (int j = i + 1; j < allCharsInString.length; j++) {
                if(allCharsInString[i] == allCharsInString[j] && allCharsInString[i] != ' ') {
                    count++;
                    allCharsInString[j] = '0';
                }
            }
            //A character is considered as duplicate if count is greater than 1
            if (count > 1 && allCharsInString[i] != '0') {
                this.errorMessages.add(String.format(this.ERROR_CHARACTERS_REPEAT, allCharsInString[i]));
                return;
            }
        }
    }
}
