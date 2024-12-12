Spring Boot Starter osquery
===========================
Integrate osquery in Spring Boot to query system information.

# Get Started

- Install osquery: https://osquery.io/
- Add dependency in your `pom.xml`:

```xml

<dependency>
  <groupId>org.mvnsearch</groupId>
  <artifactId>osquery-spring-boot-starter</artifactId>
  <version>0.1.0</version>
</dependency>
```

- Adjust `application.properties` to set management configuration

```
### management
management.server.port=8888
management.endpoints.web.exposure.include=*
management.endpoint.health.show-components=always
management.endpoint.health.show-details=always
### set osqueryi path if not in PATH
#osqueryi.path=/usr/local/bin/osqueryi
```

- Start your Spring Boot application and access the osquery endpoints

# Endpoints

* `/actuator/osquery`: list osquery info and table names
* `/actuator/osquery/{tableName}`: output table content
* `/actuator/osquery/{tableName}(col1,col2,col3)`: output table content with columns

**Note**: By default, the output is in CSV format. If you want to output in JSON format,
you can add format query string, such as `/actuator/osquery/etc_hosts?format=json`.

Examples:

```
### osquery
GET http://localhost:8888/actuator/osquery

### osquery schema
GET http://localhost:8888/actuator/osquery/etc_hosts

### osquery schema with columns
GET http://localhost:8888/actuator/osquery/etc_hosts(address,hostnames)
```

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
