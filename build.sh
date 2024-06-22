cd $1
mvn clean
mvn package
mvn exec:java -Dexec.mainClass="com.OndaByte.$1.App"
