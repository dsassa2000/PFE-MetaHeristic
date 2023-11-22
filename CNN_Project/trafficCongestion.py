import requests
import json

# Get the coordinates of the place for which you want to get traffic data.
coordinates = (35.178585, -5.2791204)
coordinates1 = (31.6258257, -7.9891608)

# Get a Google Maps API key.
api_key = "AIzaSyCMWsE4wnAuqIg6MBTXO4iWkp0u6qkmsLo"

# Create a request to the Google Maps Directions API.
url = "https://maps.googleapis.com/maps/api/directions/json?"
params = {
    "origin": f"{coordinates[0]},{coordinates[1]}",
    "destination": f"{coordinates1[0]},{coordinates1[1]}",
    "traffic_model": "best_guess",
    "departure_time": "now",
    "mode":"driving",
    "key": api_key,
}

# Send the request and parse the response.
response = requests.get(url, params=params)
response.raise_for_status()
data = json.loads(response.content)

dataRoutes=data["routes"]
print(dataRoutes)
for route in dataRoutes:
    print(route["legs"])
    legs = route["legs"]
    print("Traffic congestion:", legs[0]["duration_in_traffic"]["text"])
# Get the real-time traffic data from the response.
#traffic_data = data["legs"][0]["traffic_speed_entry"]
# Print the real-time traffic data.
#print("Traffic speed:", traffic_data["speed_kmph"], "km/h")
#print("Traffic congestion:", traffic_data["congestion_level"])