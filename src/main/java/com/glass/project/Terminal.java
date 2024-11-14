package com.glass.project;

public class Terminal {
    String identifier;
    String regex;

    public Terminal(String identifier, String regex) {
        this.identifier = identifier;
        this.regex = regex;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getRegex() {
        return this.regex;
    }
}
