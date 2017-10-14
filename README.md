Spring Boot Starter osquery
===========================
Integrate osquery in Spring Boot to display osquery information

### Query Parameters

* format, such as json, csv, pretty etc

### URLs

* /osquery: list osquery info and table names
* /osquery/{tableName}: output table content
* /osquery/{tableName}/{columns}: output table content with columns

### Attention

osquery SQL Specification： https://github.com/facebook/osquery/blob/master/docs/wiki/introduction/sql.md

### Todo

* osquery jdbc driver: local and remote(ssh channel)

### References

* osquery schema: https://osquery.io/schema/
* How to create jdbc driver : https://www.javaworld.com/article/2074249/data-storage/create-your-own-type-3-jdbc-driver--part-1.html
* CVSJdbc: https://github.com/jprante/jdbc-driver-csv
* Sqlite JDBC: https://bitbucket.org/xerial/sqlite-jdbc 
