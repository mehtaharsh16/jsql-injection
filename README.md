## Description
**jSQL Injection** is a lightweight application used to find database information from a server.

It's **free**, **open source** and **cross-platform** for Windows, Linux and Mac and it works with Java from version 11 to 20.

![Kali Linux logo](https://github.com/ron190/jsql-injection/raw/master/web/images/kali_favicon.png "Kali Linux logo") jSQL Injection is also part of the official penetration testing distribution [Kali Linux](http://www.kali.org/) and is included in various other distributions like [Blackbuntu](https://blackbuntu.org), [Pentest Box](https://pentestbox.com/), [Parrot Security OS](https://www.parrotsec.org), [ArchStrike](https://archstrike.org/) and [BlackArch Linux](http://www.blackarch.org/).

[![Twitter](https://img.shields.io/twitter/follow/ron190jsql.svg?style=social&label=ron190 "Developer Twitter account")](https://twitter.com/ron190jsql)<br>
[![Java](https://img.shields.io/badge/java-11%20to%2020-orange?logo=java "Version range compatibility")](http://www.oracle.com/technetwork/java/javase/downloads/)
[![JUnit](https://img.shields.io/badge/junit-5-50940f "Tests")](http://junit.org)
[![Maven](https://img.shields.io/badge/maven-3.6-a2265a "Build")](https://maven.apache.org/)
[![License](https://img.shields.io/github/license/ron190/jsql-injection "License")](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html)<br>
[![GitHub](https://img.shields.io/badge/build-blue?logo=github "Github Actions status")](https://github.com/ron190/jsql-injection/actions)
[![Codecov](https://img.shields.io/codecov/c/github/ron190/jsql-injection?label=coverage&logo=codecov "Codecov test coverage")](https://codecov.io/gh/ron190/jsql-injection)
[![Codacy](https://img.shields.io/codacy/grade/e7ccb247f9b74d489a1fa9f9483c978f?label=quality&logo=codacy "Codacy code quality")](https://app.codacy.com/gh/ron190/jsql-injection/dashboard)
[![Snyk](https://img.shields.io/badge/build-monitored-8A2BE2?logo=snyk&label=security "Snyk code vulnerability")](#)<br>
[![Sonar](https://img.shields.io/sonar/violations/ron190:jsql-injection?format=long&label=issues&logo=sonarqube&server=https%3A%2F%2Fsonarcloud.io "Sonar code issues")](https://sonarcloud.io/dashboard?id=ron190%3Ajsql-injection)

## Features
- Automatic injection of 33 database engines: Access, Altibase, C-treeACE, CockroachDB, CUBRID, DB2, Derby, Exasol, Firebird, FrontBase, H2, Hana, HSQLDB, Informix, Ingres, InterSystems-IRIS, MaxDB, Mckoi, MemSQL, MimerSQL, MonetDB, MySQL, Neo4j, Netezza, NuoDB, Oracle, PostgreSQL, Presto, SQLite, SQL Server, Sybase, Teradata and Vertica
- Multiple injection strategies: Normal, Stacked, Error, Blind and Time
- Parallel bitwise Boolean Blind and Time strategies
- Various injection processes: Default, Zip, Dios
- Database fingerprint: Basic error, Order By error, Boolean single query
- Script sandboxes for SQL and tampering
- Inject multiple targets
- Read and write files using injection
- Create and display Web shell and SQL shell
- Bruteforce password hash
- Search for admin pages
- Hash, encode and decode text
- Authenticate using Basic, Digest, NTLM and Kerberos  
- Proxy connection on HTTP, SOCKS4 and SOCKS5

## Installation [[jsql-injection-v0.92.jar](https://github.com/ron190/jsql-injection/releases/download/v0.92/jsql-injection-v0.92.jar)]
Install [Java](http://java.com) 11 or up to 20, then download the latest [release](https://github.com/ron190/jsql-injection/releases/) and double-click on the file `jsql-injection-v0.92.jar` to launch the software.<br>
You can also type `java -jar jsql-injection-v0.92.jar` in your terminal to start the program.<br>
If you are using Kali Linux then get the latest release using command `sudo apt-get -f install jsql`, or make a system full upgrade with `apt update` then `apt full-upgrade`.<br>
To run older version on Java 16+ use `java --illegal-access=warn --add-exports java.base/sun.net.www.protocol.http=ALL-UNNAMED -jar jsql-injection-v0.92.jar`.

## Continuous integration
This software is developed using open source libraries like [Spring](https://spring.io), [Spock](http://spockframework.org) and [Hibernate](https://hibernate.org) and is tested using continuous integration platform Github Actions.<br> 
Non regression tests are run against dockerized and in memory databases and GUI is tested on VNC screen on the CI platform, then quality checks are stored on code quality platform.
```mermaid
---
title: Architecture, covered by DevOps pipeline
---
graph
junit(JUnit Tests)
subgraph "jSQL Injection"
    gui(GUI)
    model("💉Model")
end
subgraph Spring
    apis([/api])
    admin([/admin-page])
end
subgraph Memory
    memory-other[("
        SQLite H2
        HSQLDB
        Derby
    ")]   
end
subgraph Docker
    subgraph Apache + PHP
        direction LR
        mysql[(MySQL)]
        shell(["/shell.php"])
        passwd(["/etc/passwd"])   
    end   
end
subgraph Docker2 [Docker]
    docker-other[("
        SQL Server
        PostgreSQL 
        Neo4j Db2
        Cubrid
    ")]   
end
gui -. "call" .-> shell
mysql -. create .-> shell
mysql -. read .-> passwd
junit -.-> gui
junit --> model
model & gui -.-> admin
model --> apis
apis --> Docker & Docker2 & Memory
```

## [[Test-bed scripts for Spring](https://github.com/ron190/jsql-injection/tree/master/model/src/test/java/spring/rest)]
See test scripts used for CI integration.

## [[Test-bed scripts for PHP](https://github.com/ron190/jsql-injection/tree/master/web/test-bed)]
Use the sample PHP scripts to test injection on your local environment. First install a development environment like [EasyPHP](http://www.easyphp.org), then download the [test-bed PHP scripts](https://github.com/ron190/jsql-injection/tree/master/web/test-bed) and move them into `www/`.
```php
<?php
# http://127.0.0.1/mysql/strategy/get-normal.php?id=0

$link = mysqli_connect('localhost', 'root', '', 'my_database');

$result = $link->query("SELECT col1, col2 FROM my_table where id=$_GET[id]");

while ($row = $result->fetch_array($result, MYSQLI_NUM))
    echo join(',', $row);
```

## Screenshots
[![Database](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/database-mini.png "Database")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/database.png)
[![SQL Engine](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.82/sqlengine-mini.png "SQL Engine")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.82/sqlengine.png)
[![Tamper](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.82/tamper-mini.png "Tamper")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.82/tamper.png)
[![Batch scan](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/scan-mini.png "Batch scan")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/scan.png)
[![Admin page](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/admin-mini.png "Admin page")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/admin.png)
[![Web shell](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/webshell-mini.png "Web shell")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/webshell.png)
[![SQL shell](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/sqlshell-mini.png "SQL shell")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/sqlshell.png)
[![File](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/file-mini.png "File")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/file.png)
[![Upload](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/upload-mini.png "Upload")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/upload.png)
[![Bruteforce](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/bruter-mini.png "Bruteforce")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/bruter.png)
[![Coder](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/coder-mini.png "Coder")](https://github.com/ron190/jsql-injection/raw/master/web/images/v0.75/coder.png)

## Informations
<details>
<summary>Change log</summary>
    
**v0.84-85** `Upgrade to Java 11, compatible up to Java 17`

**v0.83** `Modes Zip and Dios, Insertion char and db fingerprinting, 33 dbs including Altibase C-treeACE Exasol FrontBase InterSystems-IRIS MemSQL MimerSQL MonetDB Netezza and Presto`

**v0.82** `Tampering options, Refactoring for Cloud and multithreading`

**v0.81** `Test all parameters including JSON, Parse forms and Csrf tokens, 23 dbs including CockroachDB Mckoi Neo4j NuoDB Hana and Vertica, Translation complete: Russian, Chinese`

**v0.79** `Error Strategies for MySQL and PostgreSQL compatible with Order/Group By, Wider range of Characters Insertion including multibyte %bf`

**v0.78** `SQL Engine, MySQL Error strategy: DOUBLE, Translations: es pt de it nl id, 18 Database flavors including Access`

**v0.76** `Translation: cz, 17 dbs including SQLite`

**v0.75** `URI injection point, Source code mavenification, Upgrade to Java 7`

**v0.73** `Authentication: Basic Digest Negotiate NTLM and Kerberos, Database flavor selection`

**v0.7** `Scan multiple URLs, Github Issue reporter, 16 dbs including Cubrid Derby H2 HSQLDB MariaDB and Teradata`

**alpha-v0.6** `Speed x2: No more hex encoding, 10 dbs including Oracle SQLServer PostgreSQL DB2 Firebird Informix Ingres MaxDb and Sybase, JUnit tests, Log4j, GUI translation`

**0.5** `SQL Shell, File Uploader`

**0.4** `Admin page finder, Bruteforce hashes like MD5 and MySQL, Encode and decode string with methods like Base64, Hex and MD5`

**0.3** `File injection, Web Shell with integrated CLI, Persistence of application parameters, Update checker`

**0.2** `Strategy Time, Multi-thread control: Start Pause Resume and Stop, Log URL calls`

**0.0-0.1** `Method GET POST Header and Cookie, Strategies Normal Error and Blind, Best strategy selection, Progression bars, Simple evasion, Proxy settings, MySQL only`
</details>
<details>
<summary>Roadmap</summary>

## [[Taskboard](https://github.com/ron190/jsql-injection/projects)]
```
- New manager: create auth token for Basic, Digest, Negotiate, NTLM
- Full Path Disclosure
- WAF fingerprinting
- Inject user defined query
- Inject range of rows
- Routed query strategy
- Connect to Digest/Kerberos API with HttpClient
- Replace Docker with Kubernetes
- Database fingerprinting: Boolean single query
```

## In progress
```
- Implement DNS/HTTP out-of-band algorithm
- Inject each Cookie parameters
- Rows custom load
```

## Since latest release
```
- Testing Oracle DNS/HTTP out-of-band
- Testing PostgreSQL DNS out-of-band
- Testing Websocket Basic/STOMP
- Testing GraphQL
- Testing Kerberos
```
</details>

## Disclaimer
Attacking web-server is illegal without prior mutual consent. The end user is responsible and obeys all applicable laws.
Developers assume no liability and are not responsible for any misuse or damage caused by this program.
