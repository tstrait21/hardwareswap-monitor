# hardwareswap-monitor
hardwareswap-monitor is a Java program that pulls the 25 most recent posts from https://www.reddit.com/r/hardwareswap/ and checks for posts relevant to a provided list of key words.

In order to execute hardwareswap-monitor, you need to be able to authenticate to Reddit's API with OAuth 2 credentials.  To generate credentials, follow these steps:<br />
   1.  Log in to Reddit.<br />
   2.  Navigate to https://www.reddit.com/prefs/apps/.<br />
   3.  Create an application:<br />
      a.  Give the application a name.<br />
      b.  Select script as the application type.<br />
      c.  Put http://localhost:8080 as the redirect uri.<br />
      d.  Click create app.  A client_id and client_secret will be generated.<br />

Clone hardwareswap-monitor and export it as a .jar file.<br />

java -jar ./hardwareswap-monitor.jar client_id client_secret
