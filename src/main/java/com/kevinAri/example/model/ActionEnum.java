package com.kevinAri.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionEnum {
    REQUEST("REQUEST"),
    RESPONSE("RESPONSE");

    private final String value;

    public static Boolean isActionValue (String input) {
        for (ActionEnum ele : ActionEnum.values()) {
            if (input.equalsIgnoreCase(ele.value)) {
                return true;
            }
        }
        return false;
    }
}
