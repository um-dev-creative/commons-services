# commons-services
Commons components related to services implementation

Updated to target Spring Boot 4.x and Java 25. See `MIGRATION_TO_SB4.md` for details and migration steps.

Build (if you don't have JDK 25 locally):

```
mvn clean package "-Dbuild.java.version=21" -DskipTests=true
```

Run tests:

```
mvn test "-Dbuild.java.version=21"
```
