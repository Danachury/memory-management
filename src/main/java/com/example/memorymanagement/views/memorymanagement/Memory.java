package com.example.memorymanagement.views.memorymanagement;

public final class Memory {

    public static final int MB = 1024 * 1024 * 1024;
    public static final short MIN_SIZE = 2;
    public static final short DEFAULT_SIZE = 8;
    public static final short MAX_SIZE = memorySize();

    private Memory() {
    }

    private static short memorySize() {
        final var usableMemory = Math.round((double) Runtime.getRuntime().maxMemory() / MB);
        if (usableMemory < MIN_SIZE)
            return DEFAULT_SIZE;
        return (short) usableMemory;
    }
}
