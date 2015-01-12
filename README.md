# Raspirator
Hopefully, once it becomes a robot. Platform can be any OS with Java machine and GPIO output controller


I hope it will be something like remote control for dron projects with some logic.
It can be run on any platform with Java machine and maybe on Android in future.
Today futures list is:

- Console. It can send commands from client (Master) to server (Raspirator) and interpreted it in system terminal as system command (tunnel)
- Binding on GPIO. Use it for dynamic binding GPIO i/o and commands.
- Streaming. Not yet

# History

[0.0.1]
+ Added port binding. Now you can set the port number for server like this:
`java -jar server.jar -p 4000`
+ Added execution systems commands. Like:
`exec ping 8.8.8.8`
+ Added connection controller. Now client notify you where connection will be lost.
+ Added command ver. Its tell you version of your server.
+ Fixed some bugs
