package org.ahdb;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusMain
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        Quarkus.run(AhdbServer.class, args);
    }

    public static class AhdbServer implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            log.info("application started {}", "joe");
            // TODO: write the fucking pid file
            Quarkus.waitForExit();
            return 0;
        }
    }
}
