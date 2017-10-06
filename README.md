# hardwareswap-monitor
hardwareswap-monitor is a Java program that pulls the 25 most recent posts from https://www.reddit.com/r/hardwareswap/ and checks for posts relevant to a provided list of key words.

To execute hardwareswap-monitor:
	1.  Log in to Reddit.
	2.  Navigate to https://www.reddit.com/prefs/apps/.
	3.  Create an application:
		a.  Give the application a name.
		b.  Select script as the application type.
		c.  Put http://localhost:8080 as the redirect uri.
		d.  Click create app.  A client_id and client_secret will be generated.
	4.  Clone hardwareswap-monitor and export it as a .jar file.

java -jar ./hardwareswap-monitor.jar client_id client_secret
