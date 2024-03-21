package com.cc.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
<<<<<<< HEAD:project/server/business/src/main/java/com/cc/business/BusinessApplication.java
@EnableFeignClients
public class BusinessApplication {
=======
public class SearchApplication {
>>>>>>> cd5e4feb64111f9252e36f68a97a905549467398:project/server/search/src/main/java/com/cc/search/SearchApplication.java

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
