package com.chat.Server;
import com.chat.Server.server.ServerStart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {


		// Replace the serverPath with the path to the kafka file you downloaded
		// It should look like this String serverPath = " your server path ";
		String serverPath = "C:\\Users\\samer\\Downloads\\kafka";
		ServerStart server = new ServerStart(serverPath);

		try {
			// To give som time för the kafka server to start.
			Thread.sleep(10 * 1000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}




		SpringApplication.run(ServerApplication.class, args);
		//server.serverStop(serverPath);

	}
}
