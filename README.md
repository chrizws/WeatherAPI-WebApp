# Spring MVC - Open Weather API

## Description

A simple web application created to demonstrate the use of OpenWeather API in Spring MVC.
The web app is able to query and delete weather records from a database. Personal API key is required from OpenWeather to use the application.

## Features

- Initial loading fetches all existing records from the database and populates it in the table
- Results are persisted into the database with upto 3 days of forecast
- Deleting a record on the front end also cascades the changes in the database
- Sorting, pagination, and table searching are implemented by DataTable

## Requirements
- API key and passwords are stored in `secrets.properties` file
```
weather.api = {key}
weather.url = http://api.weatherapi.com/
spring.datasource.username = root
spring.datasource.password = {password}
```

## Front-End
- HTML
- CSS
- JavaScript
- jQuery
- DataTable

## Back-End
- Spring MVC
- Spring Data JPA
- OpenFeign
- MySQL
- Unit and Integration tested

## Examples
- Searching and inserting

https://github.com/user-attachments/assets/551e6822-a572-4057-ae40-254aaa3021c9


- Deleting a row and record

https://github.com/user-attachments/assets/5cf49557-39e0-4f9e-9086-275b465a878e


- Searching and sorting


https://github.com/user-attachments/assets/76cba8a2-14b8-4e77-a4fa-dd5c8b728ffe




