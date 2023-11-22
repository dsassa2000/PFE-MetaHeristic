import json
import cv2
from PIL import Image
import pytesseract
from geopy.geocoders import Nominatim
import networkx as nx
import matplotlib.pyplot as plt
from geopy.distance import geodesic as GD
from geopy.distance import geodesic
import socket
import folium
import requests
import json

# Get a Google Maps API key.
api_key = "AIzaSyCMWsE4wnAuqIg6MBTXO4iWkp0u6qkmsLo"

# Create a request to the Google Maps Directions API.
url = "https://maps.googleapis.com/maps/api/directions/json?"

# Connect to the Java server
java_server_address = ('localhost', 9876)
python_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
python_socket.connect(java_server_address)

class City:
    def __init__(self,name,lat,long):
        self.name = name
        self.lat = lat
        self.long = long
    def setLatitude(self,lat):
       self.lat = lat
    def setLongitude(self,long):
        self.long = long        

pytesseract.pytesseract.tesseract_cmd = r'C:/Program Files/Tesseract-OCR/tesseract.exe'

image_path = 'C:/Users/HP/Desktop/PFE-MetaHeristic/CNN_Project/b5eb7422e57986ac61280981c262d975.jpg'
image = cv2.imread(image_path)

text = pytesseract.image_to_string(image)

geolocator = Nominatim(user_agent="city_extractor")
cities = text.split('\n')
city_coordinates = {}
listCitiesData = []
for city in cities:
    location = geolocator.geocode(city, timeout=10)
    if location: 
        city_data = {} # Create a new dictionary for each city
        city_data["name"] = city
        city_data["x"] = location.latitude
        city_data["y"] = location.longitude
        # Store the city data in the listCitiesData dictionary
        listCitiesData.append(city_data)
        # Store city coordinates in the city_coordinates dictionary
        city_coordinates[city] = (location.latitude, location.longitude)

print(listCitiesData)                
# Serialize the dictionary to a JSON string
city_coordinates_json = json.dumps(listCitiesData)
#print(city_coordinates_json);
python_socket.sendall(city_coordinates_json.encode('utf-8'))

m = folium.Map(location=[31.792305849269, -7.080168000000015], zoom_start=4)
# Add markers for each city to the map
for city, location in city_coordinates.items():
    folium.Marker(location=location, tooltip=city).add_to(m)

# Add lines to represent the links between cities
citiesD = list(city_coordinates.keys())
for i, city1 in enumerate(citiesD):
    for j, city2 in enumerate(citiesD):
        if i < j:
            start_city = city1
            end_city = city2
            #print("City 1:", start_city)
            #print("City 2:", end_city)
            start_location = city_coordinates[start_city]
            end_location = city_coordinates[end_city]
            folium.PolyLine(locations=[start_location, end_location], color="blue").add_to(m)
            if start_location is not None and end_location is not None:
                params = {
                "origin": f"{start_location[0]},{start_location[1]}",
                "destination": f"{end_location[0]},{end_location[1]}",
                "traffic_model": "best_guess",
                "departure_time": "now",
                "mode":"driving",
                "key": api_key,
                }
                response = requests.get(url, params=params)
                response.raise_for_status()
                data = json.loads(response.content)
                dataRoutes=data["routes"]
                for route in dataRoutes:
                    legs = route["legs"]
                    print("Traffic congestion:", legs[0]["duration_in_traffic"]["text"])
# Display the map
m.save("C:/Users/HP/Desktop/PFE-MetaHeristic/CNN_Project/mapCities.html") 

# Close the socket
python_socket.close()     