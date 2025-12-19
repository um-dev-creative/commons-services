Migration notes: Upgrade to Spring Boot 4.x and Java 25

What changed
- Spring Boot parent updated to 4.0.0
- Project `java.version` updated to 25 (compiler release configurable via `-Dbuild.java.version`)
- Spring Cloud BOM bumped to a 2025.x train (adjust if your organization pins a different release)
- Tomcat and Jakarta servlet dependencies are now managed by Spring Boot 4 (jakarta.* namespace)
- Tests: adjusted Mockito test configuration for compatibility with newer Mockito strictness rules

Build and test locally (if you don't have JDK 25 installed)
- Use your existing JDK (e.g., 21) but override the compiler release so the code compiles with the older javac:

  mvn clean package "-Dbuild.java.version=21" -DskipTests=true

To run unit tests locally (using current local JDK):

  mvn test "-Dbuild.java.version=21"

CI / Production
- CI should use JDK 25 for building and running integration tests to match the new runtime target.
- Update Docker images to a JDK 25 base (e.g., eclipse-temurin:25-jre or equivalent) in Dockerfiles or build pipelines.

Eureka (Jersey) compatibility
- This project references older Jersey-based Eureka clients in comments. Verify Eureka client usage in downstream services and prefer using Spring Cloud DiscoveryClient implementations compatible with Spring Boot 4 / Spring Cloud 2025.x. Consider migrating away from the legacy com.sun.jersey clients if necessary.

Downstream migration checklist
- Update dependent services to compile against Java 25 (or continue using -Dbuild.java.version for local builds).
- Update Spring Boot parent or Spring Cloud BOM only when downstream projects are ready to accept breaking changes from Spring Framework 7.x / Jakarta 6.x.

Rollback
- If issues are found, revert the pom change and release a patch that keeps the previous Spring Boot 3.x parent.

Contact
- For assistance, reach out to the repository maintainer listed in pom.xml.

