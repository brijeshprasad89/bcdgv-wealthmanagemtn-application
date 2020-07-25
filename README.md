# BCGDV Wealth Management Application

## Instructions to run the application.

The Application assumes Financial Portfolio to be running on the host : ```http://localhost:5000```.
The same can be updated in application.properties.

1. Update customers.csv and strategies.csv at `src/main/resources` folders to include more values.
2. Run `mvn clean compile exec:java -Dexec.mainClass="com.bcgdv.wealthmgmt.WealthmgmtApplication"`
3. The scheduler will run every day at 1 AM and process the csv files.