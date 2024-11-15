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

https://github.com/user-attachments/assets/6eb584a0-060e-4311-a3c3-b938c1c4305d

- Deleting a row and record

https://github.com/user-attachments/assets/829501c8-9651-48de-b885-f9071d5e2e5e

- Searching and sorting

https://github.com/user-attachments/assets/0e6b64aa-abfd-4d91-b0eb-bb598c70e060

