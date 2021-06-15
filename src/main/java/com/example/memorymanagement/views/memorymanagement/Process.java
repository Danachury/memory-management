package com.example.memorymanagement.views.memorymanagement;

import lombok.Getter;

import java.util.Objects;
import java.util.Random;

@Getter
public class Process {

    private static final Random random = new Random();

    private final String id;
    private final Character name;

    public Process(Character name) {
        this.name = name;
        if (Objects.isNull(name))
            throw new NullPointerException("Process 'name' must not be null.");
        this.id = this.name + String.format("%04d", random.nextInt(10000));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var process = (Process) o;
        return Objects.equals(this.id, process.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
