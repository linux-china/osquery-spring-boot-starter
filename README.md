Spring Boot Starter osquery
===========================
Integrate osquery in Spring Boot to query system information.

# Endpoints

* `/osquery`: list osquery info and table names
* `/osquery/{tableName}`: output table content
* `/osquery/{tableName}(col1,col2,col3)`: output table content with columns

**Note**: By default, the output is in CSV format. If you want to output in JSON format,
you can add format query string, such as `/osquery/etc_hosts?format=json`.

# DuckDB friendly

```shell
$ duckdb -c "SELECT * FROM read_csv('http://localhost:8888/actuator/osquery/etc_hosts')"
```

# References

* osquery schema: https://osquery.io/schema/
* How to create jdbc
  driver : https://www.javaworld.com/article/2074249/data-storage/create-your-own-type-3-jdbc-driver--part-1.html
* CVSJdbc: https://github.com/jprante/jdbc-driver-csv
* Sqlite JDBC: https://bitbucket.org/xerial/sqlite-jdbc
