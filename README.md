# Bloomberg FX-Deals : Data Warehouse
## ProgressSoft Task
The solution to the tasks was built using spring and maven. 
<table>
  <tr>
    <th>Language</th>
    <td>Java 8</th>
  </tr>
  <tr>
    <th>Built Using</td>
    <td>SpringBoot <version>2.4.1.RELEASE</version>, Junit 4, Mockmvc, Maven, Hibernate, Mysql Database</td>
  </tr>
 
  
</table>

## Task:
Suppose you are part of a scrum team developing data warehouse for Bloomberg to analyze FX deals. One of customer stories is to accept deals details from and persist them into DB.

### Request Login
- Request Fields(Deal Unique Id, From Currency ISO Code "Ordering Currency", To Currency ISO Code, Deal timestamp, Deal Amount in ordering currency).
- Validate row structure.(e.g: Missing fields, Type format, etc. We do not expect you to cover all possible cases but we'll look to how you'll implement validations)
- System should not import same request twice.
- No rollback allowed, what every rows imported should be saved in DB.

### Deliverables should be ready to work including:
- Use Actual Db, you can select between (Postgres, MySql or MongoDB)
- Workable deployment including sample file (Docker Compose).
- Maven or Gradle project is required for full source code.
- Proper error/exception handling.
- Proper Logging.
- Proper unit testing with respected Coverage.
- Proper documentation using md.
- Delivered over Githhub.com.
- Makefile to streamline running application (plus).

## REST Endpoint
### Specs
````
POST /uploadFile
````
Upload a file with ".csv, .xls, .xlsx" extension.

### Successful Response Body: HTTP status code(200)
```
{
    "filename": "sample_csv.csv",
    "message": "Uploaded Successfully."
}
```

### File Already Exists Response Body: HTTP status code(400)
```
{
    "statusCode": "06",
    "statusMessage": "File already exists."
}
```

### Invalid File format Response Body: HTTP status code(400)
```
{
    "statusCode": "10",
    "statusMessage": "You have uploaded an invalid file."
}
```

### EmptyFile Response Body: HTTP status code(400)
```
{
    "statusCode": "06",
    "statusMessage": "Failed to store empty file."
}
```

### CSV File contains an invalid Date Response Body: HTTP status code(400)
```
{
    "statusCode": "08",
    "statusMessage": "Invalid date"
}
```

### CSV File contains an invalid ISO currency code Response Body: HTTP status code(400)
```
{
    "statusCode": "99",
    "statusMessage": "Invalid Currency ISO code"
}
```

The project contains a Dockerfile