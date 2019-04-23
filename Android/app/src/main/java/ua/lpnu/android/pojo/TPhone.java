package ua.lpnu.android.pojo;

import android.support.v4.content.ContextCompat;

import ua.lpnu.android.R;

public class TPhone {
    final String number;
    final int type;
    final int id;

    public TPhone(String number, int type, int id) {
        this.number = number;
        this.type = type;
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public final static String getNameById(final int i) {
        switch (i) {
            case Contact.TypePhone
                    .FAMILY:
                return "FAMILY";
            case Contact.TypePhone
                    .FRIEND:
                return "FRIEND";

            case Contact.TypePhone
                    .HOME:
                return "HOME";
            case Contact.TypePhone
                    .WORK:
                return "WORK";
            default:
                return "UNKNOWN";
        }
    }

    public final static int getIdByName(final String name) {
        switch (name.toUpperCase()) {
            case "FAMILY":
                return Contact.TypePhone
                        .FAMILY;
            case "FRIEND":
                return Contact.TypePhone
                        .FRIEND;

            case "HOME":
                return Contact.TypePhone
                        .HOME;

            case "WORK":
                return Contact.TypePhone
                        .WORK;

            default:
                return Contact.TypePhone
                        .UNKNOWN;
        }
    }
}
