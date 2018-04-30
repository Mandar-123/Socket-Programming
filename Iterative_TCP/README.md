## Example
- Host a server at port 55555

  java Server 55555

- Make Simultaneous requests from 2 clients to the server

  java Client localhost 55555
  java Client localhost 55555


- OUTPUT

  - Client 1: // Request processed first
  <p align="center"><img src="Iterative_TCP/Res/1.png" width="80%"></p>
  
  - Client 2: // Request is processed after request from first client is processed
  <p align="center"><img src="Iterative_TCP/Res/1.png" width="80%"></p>
