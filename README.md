# Ride price estimator
Just an estimator which takes maps coordinates (latitude &amp; longitude) of two addresses and other parameters as input and return a price to you.

## Requirements

Java 1.8 or higher.

A Google Maps API key. (Put it in the google.api.key property in application.properties file)

Maven installed.

## Installing and running

Download the project dependencies : 
```bash 
mvn clean install 
```

Run : 
```bash 
mvn spring-boot:run
```

## Usage

* **URL:**
http://localhost:8080/estimate

* **Method:**
`POST`

* **Data Params:**
`{
    "latitude1" : "48.8567397",
    "longitude1" : "2.2914176",
    "latitude2" : "48.7999614",
    "longitude2" : "2.1289782"
}`

* **Success Response:**

  * **Code:** 200 <br />
  * **Content:** `[
    {
        "rideOptionDTO": {
            "id": "1",
            "name": "POOL"
        },
        "price": 43.0
    },
    {
        "rideOptionDTO": {
            "id": "2",
            "name": "COMFORT"
        },
        "price": 56.5
    },
    {
        "rideOptionDTO": {
            "id": "3",
            "name": "LUXURY"
        },
        "price": 70.0
    }
]`

## Documentation

The idea of the price calculation is to give a price per kilometer to intervals (you can add many in the database) and to give a minimum price of the ride too.

**Examples :** 

|distance (km) | price ($) |
|:----------:|:----------:|
|0|10|
|4|3|
|8|2|
|15|1|

  * **Example 1 :**

    Ride distance :  7 km

    4 * 3 USD  = $ 12

    3 * 2 USD = $ 6

    Total : 12 + 6 = $ 18


  * **Example 2 :**

      Ride distance :  2.5 km

    2.5 * 3 USD  = $ 7.5 < $ 10 (minimum price)

    Total : $ 10
