package com.jasontriche.bigram;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MyCommands2 {

    @ShellMethod("Subtract2")
    public int subtract3(int a, int b) {
        return a - b;
    }
}
