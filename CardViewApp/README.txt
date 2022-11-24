1. To start development, Use any IDE to import this project and execute maven commands to build war file which can be deployed in web server,
    a) Import the project
    b) Make changes if required
    c) Execute Maven commands: clean compile install (or) clean compile package
    d) '.war' file will be generated for deployment.

2.  URL to access employee card view
    http://<hostname>:<port>/CardView/employee

3.  URL to access expense card view
    http://<hostname>:<port>/CardView/expense

4. In "application.properties", set the value of the below property to your project deployment path,
   spring.project.directory=C:/Users/Raja/apache-tomcat-9.0.59/webapps/CardView/

5. In "expense-functions.js", if in case your project name is modified, make sure to change the project name 'CardView' in the below url's
    url: '/CardView/expense/uploadFile'
    url: '/CardView/expense/deleteFile'

6. In "employee-functions.js", if in case your project name is modified, make sure to change the project name 'CardView' in the below url's
    url: '/CardView/employee/uploadFile'
    url: '/CardView/employee/deleteFile'

7. In "pom.xml", if in case your database is changed, make sure to,
    a) Comment the following dependency
        <dependency>
   			<groupId>com.h2database</groupId>
   			<artifactId>h2</artifactId>
   			<scope>runtime</scope>
   		</dependency>
   	b) Uncomment the following dependency if it's mysql database (or) add dependency as according to your database,
   	    <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

8. In "application.properties", if in case your database is changed, configure the following properties,

    spring.datasource.url=jdbc:mysql://localhost:3306/mydb
    spring.datasource.username=root
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

9. This project uses H2 in-mem database for its database operations.
    a) "schema.sql" drops and creates TBL_EMPLOYEES and TBL_EXPENSES on every restart.
    b) "data.sql" insert mock data into TBL_EMPLOYEES and TBL_EXPENSES tables.

10. In expense-functions.js, you can change the following min and max amount threshold based on which the font color will be changed,
        var min = 1000;
        var max = 2000;