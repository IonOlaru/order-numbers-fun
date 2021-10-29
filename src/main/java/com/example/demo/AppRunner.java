package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class AppRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private static final int THREAD_POOL_SIZE = 3;
    private static final int N_RECORDS = 10;

    private final TransactionsService transactionsService;
    private final ExecutorService executorService;

    private static final String[] prefixes = new String[] {"AB"};
    // private static final String[] prefixes = new String[] {"AB", "CD", "EF", "FG"};
    Random r = new Random();

    public AppRunner(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    private void produceWork() {
        for (int i = 1; i <= N_RECORDS; i++) {
            String prefix = prefixes[r.nextInt(prefixes.length)];
            executorService.execute(() -> transactionsService.insertNewRecord(prefix));
        }
    }

    private void shutDown() throws InterruptedException {
        executorService.shutdown();
        System.out.println("Waiting for threads to shutdown.");

        boolean terminated = executorService.awaitTermination(10, TimeUnit.MINUTES);

        if (terminated)
            System.out.println("ALL threads finished work OK.");
        else
            System.out.println("Threads did not finish work.");

        System.out.println("OK.");
    }

    @Override
    public void run(String... args) throws InterruptedException {
        produceWork();
        shutDown();
    }

}
