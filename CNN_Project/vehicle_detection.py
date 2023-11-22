# Importting necessary libraries
import pandas as pd
import numpy as np
import socket
import requests
import json

java_server_address = ('localhost', 9877)
python_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
python_socket.connect(java_server_address)
car_petrol_df = pd.read_csv('C:/Users/HP\Downloads/archive (1)/PETROL.csv')

print(car_petrol_df)

class Car:
    def __init__(self, name, fuel_efficiency, tank_capacity):
        self.name = name
        self.fuel_efficiency = fuel_efficiency
        self.tank_capacity = tank_capacity

    def calculate_max_distance(self):
        return self.fuel_efficiency * self.tank_capacity

    def calculate_refueling_stops(self, total_distance):
        max_distance = self.calculate_max_distance()
        return total_distance / max_distance

 
# Example usage
if __name__ == "__main__":
    # Define car 1 with its parameters
    carsData = {}   
    carsDataList = []
    for index, row in car_petrol_df.iterrows():
        car_petrol_df['ENGINE L']
        car_petrol_df['FUEL TANK L']
        car_petrol_df['MAKE']

        carsData['name'] = row['MAKE']
        carsData['fuel_efficiency'] = row['FUEL TANK L']
        carsData['tank_capacity'] = row['ENGINE L']
        
        carsDataList.append(carsData)
    print(carsDataList)    
    # Serialize the dictionary to a JSON string
    city_coordinates_json = json.dumps(carsDataList)
    python_socket.sendall(city_coordinates_json.encode('utf-8'))

# Close the socket
python_socket.close()   
    # Choosing the car requiring fewer refueling stops
    #if refueling_stops_car1 < refueling_stops_car2:
        #print(f"Hence, {car1.name} is expected to be more suitable for this trip.")
    #elif refueling_stops_car1 > refueling_stops_car2:
        #print(f"Hence, {car2.name} is expected to be more suitable for this trip.")
    #else:
        #print("Both cars are expected to require the same number of refueling stops.")