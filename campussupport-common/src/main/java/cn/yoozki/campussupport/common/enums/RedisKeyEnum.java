package cn.yoozki.campussupport.common.enums;

/**
 * @author yoozki
 * @date 2021/3/8
 */
public enum RedisKeyEnum {
    USER_TOKEN_KEY("USER_TOKEN_KEY_");

    private String keyName;

    RedisKeyEnum(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
