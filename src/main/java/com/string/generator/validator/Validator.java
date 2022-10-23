package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Validator extends AbstractValidator
{
    private int charactersProvided;

    private final ConfigurableEnvironment config;

    @Autowired
    public Validator(ConfigurationService configurationService)
    {
        this.config = configurationService.properties();
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
            this.errorMessages.add(String.format(
                    this.config.getRequiredProperty("create.job.exceeds.total.unique"),
                    possibleStringVariations
            ));
        }
        if (expectedResults <= 0) {
            this.errorMessages.add(this.config.getProperty("create.job.zero.qty"));
        }
    }

    /**
     * Max length cannot exceed total qty of chars provided
     * @param maxLength max length of the generated string
     */
    private void validateMaxLength(int maxLength)
    {
        if (maxLength > this.charactersProvided) {
            this.errorMessages.add(String.format(
                    this.config.getRequiredProperty("create.job.maximum.too.high"),
                    this.charactersProvided
            ));
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
            this.errorMessages.add(this.config.getProperty("create.job.minimum.too.high"));
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
            this.errorMessages.add(this.config.getProperty("create.job.length.zero"));
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
                this.errorMessages.add(String.format(
                        this.config.getRequiredProperty("create.job.character.repeat"),
                        allCharsInString[i]
                ));
                return;
            }
        }
    }
}
