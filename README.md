# Ride price estimator
Just an estimator which takes maps coordinates (latitude &amp; longitude) of two addresses and other parameters as input and return a price to you.

# Requirements

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

TODO
