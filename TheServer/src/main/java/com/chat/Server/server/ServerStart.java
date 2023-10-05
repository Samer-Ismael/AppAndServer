package com.chat.Server.server;

import java.io.IOException;

public class ServerStart {
    public ServerStart (String path){
        start(path);
    }

    private void start(String path) {

        try {
            System.out.println("Wait... It's working but it needs about 10 seconds :)");
            System.out.println("Trust me it's worth it, you don't want to connect the app to a non-working server");
            // Base directory where Kafka and ZooKeeper are installed
            String kafkaFilePath = path;

            ProcessBuilder zookeeperProcessBuilder = new ProcessBuilder(
                    kafkaFilePath + "\\bin\\windows\\zookeeper-server-start.bat",
                    kafkaFilePath + "\\config\\zookeeper.properties"
            );
            zookeeperProcessBuilder.redirectErrorStream(true);

            ProcessBuilder kafkaProcessBuilder = new ProcessBuilder(
                    "\"" + kafkaFilePath + "\\bin\\windows\\kafka-server-start.bat\"",
                    "\"" + kafkaFilePath + "\\config\\server.properties\""
            );
            kafkaProcessBuilder.redirectErrorStream(true);

            Process zookeeperProcess = zookeeperProcessBuilder.start();
            Process kafkaProcess = kafkaProcessBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverStop(String path) {
        try {
            ProcessBuilder zookeeperProcessBuilder = new ProcessBuilder(
                    path + "\\bin\\windows\\zookeeper-server-stop.bat",
                    path + "\\config\\zookeeper.properties"
            );
            zookeeperProcessBuilder.redirectErrorStream(true);
            Process zookeeperProcess = zookeeperProcessBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
