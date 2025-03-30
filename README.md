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
1. Compile and run the server:
   ```bash
   javac BasicServer.java
   java BasicServer
