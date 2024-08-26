package br.com.maicon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartupTests {

    @Test
    void contextLoads() {}

    @Test
    void mainTest() {
        Startup.main(new String[] {});
    }
}
