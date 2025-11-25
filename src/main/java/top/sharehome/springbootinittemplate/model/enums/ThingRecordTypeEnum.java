package top.sharehome.springbootinittemplate.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ThingRecordTypeEnum {
    AUDITING(1, "审核中"),
    AUDIT_FAIL(2, "审核失败"),
    AUDIT_SUCCESS(3, "审核通过"),
    ;

    private final int code;
    private final String msg;

    public static String getMsgByCode(int code) {
        for (ThingRecordTypeEnum value : ThingRecordTypeEnum.values()) {
            if (value.code == code) {
                return value.msg;
            }
        }
        return null;
    }
}
