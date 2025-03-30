# Java Socket File Transfer System

## Overview
A Java implementation of client-server communication with both basic messaging and advanced file transfer capabilities. The system demonstrates socket programming fundamentals and secure file operations.

## Project Files

### Basic Communication
- `BasicClient.java` - Simple client for text messaging
- `BasicServer.java` - Simple server for text responses

### File Transfer System
- `FileTransferServer.java` - Advanced server with file operations
- `FileTransferClient.java` - Advanced client with file transfer UI

## Features

### Basic Implementation
- Simple "hello world" style message exchange
- Server timeout after 10 seconds of inactivity
- Single client-server connection

### Advanced Implementation
- **File Download**: Clients can request files from server
- **File Upload**: Clients can send files to server
- Multi-threading (handles 10+ concurrent clients)
- Secure file operations with validation
- Interactive menu interface

## How to Run

### Basic Communication
1. Compile and run the server in /singleThreaded :
   ```bash
   javac Server.java
   java Server
2. In another terminal, compile and run the client in /singleThreaded
   ```bash
      javac Client.java
      java Client
### File Transfer System
1. First start the server:
   ```bash
   javac FileTransferServer.java
   java FileTransferServer
2.Then run the client (can run multiple instances):
   
    javac FileTransferServer.java
    java FileTransferServer



## Usage Instructions

### Basic System
1. **Start the Server**:
   - Run the `BasicServer` program first
   - You'll see: `server listening on port 8010`

2. **Start the Client**:
   - Run the `BasicClient` program in another terminal
   - Automatic message exchange occurs:
     ```
     Client → Server: "hello from the client"
     Server → Client: "Hello from the server"
     ```
   - Client displays: `Response from the Server : Hello from the server`

3. **Connection Closes**:
   - Both programs automatically close the connection after message exchange
   - Server continues listening for new connections

### File Transfer System

#### Menu Options:
1.Download file
2.Upload file
3.Exit
Choose option


#### Downloading Files:
1. Select option `1` and press Enter
2. Enter the filename you want to download from server (e.g., `notes.txt`)
3. The system will:
   - Send download request to server
   - Display file contents if found:
     ```
     File contents:
     Line 1 of notes
     Line 2 of notes
     File download complete
     ```
   - Show error if file doesn't exist

#### Uploading Files:
1. Select option `2` and press Enter
2. Enter:
   - Local file path (e.g., `C:\files\local.txt`)
   - Server filename to save as (e.g., `server_copy.txt`)
3. The system will:
   - Verify local file exists
   - Send file contents to server
   - Display confirmation:
     ```
     Server response: UPLOAD_SUCCESS
     ```
   - Show error if:
     - Local file not found
     - Filename invalid
     - File too large (>1MB)

#### Exiting:
- Select option `3` to gracefully exit the client
- Server continues running for other clients

#### Server Console During Operations:

[UPLOAD] Received: server_copy.txt (2048 bytes)
[DOWNLOAD] Sent: notes.txt to client
[ERROR] File not found: unknown.txt


#### Important Notes:
- Server must be running before starting clients
- Files are stored in `server_files/` directory
- Maximum 10 simultaneous connections
- Files over 1MB will be rejected
- Use only alphanumeric filenames (a-z, 0-9, ._-)

  
