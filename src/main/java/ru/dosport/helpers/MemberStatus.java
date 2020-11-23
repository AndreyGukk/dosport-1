package ru.dosport.helpers;

import ru.dosport.dto.MemberRequest;

/**
 * Статусы участников
 */
public final class MemberStatus {

    public final static String YES_STATUS = "Точно буду";
    public final static String MAYBE_STATUS = "Возможно буду";

    public static boolean checkCorrectlyStatus(String status) {
        return status.equals(YES_STATUS) || status.equals(MAYBE_STATUS);
    }

    public static boolean checkCorrectlyStatus(MemberRequest status) {
        return status.getUserStatus().equals(YES_STATUS) || status.getUserStatus().equals(MAYBE_STATUS);
    }

}
