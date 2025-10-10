# LEARNING MICROSERVICES - My Project

**Basic Requirement for contributing:**

| Requirement | Version |
|-------------|---------|
| JDK         | 21.x    |
| Maven       | 3.9.x   |

_Note:_ Creating Pull Request for merging your changes to main branch is must.

### Steps to contribute

- _**Fork**_ this repository either from **[GitHub CLI](https://cli.github.com)** or **[Web page](https://github.com)**. 
Make sure you have configured Git on your local.
- Create a clone of forked repository (your own copy).
- Create a branch to contribute.
- Push the changes by creating a PR to feature branch.

### Steps to compile and run the project
- Clone the repository.
- Navigate to the project root directory `ms-app`.
- Run the following command to compile and package the application:
  ```
  mvn clean install -Dspring.profiles.active=test
  ```
- After successful build, navigate to ms-product directory and run either of the following commands:
  ```
  mvn spring-boot:run -Dspring-boot.run.profiles=test
  ```
  or
  ```
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=test
  ```
- The application will start on port 8100.