[1mdiff --git a/bin/com/trackback/raspirator/Main.class b/bin/com/trackback/raspirator/Main.class[m
[1mindex 6333a78..2d9b7dc 100644[m
Binary files a/bin/com/trackback/raspirator/Main.class and b/bin/com/trackback/raspirator/Main.class differ
[1mdiff --git a/bin/com/trackback/raspirator/console/Command.class b/bin/com/trackback/raspirator/console/Command.class[m
[1mindex 6f6dd33..9703b4f 100644[m
Binary files a/bin/com/trackback/raspirator/console/Command.class and b/bin/com/trackback/raspirator/console/Command.class differ
[1mdiff --git a/bin/com/trackback/raspirator/console/Interpreter.class b/bin/com/trackback/raspirator/console/Interpreter.class[m
[1mindex 4bf4a98..0a5aa4a 100644[m
Binary files a/bin/com/trackback/raspirator/console/Interpreter.class and b/bin/com/trackback/raspirator/console/Interpreter.class differ
[1mdiff --git a/bin/com/trackback/raspirator/console/commands/Help.class b/bin/com/trackback/raspirator/console/commands/Help.class[m
[1mindex 879ac57..9cbab24 100644[m
Binary files a/bin/com/trackback/raspirator/console/commands/Help.class and b/bin/com/trackback/raspirator/console/commands/Help.class differ
[1mdiff --git a/bin/com/trackback/raspirator/hardware/gpio/Gpio.class b/bin/com/trackback/raspirator/hardware/gpio/Gpio.class[m
[1mindex 695e4ed..8962c3a 100644[m
Binary files a/bin/com/trackback/raspirator/hardware/gpio/Gpio.class and b/bin/com/trackback/raspirator/hardware/gpio/Gpio.class differ
[1mdiff --git a/bin/com/trackback/raspirator/resources/values/commands.list b/bin/com/trackback/raspirator/resources/values/commands.list[m
[1mdeleted file mode 100644[m
[1mindex 54d3580..0000000[m
[1m--- a/bin/com/trackback/raspirator/resources/values/commands.list[m
[1m+++ /dev/null[m
[36m@@ -1 +0,0 @@[m
[31m-help,drive[m
\ No newline at end of file[m
[1mdiff --git a/bin/com/trackback/raspirator/server/Server.class b/bin/com/trackback/raspirator/server/Server.class[m
[1mindex 405a152..5d6bc07 100644[m
Binary files a/bin/com/trackback/raspirator/server/Server.class and b/bin/com/trackback/raspirator/server/Server.class differ
[1mdiff --git a/bin/com/trackback/raspirator/server/ServerAdmin.class b/bin/com/trackback/raspirator/server/ServerAdmin.class[m
[1mindex d85c1e0..b3d09c0 100644[m
Binary files a/bin/com/trackback/raspirator/server/ServerAdmin.class and b/bin/com/trackback/raspirator/server/ServerAdmin.class differ
[1mdiff --git a/bin/com/trackback/raspirator/settings/Settings.class b/bin/com/trackback/raspirator/settings/Settings.class[m
[1mindex 7e055a2..9778f69 100644[m
Binary files a/bin/com/trackback/raspirator/settings/Settings.class and b/bin/com/trackback/raspirator/settings/Settings.class differ
[1mdiff --git a/bin/com/trackback/raspirator/system/Boot.class b/bin/com/trackback/raspirator/system/Boot.class[m
[1mindex 2bb583f..372272d 100644[m
Binary files a/bin/com/trackback/raspirator/system/Boot.class and b/bin/com/trackback/raspirator/system/Boot.class differ
[1mdiff --git a/bin/com/trackback/raspirator/tools/BaseFunction.class b/bin/com/trackback/raspirator/tools/BaseFunction.class[m
[1mindex be64f17..584fc57 100644[m
Binary files a/bin/com/trackback/raspirator/tools/BaseFunction.class and b/bin/com/trackback/raspirator/tools/BaseFunction.class differ
[1mdiff --git a/bin/com/trackback/raspirator/tools/Life.class b/bin/com/trackback/raspirator/tools/Life.class[m
[1mindex 672400e..d17614b 100644[m
Binary files a/bin/com/trackback/raspirator/tools/Life.class and b/bin/com/trackback/raspirator/tools/Life.class differ
[1mdiff --git a/src/com/trackback/raspirator/Main.java b/src/com/trackback/raspirator/Main.java[m
[1mindex d9b43f4..dfd2090 100644[m
[1m--- a/src/com/trackback/raspirator/Main.java[m
[1m+++ b/src/com/trackback/raspirator/Main.java[m
[36m@@ -3,17 +3,26 @@[m [mpackage com.trackback.raspirator;[m
 import javafx.application.Application;[m
 import javafx.stage.Stage;[m
 [m
[32m+[m[32mimport com.trackback.raspirator.settings.Settings;[m
 import com.trackback.raspirator.system.Boot;[m
 [m
 public class Main extends Application {[m
[31m-	[m
[32m+[m	[32mprivate static int port = Settings.SERVER_PORT;[m
 	public static void main(String[] args) {[m
[32m+[m		[32mfor (int i = 0; i < args.length; i++) {[m
[32m+[m			[32mif(args[i].equals("-p")){[m
[32m+[m				[32mif(args.length > i + 1){[m
[32m+[m					[32mint newPort = Integer.parseInt(args[i + 1]);[m
[32m+[m					[32mif(newPort > 1000) port = newPort;[m
[32m+[m				[32m}[m
[32m+[m			[32m}[m
[32m+[m		[32m}[m
 		launch(args);[m
 	}[m
 [m
 	@Override[m
 	public void start(Stage arg0){[m
[31m-		Boot boot = new Boot();[m
[32m+[m		[32mnew Boot(port);[m
 	}[m
 [m
 }[m
[1mdiff --git a/src/com/trackback/raspirator/console/Command.java b/src/com/trackback/raspirator/console/Command.java[m
[1mindex 6bf22a3..1ddb0c2 100644[m
[1m--- a/src/com/trackback/raspirator/console/Command.java[m
[1m+++ b/src/com/trackback/raspirator/console/Command.java[m
[36m@@ -23,5 +23,9 @@[m [mpublic class Command {[m
 	public List<String> getCommandsList(){[m
 		return interpreter.getCommandsIndex();[m
 	}[m
[32m+[m[41m	[m
[32m+[m	[32mpublic CommandListner getListener(){[m
[32m+[m		[32mreturn listener;[m
[32m+[m	[32m}[m
 [m
 }[m
[1mdiff --git a/src/com/trackback/raspirator/console/Interpreter.java b/src/com/trackback/raspirator/console/Interpreter.java[m
[1mindex fa5ad3e..31740fa 100644[m
[1m--- a/src/com/trackback/raspirator/console/Interpreter.java[m
[1m+++ b/src/com/trackback/raspirator/console/Interpreter.java[m
[36m@@ -1,10 +1,14 @@[m
 package com.trackback.raspirator.console;[m
 [m
 import java.util.ArrayList;[m
[32m+[m[32mimport java.util.Iterator;[m
 import java.util.List;[m
 [m
[32m+[m[32mimport com.trackback.raspirator.console.commands.Exec;[m
 import com.trackback.raspirator.console.commands.Help;[m
[32m+[m[32mimport com.trackback.raspirator.constants.ConstantsArgs;[m
 import com.trackback.raspirator.server.onServerGetRequest;[m
[32m+[m[32mimport com.trackback.raspirator.settings.Settings;[m
 import com.trackback.raspirator.system.Boot;[m
 import com.trackback.raspirator.tools.D;[m
 [m
[36m@@ -21,9 +25,9 @@[m [mpublic class Interpreter implements onServerGetRequest, CommandListner{[m
 	}[m
 	[m
 	private void prepareCommandsIndex(){[m
[31m-		String index = Boot.bf.getStringFromFile("values/commands.list");[m
[31m-		String[] splited = index.split(",");[m
[31m-		for (String string : splited) {[m
[32m+[m		[32mString[] splited = ConstantsArgs.commands.split(",");[m
[32m+[m		[32mfor (int i = 0; i < splited.length; i++) {[m
[32m+[m			[32mString string = splited[i];[m
 			D.log("Add to index "+string);[m
 			commandsList.add(string.trim());[m
 		}[m
[36m@@ -81,12 +85,27 @@[m [mpublic class Interpreter implements onServerGetRequest, CommandListner{[m
 			case 0:[m
 				new Help(this);[m
 				break;[m
[31m-[m
[32m+[m			[32mcase 1:[m
[32m+[m				[32mExec e = new Exec(this);[m
[32m+[m				[32mString str = Boot.bf.join(" ",args);[m
[32m+[m				[32mstr = str.replace("exec ", "");[m
[32m+[m				[32me.exec(str);[m
[32m+[m				[32mbreak;[m
[32m+[m			[32mcase 2:[m
[32m+[m				[32mbridg.sendResponseToClient("Raspirator version is "+Settings.ver);[m
[32m+[m				[32mbreak;[m
 			default:[m
[32m+[m				[32mbridg.sendResponseToClient("What do you want? Ha!?");[m
 				break;[m
 			}[m
 		}else{[m
[31m-			bridg.sendResponseToClient("Command not found");[m
[32m+[m			[32mbridg.sendResponseToClient("Command "+args[0]+" not found \n Pleas, type help to get commands list "+commandsList.size());[m
[32m+[m			[32mIterator<String> it = commandsList.iterator();[m
[32m+[m[41m			[m
[32m+[m			[32mwhile(it.hasNext()){[m
[32m+[m				[32mbridg.sendResponseToClient(it.next());[m
[32m+[m			[32m}[m
[32m+[m[41m			[m
 		}[m
 	}[m
 [m
[1mdiff --git a/src/com/trackback/raspirator/console/commands/Help.java b/src/com/trackback/raspirator/console/commands/Help.java[m
[1mindex 06c411b..fc46264 100644[m
[1m--- a/src/com/trackback/raspirator/console/commands/Help.java[m
[1m+++ b/src/com/trackback/raspirator/console/commands/Help.java[m
[36m@@ -17,7 +17,7 @@[m [mpublic class Help extends Command{[m
 		String dsc = Boot.bf.getStringFromFile("values/help.dsc");[m
 		sendToClient(dsc);[m
 		[m
[31m-		Iterator iterator = getCommandsList().iterator();[m
[32m+[m		[32mIterator<String> iterator = getCommandsList().iterator();[m
 		[m
 		while(iterator.hasNext()){[m
 			sendToClient(iterator.next()+" \n");[m
[1mdiff --git a/src/com/trackback/raspirator/hardware/gpio/Gpio.java b/src/com/trackback/raspirator/hardware/gpio/Gpio.java[m
[1mindex 8d5c8af..b0f7baa 100644[m
[1m--- a/src/com/trackback/raspirator/hardware/gpio/Gpio.java[m
[1m+++ b/src/com/trackback/raspirator/hardware/gpio/Gpio.java[m
[36m@@ -5,9 +5,19 @@[m [mimport com.pi4j.io.gpio.GpioFactory;[m
 [m
 public class Gpio {[m
 	private GpioController gController;[m
[32m+[m	[32mprivate GpioIOListner listener;[m
 	[m
 	public Gpio() {[m
 		gController = GpioFactory.getInstance();[m
 	}[m
[32m+[m[41m	[m
[32m+[m	[32mpublic void setListener(GpioIOListner listner){[m
[32m+[m		[32mthis.listener = listner;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
[32m+[m	[32mpublic boolean isListned(){[m
[32m+[m		[32mreturn (listener == null)? false : true;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
 [m
 }[m
[1mdiff --git a/src/com/trackback/raspirator/server/Server.java b/src/com/trackback/raspirator/server/Server.java[m
[1mindex 2e6989f..8d13e5c 100644[m
[1m--- a/src/com/trackback/raspirator/server/Server.java[m
[1m+++ b/src/com/trackback/raspirator/server/Server.java[m
[36m@@ -47,7 +47,12 @@[m [mclass Server extends Thread implements ServerBridg {[m
 				}[m
 				D.log(TAG, "Master sad: " + data);[m
 				if (data.equals("exit")) {[m
[32m+[m					[32mD.log(TAG, "Good by!");[m
[32m+[m					[32mbreak;[m
[32m+[m				[32m}[m
[32m+[m				[32mif(data.equals("die")){[m
 					D.log(TAG, "Die");[m
[32m+[m					[32mSystem.exit(0);[m
 					break;[m
 				}[m
 			}[m
[36m@@ -74,9 +79,14 @@[m [mclass Server extends Thread implements ServerBridg {[m
 			String[] args = data.split("\\r?\\n");[m
 			for (String string : args) {[m
 				D.log(string);[m
[31m-				os.write(string.getBytes());	[m
[32m+[m				[32mif(string.getBytes() != null && os != null){[m
[32m+[m					[32mos.write(string.getBytes());[m
[32m+[m				[32m}else{[m
[32m+[m					[32mSystem.out.println("Connection stil down!");[m
[32m+[m				[32m}[m
[32m+[m[41m						[m
 			}[m
[31m-		} catch (IOException e) {[m
[32m+[m		[32m} catch (Exception e) {[m
 			D.log("Master can't hear me");[m
 			e.printStackTrace();[m
 		}[m
[1mdiff --git a/src/com/trackback/raspirator/server/ServerAdmin.java b/src/com/trackback/raspirator/server/ServerAdmin.java[m
[1mindex 7de9f6f..a35d4fc 100644[m
[1m--- a/src/com/trackback/raspirator/server/ServerAdmin.java[m
[1m+++ b/src/com/trackback/raspirator/server/ServerAdmin.java[m
[36m@@ -10,10 +10,10 @@[m [mimport com.trackback.raspirator.tools.D;[m
 public class ServerAdmin {[m
 	[m
 [m
[31m-    public static void init(Interpreter interpreter){[m
[32m+[m[32m    public static void init(Interpreter interpreter, int port){[m
         try{[m
             int i = 0;[m
[31m-            ServerSocket server = new ServerSocket(Settings.SERVER_PORT, 0, InetAddress.getByName("localhost"));[m
[32m+[m[32m            ServerSocket server = new ServerSocket(port, 0, InetAddress.getByName("localhost"));[m
 [m
             D.log("Black Admin", "I'm waiting for the master");[m
             while(true){[m
[1mdiff --git a/src/com/trackback/raspirator/settings/Settings.java b/src/com/trackback/raspirator/settings/Settings.java[m
[1mindex 67b71ac..2f82bc3 100644[m
[1m--- a/src/com/trackback/raspirator/settings/Settings.java[m
[1m+++ b/src/com/trackback/raspirator/settings/Settings.java[m
[36m@@ -3,4 +3,5 @@[m [mpackage com.trackback.raspirator.settings;[m
 public final class Settings {[m
 	public static final boolean debug = true;[m
 	public static final int SERVER_PORT = 4000;[m
[32m+[m	[32mpublic static final String ver = "0.0.1 #1";[m
 }[m
[1mdiff --git a/src/com/trackback/raspirator/system/Boot.java b/src/com/trackback/raspirator/system/Boot.java[m
[1mindex c834813..02d144b 100644[m
[1m--- a/src/com/trackback/raspirator/system/Boot.java[m
[1m+++ b/src/com/trackback/raspirator/system/Boot.java[m
[36m@@ -16,14 +16,14 @@[m [mpublic class Boot {[m
 	public static Interpreter interpreter;[m
 	public static BaseFunction bf;[m
 	[m
[31m-	public Boot() {[m
[32m+[m	[32mpublic Boot(int port) {[m
 		D.log(TAG, "Initializing...");[m
 		bf = new BaseFunction();[m
 		life = new Life();[m
 		hw = new Hardware();[m
 		actions = new Actions();[m
 		interpreter = new Interpreter();[m
[31m-		ServerAdmin.init(interpreter);[m
[32m+[m		[32mServerAdmin.init(interpreter, port);[m
 		onCreate();	[m
 	}[m
 	[m
[1mdiff --git a/src/com/trackback/raspirator/tools/BaseFunction.java b/src/com/trackback/raspirator/tools/BaseFunction.java[m
[1mindex 58cb4c5..beb790e 100644[m
[1m--- a/src/com/trackback/raspirator/tools/BaseFunction.java[m
[1m+++ b/src/com/trackback/raspirator/tools/BaseFunction.java[m
[36m@@ -1,12 +1,10 @@[m
 package com.trackback.raspirator.tools;[m
 [m
[31m-import java.io.BufferedReader;[m
 import java.io.File;[m
 import java.io.FileNotFoundException;[m
[31m-import java.io.FileReader;[m
 import java.io.FileWriter;[m
[31m-import java.io.IOException;[m
 import java.io.PrintWriter;[m
[32m+[m[32mimport java.util.Scanner;[m
 import java.util.prefs.BackingStoreException;[m
 import java.util.prefs.Preferences;[m
 [m
[36m@@ -14,9 +12,7 @@[m [mimport com.trackback.raspirator.Main;[m
 [m
 public class BaseFunction {[m
 	private Preferences pref = Preferences.systemNodeForPackage(getClass());[m
[31m-	[m
 	public BaseFunction() {[m
[31m-[m
 	}[m
 [m
 	/**[m
[36m@@ -61,28 +57,6 @@[m [mpublic class BaseFunction {[m
 		}[m
 	}[m
 [m
[31m-	public BufferedReader readFromFile(String file) {[m
[31m-		File src = new File(Main.class.getResource("resources/" + file).getPath());[m
[31m-		BufferedReader br = null;[m
[31m-		try {[m
[31m-			br = new BufferedReader(new FileReader(src));[m
[31m-		} catch (FileNotFoundException e) {[m
[31m-			e.printStackTrace();[m
[31m-		}[m
[31m-		return br;[m
[31m-	}[m
[31m-	[m
[31m-	public void clearFile(String file){[m
[31m-		PrintWriter writer;[m
[31m-		try {[m
[31m-			writer = new PrintWriter(new File(Main.class.getResource("resources/"+file).getPath()));[m
[31m-			writer.write("");[m
[31m-			writer.close();[m
[31m-		} catch (FileNotFoundException e) {[m
[31m-			e.printStackTrace();[m
[31m-		}[m
[31m-	}[m
[31m-[m
 	public int string2Int(String string) {[m
 		return Integer.parseInt(string);[m
 	}[m
[36m@@ -151,19 +125,29 @@[m [mpublic class BaseFunction {[m
 		return joined;[m
 	}[m
 	[m
[32m+[m	[32mpublic static String readFileAsString(String file) {[m
[32m+[m		[32mString result = "";[m
[32m+[m		[32mtry {[m
[32m+[m			[32mresult = new Scanner(new File(file)).useDelimiter("[\\r\\n]+").next();[m
[32m+[m		[32m} catch (FileNotFoundException e) {[m
[32m+[m			[32me.printStackTrace();[m
[32m+[m		[32m}[m
[32m+[m		[32mreturn result;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
 	public String getStringFromFile(String path) {[m
[31m-		BufferedReader r = readFromFile(path);[m
[31m-		String line = "";[m
[32m+[m[41m        [m
 		String upend = "";[m
[31m-		if (r != null) {[m
[31m-			try {[m
[31m-				while ((line = r.readLine()) != null) {[m
[31m-					upend += line;[m
[31m-				}[m
[31m-			} catch (IOException e) {[m
[31m-				e.printStackTrace();[m
[31m-			}[m
[31m-		}[m
[32m+[m	[32m    try {[m
[32m+[m	[32m        Scanner s = new Scanner(new File(Main.class.getResource("resources/"+ path).getPath())).useDelimiter("/n");[m
[32m+[m	[32m        while (s.hasNext()) {[m
[32m+[m	[41m        [m	[32mupend += s.next();[m
[32m+[m	[32m        }[m
[32m+[m	[32m    } catch (Exception ex) {[m
[32m+[m	[32m        System.err.println(ex);[m
[32m+[m	[32m        ex.printStackTrace();[m
[32m+[m	[32m    }[m
 		return upend;[m
 	}[m
[31m-}[m
[32m+[m[41m	[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/com/trackback/raspirator/tools/Life.java b/src/com/trackback/raspirator/tools/Life.java[m
[1mindex 0ac4fcb..3bd5596 100644[m
[1m--- a/src/com/trackback/raspirator/tools/Life.java[m
[1m+++ b/src/com/trackback/raspirator/tools/Life.java[m
[36m@@ -5,7 +5,7 @@[m [mimport java.util.List;[m
 [m
 public class Life {[m
 	private List<LifeListener> listeners = new ArrayList<LifeListener>();[m
[31m-	private final String TAG = "Life";[m
[32m+[m[32m//	private final String TAG = "Life";[m
 	[m
 	public Life() {[m
 	}[m
