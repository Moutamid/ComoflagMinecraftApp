package com.girlsskinsminecraft.boyskinsminecraft.model;


public class Skin {
    private int intName;
    private boolean isNumber;
    private String name;
    private String number;

    public Skin(int i) {
        this.number = String.valueOf(i);
        this.name = String.valueOf(i + 1);
        parseName();
    }

    public Skin(String str, String str2) {
        this.number = str;
        this.name = str2;
        parseName();
    }

    public String getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }

    public int getIntName() {
        return this.intName;
    }

    public boolean isNumber() {
        return this.isNumber;
    }

    private void parseName() {
        try {
            this.intName = Integer.parseInt(this.name);
            this.isNumber = true;
        } catch (NumberFormatException unused) {
        }
    }
}
