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
    @DisplayName("Test01 : QPC1 TEST wait")
    void Test01() throws Exception {

        // 1ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc1_bench(1);
        }

        // 10ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc1_bench(10);
        }

        // 100ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc1_bench(100);
        }

        // 1000ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc1_bench(1000);
        }
    }

    @Test
    @DisplayName("Test02 : QPC1 TEST wait by noSleep")
    void Test02() throws Exception {

        // 1ms
        for (int i = 0; i < 1000; i++) {
            wait_noSleep_bench(1);
        }

        // 10ms
        for (int i = 0; i < 1000; i++) {
            wait_noSleep_bench(10);
        }

        // 100ms
        for (int i = 0; i < 1000; i++) {
            wait_noSleep_bench(100);
        }

        // 1000ms
        for (int i = 0; i < 1000; i++) {
            wait_noSleep_bench(1000);
        }
    }

    @Test
    @DisplayName("Test03 : QPC1 TEST wait by nanoTime")
    void Test03() throws Exception {

        // 1ms
        for (int i = 0; i < 1000; i++) {
            wait_nanoTime_bench(1);
        }

        // 10ms
        for (int i = 0; i < 1000; i++) {
            wait_nanoTime_bench(10);
        }

        // 100ms
        for (int i = 0; i < 1000; i++) {
            wait_nanoTime_bench(100);
        }

        // 1000ms
        for (int i = 0; i < 1000; i++) {
            wait_nanoTime_bench(1000);
        }
    }

    @Test
    @DisplayName("Test04 : QPC2 TEST wait")
    void Test04() throws Exception {

        // 1ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc2_bench(1);
        }

        // 10ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc2_bench(10);
        }

        // 100ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc2_bench(100);
        }

        // 1000ms
        for (int i = 0; i < 1000; i++) {
            wait_qpc2_bench(1000);
        }
    }

    public static void wait_qpc1_bench(int msec) {
        long start = System.nanoTime();

        com.example.qpc.QPC1.wait(msec);

        long end = System.nanoTime();
        System.out.println((end - start));
    }
    
    public static void wait_noSleep_bench(int msec) {
        long start = System.nanoTime();

        com.example.qpc.QPC1.wait_noSleep(msec);

        long end = System.nanoTime();
        System.out.println((end - start));
    }

    public static void wait_nanoTime_bench(int msec) {
        long start = System.nanoTime();

        com.example.qpc.QPC1.wait_nanoTime(msec);

        long end = System.nanoTime();
        System.out.println((end - start));
    }

    public static void wait_qpc2_bench(int msec) {
        long start = System.nanoTime();

        com.example.qpc.QPC2.wait(msec);

        long end = System.nanoTime();
        System.out.println((end - start));
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }

}