# README

### PROJECT SETUP

1. Download and install JDK 11.
    1. You can use [SDKMAN](https://sdkman.io/install).
    2. Verify your version using: `java --version`.
2. Download and install [Apache Maven 3.8.4](https://maven.apache.org/download.cgi).
    1. Verify your version using: `mvn --version`.
3. Install [MySQL](https://dev.mysql.com/doc/refman/8.0/en/installing.html).
    1. For local purposes, you can use H2 database. If you do it, ensure you do not commit it.
4. Select the IDE that you want to use: IntelliJ, VS Code, Eclipse, etc.
    1. Based on your selection, download the `*-style.xml` file for your IDE
       from [Google Style Guide](https://github.com/google/styleguide).
    2. Set up the downloaded file in the IDE preferences.

[ IMPORTANT ] Step 4 is a MUST if you want to open a pull request. Otherwise, your code will not
pass the check status and your code changes will not be reviewed.

### CODE STANDARDS

We follow the rules
from [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) and:

- The interfaces should start with prefix "I". Example: IUserRepository.
- The name of abstract classes should start with prefix "Abstract". Example: "AbstractFile".
- Package names are in singular.
- The names of attributes/fields from Java classes must be written using camel case. Example:
  firstName.
- The name of columns in the entities must be written using underscore and uppercase. Example:
  FIRST_NAME.
- The name of the tables is always in plural, but the entity name should be in singular.
- Exceptions should be handled by an implementation of ControllerAdvice.
- All the configuration classes must go in the config package.
- The integration test must go into the bigtest package.
- If you add a new endpoint, make sure to set the role access for it in the SecurityConfig class.

- [ Optional ] You will find an example of how to work with the project architecture in
  architecture-example branch.

### KEEP IN MIND FOR PULL REQUEST AND CODE REVIEW

#### FORMAT:

- The branch name format is: {jiraTicket#}.
- The pull request title format is: "{jiraTicket#}: {jiraTitle}".
- The commits format is: "{jiraTicket#}: {commitDescription}". Small commits are a nice to have.
- The pull request has to contain only the changes related to the scope defined in the ticket.
    - If you pull request contains unrelated changes, it will be rejected until those changes be
      removed.
- If your pull request does not pass all the checks, it will not be review until checks passed.

#### EVIDENCE:

- If you do not write unit test or integration test as part of your code changes, you should add the
  HTTP request and response as evidence that the code is working as expected.
- Postman collection should be updated every time that you open a pull request. Be a team player!

#### REQUEST RE-REVIEW

- Once you addressed all the comments, click on "Request re-review" button.
- [ Optional ] At the bottom of the pull request, you can leave a comment asking for re-review.

### RUN LOCALLY

On the root folder run:

```
mvn spring-boot:run
```

### CHECKSTYLE

You can generate the Checkstyle report by explicitly executing below command from the command line:

```
mvn checkstyle:checkstyle
```
