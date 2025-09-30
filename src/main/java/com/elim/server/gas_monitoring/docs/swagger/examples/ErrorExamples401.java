package com.elim.server.gas_monitoring.docs.swagger.examples;

public class ErrorExamples401 {


    /**
     * 로그인 실패
     * */
    public static final String LOGIN_FAILED = """
        {
          "code": 400,
          "messageKey": "member.login.failed",
          "message": "아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해주세요.",
          "errors": []
        }
        """;


    /**
     * JWT 토큰 미존재
     * */
    public static final String JWT_TOKEN_MISSING = """
        {
          "code": 400,
          "messageKey": "jwt.token.missing",
          "message": "JWT 토큰이 존재하지 않습니다.",
          "errors": []
        }
        """;


    /**
     * 유효하지 않은 JWT 토큰
     * */
    public static final String JWT_TOKEN_INVALID = """
        {
          "code": 400,
          "messageKey": "jwt.token.invalid",
          "message": "유효하지 않은 JWT 토큰입니다.",
          "errors": []
        }
        """;

}
