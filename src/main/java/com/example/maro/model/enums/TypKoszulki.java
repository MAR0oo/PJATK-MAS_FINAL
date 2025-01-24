package com.example.maro.model.enums;

public enum TypKoszulki {
    SPORTOWE("SPORTOWE"), OVERSIZE("OVERSIZE"), POLO("POLO"), SPORTOWE_OVERSIZE("SPORTOWE_OVERSIZE");

    private final String nazwa;

    TypKoszulki(String nazwa){
        this.nazwa = nazwa;
    }
}
