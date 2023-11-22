import requests
import json

lat = 35.178585
lon = -5.2791204
GOOGLE_MAPS_API_KEY = "AIzaSyCMWsE4wnAuqIg6MBTXO4iWkp0u6qkmsLo"
# Google Maps API for traffic information
traffic_url = f'https://maps.googleapis.com/maps/api/geocode/json?latlng={lat},{lon}&key={GOOGLE_MAPS_API_KEY}'
traffic_response = requests.get(traffic_url)
traffic_data = traffic_response.json()
print(traffic_data)