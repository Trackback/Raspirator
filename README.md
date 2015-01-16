# Raspirator
Hopefully, once it becomes a robot. Platform can be any OS with Java machine and GPIO output controller


I hope it will be something like remote control for dron projects with some logic.
It can be run on any platform with Java machine and maybe on Android in future.
Today futures list is:

- Console. It can send commands from client (Master) to server (Raspirator) and interpreted it in system terminal as system command (tunnel)
- Binding on GPIO. Use it for dynamic binding GPIO i/o and commands.
- Streaming. Not yet

# How to

Run server on any OS Windows, Mac or Linux.
On Linux/Mac:
`sudo java -jar raspirator.jar -p 5000`
On Windows:
`java -jar raspirator.jar -p 5000`
or double click on jar file (will be used port 4000).
Where -p is port.

Then, run clien (see raspiratorMaster project) and set remote address and por of your raspirator
After that, you will see command console, where you can typing commands
Commands list:
`help` Show all commands supported of your server

`ver` Shows current version of server

`exec` Translate command to OS, like

`exec ping 8.8.8.8`

`top` Shows all executed commands

`top kill [pid]` or `kill [pid]` Destroy process with pid

`top killall` or `killall` Destroy all processes

`pin` Call GPIO manager. Supported flags:

  `-c` - create pin
  
  `pin -c -n [name] -s [state] -io [type] -i [pin number on board]`
  
  Statets can be: on/off, io type can be: input/output
  
  `-l` - show pins list
  
  `-e` - edit pin
  
  `pin -e 0 -n [name] -s [state] -io [type] -i [pin number on board]`
  
  `-r` - remove pin
  
  `pin -r 0`
  
  `-s` - save pins map
  
  `-L` - load saved pins map

# History

[0.0.2]
+ Added GPIO manager.
+ Fix saving data
+ Fixed bugs

[0.0.1]
+ Added port binding. Now you can set the port number for server like this:
`java -jar server.jar -p 4000`
+ Added execution systems commands. Like:
`exec ping 8.8.8.8`
+ Added connection controller. Now client notify you where connection will be lost.
+ Added command ver. Its tell you version of your server.
+ Fixed some bugs

