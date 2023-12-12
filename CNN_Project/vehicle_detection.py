# Importting necessary libraries
import pandas as pd
import numpy as np
import socket
import requests
import json
import matplotlib.pyplot as plt
import seaborn as sns
import matplotlib.gridspec as gridspec

java_server_address = ('localhost', 9877)
python_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
python_socket.connect(java_server_address)
car_petrol_df = pd.read_csv('C:/Users/HP/Downloads/archive (1)/PETROL.csv')

if __name__ == "__main__":
    print(car_petrol_df)
    # Checking the null values 
    car_petrol_df.isnull().sum()
    # Handling missing value
    car_petrol_df.dropna(inplace=True)
    df_no_duplicates = car_petrol_df.drop_duplicates()
    # Handling miss data type
    def convert_str_num(df, col_name):
        new_col = []
        for i in df[col_name]:
            new_col.append(int(i.replace(',', '')))
        df[col_name] = new_col
     
    convert_str_num(df_no_duplicates,'RANGE km')
    print(df_no_duplicates['MAKE'].value_counts())
    print(df_no_duplicates['MAKE'].value_counts().index)

    counts = df_no_duplicates['MAKE'].value_counts()
    x = counts.index
    y = counts.values

    plt.figure(figsize=(10, 10))
    ax1 = plt.subplot(2, 1, 1)
    ax1 = sns.barplot(x=x, y=y)
    ax1.set_title("Distribution of Car -- Petrol")
    plt.xticks(rotation=90)

    xPie = car_petrol_df.TYPE.value_counts()

    fig = plt.figure(figsize=(10,6),tight_layout=True)
    gs = gridspec.GridSpec(1, 2)
    ax1 = fig.add_subplot(gs[0, 0])
    ax1.pie(xPie, labels=xPie.index, autopct='%.2f%%', shadow=True, radius=0.75)
    ax1.set_title("Petrol vehicle")

    plt.show()

    carsDataList = []
    for index, row in df_no_duplicates.iterrows():

        carsData = {}   
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