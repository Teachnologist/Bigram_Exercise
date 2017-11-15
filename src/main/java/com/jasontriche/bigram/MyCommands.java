package com.jasontriche.bigram;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class MyCommands {

    @ShellMethod(value="Add",key="sum", prefix="*")
    public int add(@ShellOption("-f1") int a, @ShellOption("-f2") int b) {
        return a + b;
    }
}
