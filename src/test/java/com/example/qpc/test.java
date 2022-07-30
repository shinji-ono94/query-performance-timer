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
    @DisplayName("Test01 : QPC1, main OK")
    void Test01() throws Exception {

        long start = System.nanoTime();

        String args[] = {};
        // com.example.qpc.qpc1.QPC1.main(args);

        long end = System.nanoTime();
        System.out.println((end - start));

    }

    @Test
    @DisplayName("Test02 : QPC2, main OK")
    void Test02() throws Exception {

        long start = System.nanoTime();

        String args[] = {};
        // QPC2.main(args);

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