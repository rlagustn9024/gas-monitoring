package com.elim.server.gas_monitoring.docs.swagger.examples;

public class SuccessExampleValues {


    /**
     * 성공 응답 (Void 타입)
     * */
    public static final String VOID_SUCCESS_RESPONSE = """
        {
          "code": 200,
          "message": "성공",
          "data": null
        }
        """;


    /**
     * 성공 응답 (Health Check)
     * */
    public static final String HEALTH_CHECK_SUCCESS_RESPONSE = """
        {
          "code": 200,
          "message": "성공",
          "data": "API Server Running"
        }
        """;
}
