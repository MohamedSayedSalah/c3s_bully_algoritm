# cs3_bully_algoritm

This is an implementation of bully algorithm using sockets 

The main advantage from using sockets is that 
you can simulate multiple systems 
So using any Shared Memory technique for communication between processes is not an option 
So there are two ways for doing this 
- create  a central queue for all the processes and  every process consume the message from the central queue accordingly 
- processes communicate with each other directly 

-in order for each process to communicate directly it must act as a client and server at the same times 

-so every created process has 2 threads for listening for incoming requests and pushing message to other process 


- the main program start as follow: 
- creating n process with every process has 2 separate threads 
- each request will be handled separately using separate thread for each request to prevent any request from being discarded 
- in order to update the UI each process must talk back to the main server throw a message and the main process will update UI 
- if any process didnt get any request from its leader in Threshold second it will start an election process
- you could kill the current leader if any but you cant revive it back 
- if the process get killed  its server which is responsible for listining  for any incoming requests will be closed     
- the program will goes until all the processes get killed 
- when  user exit the program a message will be sent to the main server to kill all the running process "How ever sometimes this is not running perfectly " 

... enjoy 





