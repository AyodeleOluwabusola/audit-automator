package com.audit.automator.enums;

import lombok.Getter;

@Getter
public enum BankEnum {

    GTBANK("GTBank", "oluwabusola.ayodele@gmail.com"),
    ZENITH_BANK("Zenith Bank", "oluwabusola.ayodele@gmail.com"),
    FIRST_BANK("First Bank", "oluwabusola.ayodele@gmail.com"),
    PROVIDUS_BANK("Providus Bank", "oluwabusola.ayodele@gmail.com"),
    ;

    private String name;
    private String email;

    BankEnum(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public static String from(String name) {
        if(name == null || name.isEmpty()) {
            return null;
        }
        for(BankEnum bankEnum : BankEnum.values()) {
            if(name.equals(bankEnum.getName())) {
                return bankEnum.name();
            }
        }
        return null;
    }


    public static String getEmailFromString(String name) {
        if(name == null || name.isEmpty()) {
            return null;
        }
        for(BankEnum bankEnum : BankEnum.values()) {
            if(name.equals(bankEnum.getName())) {
                return bankEnum.getEmail();
            }
        }
        return null;
    }
}
