package com.labelinsight.smartlabel.domain;

public class Allergen {
    private String name;
    private String presence;

    public Allergen(String name, String presence) {
        this.name = name;
        this.presence = presence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    @Override
    public String toString() {
        return "Allergen{" +
                "name='" + name + '\'' +
                ", presence='" + presence + '\'' +
                '}';
    }
}
