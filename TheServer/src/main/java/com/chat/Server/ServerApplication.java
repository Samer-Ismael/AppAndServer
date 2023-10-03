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
		SpringApplication.run(ServerApplication.class, args);
		//server.serverStop(serverPath);

	}
}
