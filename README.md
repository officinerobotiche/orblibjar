# jorb_bridge
=============

**J**ava Library for serial communication with **O**fficine **R**obotiche **B**oards

From: [Arduino Page]

## RXTX Library Set Up
-----------
If you installed the Arduino IDE, you already have the RXTX library installed for the next steps, and you may be able to skip this section and start with the sample Java code. If you installed a separate copy of the RXTX, you can do one of the following:


Platform indepedent (not require for Linux with the new method)
-----------
When starting Java, use the **-Djava.library.path=** command line argument to state where the JNI libraries are located. For example, you could use:
```
	java -Djava.library.path=C:\rxtx-2.2pre2-bins\win32 SerialTest
```
You should also ensure that the RXTXcomm.jar is in your CLASSPATH.

Windows 32-bit
-----------
Copy the win32/rxtxSerial.dll into C:\Windows\System32
Append the directory containing rxtxSerial.dll into your PATH environment variable.

Windows 64-bit
-----------
Copy the win64/rxtxSerial.dll into C:\Windows\SysWOW64
Append the directory containing rxtxSerial.dll into your PATH environment variable.

Linux
-----------
Download the last version of the rxtx java library at http://rxtx.qbang.org/wiki/index.php/Download.
Extract the package, open extracted files, go to Linux and choose the folder which correspond to your system configuration.
Copy all the files from the previous folder to /jre/lib/[machine type] (i386 for instance).
Copy RXTXcomm.jar from the main folder to /jre/lib/ext.

Mac OS X
-----------
Copy RXTXcomm.jar from the main folder to /Library/Java/Extensions
Copy the mac-10.5/librxtxSerial.jnilib (or, if this version does not work for you, obtain a 64 bit compiled version from http://blog.iharder.net/2009/08/18/rxtx-java-6-and-librxtxserial-jnilib-on-intel-mac-os-x/) and paste into /Library/Java/Extensions
Append the directory containing librxtxSerial.jnilib files into your DYLD_LIBRARY_PATH environment variable
See the other notes on Mac OS X below the sample java code.


[Arduino Page]:http://playground.arduino.cc/Interfacing/Java