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

# Connect to the Java server
java_server_address = ('localhost', 9876)
python_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
python_socket.connect(java_server_address)

class City:
    def __init__(self,lat, long):
        self.lat = lat
        self.long = long


pytesseract.pytesseract.tesseract_cmd = r'C:/Program Files/Tesseract-OCR/tesseract.exe'

image_path = 'C:/Users/HP/Desktop/CNN_Project/b5eb7422e57986ac61280981c262d975.jpg'
image = cv2.imread(image_path)
#image = Image.open(image_path)

text = pytesseract.image_to_string(image)

geolocator = Nominatim(user_agent="city_extractor")
cities = text.split('\n')  # Assuming each city name is on a new line
#print("cities",cities)

city_coordinates = {}
#ListCities = [];
for city in cities:
    location = geolocator.geocode(city, timeout=10)
    if location:
        #cityObject =  City(location.latitude, location.longitude)
        city_coordinates[city] = (location.latitude, location.longitude)
        #ListCities.append(cityObject)

print(city_coordinates)
# Serialize the dictionary to a JSON string
city_coordinates_json = json.dumps(city_coordinates)
python_socket.sendall(city_coordinates_json.encode('utf-8'))
G = nx.Graph()

for city, coordinates in city_coordinates.items():
    G.add_node(city, pos=coordinates)

# Calculate distances between cities
distance_threshold = 5.0
def calculDistancesBetweenCities(city_coordinates):
    for city1 in city_coordinates:
        for city2 in city_coordinates:
            if city1 != city2:
                distance = geodesic(city_coordinates[city1], city_coordinates[city2]).kilometers
                print(f"Distance between {city1} and {city2}: {distance:.2f} km")  
                if distance <= distance_threshold:
                  G.add_edge(city1, city2, weight=distance)
    return distance            

distance = calculDistancesBetweenCities(city_coordinates)  

# Example: Visualize the graph
pos = nx.get_node_attributes(G, 'pos')
nx.draw(G, pos, with_labels=True, font_size=8, font_color='red', node_size=300, font_weight='bold')

# Display the graph
plt.show()            
     

# Close the socket
python_socket.close()     