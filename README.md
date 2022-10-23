# Task Description: 
Create a mini application in Java. Application need to generate a file with random unique strings (every string in a separate row). Users should be able to define how long the string will be (min and max length), specify possible chars of string (from what characters string should be made) and how much string he wants.
# Techstack used
- Gradle 7.5
- Java 17
- Spring Boot 2.7.4
# Acceptance criteria:
- Use maven or gradle
- Connection to local DB
- REST endpoints:
    - Create Job (POST)
    - Resume Job (POST)
    - check running Jobs (GET)
    - Fetch result (GET)
 - Return detailed error messages
    - ex. expected qty exceeds total possible permutations
 - Generate multiple results in parallel
 
 # Run the app
- change **src/main/resources/configuration.properties:1** to wherever you want to save the generated files
- **./gradlew build**
- **./gradlew bootrun** - run the app 
- **./gradlew test** - run test only
 
 # API collection: 
- Downloand this postman collection - > <a id="raw-url" href="https://raw.githubusercontent.com/duckiedot/string-generator-api-app/master/stringgenerator.postman_collection.json">Download FILE</a>
    - Create Job 
        - **{{hostname}}/rest/createjob**
        - body - JSON
        - allowedCharacters - String, allowed characters to generate from
        - minimumLength - int, minimum length of the generated string
        - maximumLength - int, maximum length of the generated string
        - expectedResults - int, expected unique strings to generate
    - Resume Job
        - **{{hostname}}/rest/resumejob
        - body - JSON
        - int - id of the job we want to resume
    - Get running jobs
        - **{{hostname}}/rest/jobs/get-all-active**
        - Returns all jobs with status active
    - Get Job by ID
        - **{{hostname}}/rest/jobs/get-by-id/{{jobId}}**
        - if exists, it returns the job by given id
    - Get all jobs
        - **{{hostname}}/rest/jobs/get-all-jobs**
        - returns every job, regardless of its status
    - Download result
        - **{{hostname}}/rest/download-by-id**
        - parameter ?id={id}
        - returns .txt file with all generated strings 
