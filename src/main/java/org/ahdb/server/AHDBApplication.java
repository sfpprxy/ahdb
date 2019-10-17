package org.ahdb.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class AHDBApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AHDBApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("./AHDBPID"));
        springApplication.run(args);
    }

}
