import socket

# Connect to the Java server
java_server_address = ('localhost', 9876)
python_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
python_socket.connect(java_server_address)

# Send a message to the Java server
message = "Hello from Python!"
python_socket.sendall(message.encode('utf-8'))

# Close the socket
python_socket.close()