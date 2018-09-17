package com.gitee.taven.runner;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gitee.taven.entity.User;
import com.gitee.taven.service.GitHubLookupService;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
   	   // Start the clock
       long start = System.currentTimeMillis();

       // Kick of multiple, asynchronous lookups
       CompletableFuture<User> page1 = gitHubLookupService.findByAsync("PivotalSoftware");
       CompletableFuture<User> page2 = gitHubLookupService.findByAsync("CloudFoundry");
       CompletableFuture<User> page3 = gitHubLookupService.findByAsync("Spring-Projects");

       // Wait until they are all done
       CompletableFuture.allOf(page1,page2,page3).join();

       // Print results, including elapsed time
       float exc = (float) (System.currentTimeMillis() - start)/1000;
       logger.info("Elapsed time: " + exc + " seconds");
       logger.info("--> " + page1.get());
       logger.info("--> " + page2.get());
       logger.info("--> " + page3.get());
    }

    
}