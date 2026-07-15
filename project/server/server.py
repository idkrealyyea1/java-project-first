import socket
import threading

active_clients = []
client_nicknames = {}
clients_lock = threading.Lock()

def broadcast(message, sender_socket):
    with clients_lock:
        clients_copy = active_clients.copy()

    for client in clients_copy:
        try:
            client.send((message + "\n").encode('utf-8'))
        except:
            with clients_lock:
                if client in active_clients:
                    active_clients.remove(client)

def handle_client(client_socket, address):
    nickname = None
    try:
        nickname = client_socket.recv(1024).decode('utf-8').strip()
        if not nickname:
            client_socket.close()
            return

        with clients_lock:
            active_clients.append(client_socket)
            client_nicknames[client_socket] = nickname

        print(f"{nickname} joined from {address[0]}:{address[1]}")
        broadcast(f"{nickname} joined the chat!", None)

        while True:
            message = client_socket.recv(4096).decode('utf-8').strip()
            if not message:
                break
            print(f"[{nickname}] {message}")
            broadcast(f"{nickname}: {message}", None)

    except Exception as e:
        print(f"Error from {address}: {e}")

    finally:
        if nickname:
            broadcast(f"{nickname} left the chat!", None)
            with clients_lock:
                if client_socket in active_clients:
                    active_clients.remove(client_socket)
                if client_socket in client_nicknames:
                    del client_nicknames[client_socket]
            try:
                client_socket.close()
            except:
                pass
            print(f"Connection closed with {address}")

def start_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind(("0.0.0.0", 5555))
    server_socket.listen()
    print("=" * 50)
    print("Server started on 0.0.0.0:5555")
    print("=" * 50)
    print("Waiting for connection...\n")

    try:
        while True:
            client_socket, address = server_socket.accept()
            print(f"New connection from {address[0]}:{address[1]}")
            client_thread = threading.Thread(
                target=handle_client, args=(client_socket, address), daemon=True
            )
            client_thread.start()
    except KeyboardInterrupt:
        print("\n\nServer shutting down..")
    finally:
        server_socket.close()
        print("Server closed")

if __name__ == "__main__":
    start_server()
