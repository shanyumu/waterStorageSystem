package com.veyit.waterstoragesystem.tools;

public class FilterInput {

    // private static final String ONLY_LETTER_OR_NUMBER = "^[a-z0-9A-Z]+$";

    /** 密码规则 8-16位 至少包含 一个字母 和 一个数字或特殊字符 */
    public static final String PASSWORD_RULES = "(?=^.{8,16}$)((?=.*\\d)|(?=.*\\W+))(?=.*[A-Za-z]).*$";

    /** 11位 数字 */
//    解释：
//    ^:代表起始，即手机号码只能以1为开头
//    3[0-9]：代表手机号码第二位可以是3，第三位可以是0-9中任意一个数字
//    5[0-3,5-9]：代表手机号码第二位也可以是5，第三位是0-3和5-9中的任意一个数字
//    在这里，以3开头的，以5开头的，以及以8开头的三种情况，我们用“|”来将他们隔开
//    \d：匹配一个数字字符，等价于 [0-9]
//    $:终止符，代表不可以再有第12位了
            //^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\d{8}$
    public static final String PHONE_RULES = "^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9]|9[0-9])\\d{8}$";

    /** 4-20位 字母开头 只允许数字字母下划线 */
    public static final String USER_RULES = "^[a-zA-Z][a-z0-9A-Z_]{3,19}$";
    // private static final String START_LETTER_ONLY_OR_NUMBER_OR_SYMBOL_LIMIT_4_20 = "^[a-zA-Z][a-z0-9A-Z_]{3,19}$";
    public static final String POSITION_RULES = "^[0-9]+(\\.[0-9]{1,12})?$";

    public static boolean verifyMatch(String rule, String input){
        return input.matches(rule);
    }

    public static String filterSpacing(String input){
        return input.trim();
    }
}
