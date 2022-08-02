package com.example.qpc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class test {
    private static final int OK = 0;
    private static final int ERROR = -1;
    int iResult = ERROR;

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() throws Exception {
        iResult = ERROR;
    }

    @Test
    @DisplayName("Test01 : QPC1 wait test")
    void Test01() throws Exception {

        long start = System.nanoTime();

        com.example.qpc.QPC1.wait(10);

        long end = System.nanoTime();
        System.out.println((end - start));
    }

    @Test
    @DisplayName("Test02 : QPC1 wait_noSleep test")
    void Test02() throws Exception {

        long start = System.nanoTime();

        com.example.qpc.QPC1.wait_noSleep(10);

        long end = System.nanoTime();
        System.out.println((end - start));
    }

    @Test
    @DisplayName("Test03 : QPC1 wait_nanoTime test")
    void Test03() throws Exception {

        // 1ms
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

            com.example.qpc.QPC1.wait_nanoTime(1);

            long end = System.nanoTime();
            System.out.println((end - start));
        }

        // 10ms
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

            com.example.qpc.QPC1.wait_nanoTime(10);

            long end = System.nanoTime();
            System.out.println((end - start));
        }

        // 100ms
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

            com.example.qpc.QPC1.wait_nanoTime(100);

            long end = System.nanoTime();
            System.out.println((end - start));
        }

        // 1000ms
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

            com.example.qpc.QPC1.wait_nanoTime(1000);

            long end = System.nanoTime();
            System.out.println((end - start));
        }
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }

}