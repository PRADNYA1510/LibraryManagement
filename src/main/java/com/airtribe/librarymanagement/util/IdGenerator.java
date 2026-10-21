package com.airtribe.librarymanagement.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger loanCounter = new AtomicInteger(100);

    public static String generateLoanId() {
        return "L" + loanCounter.incrementAndGet();
    }
}
