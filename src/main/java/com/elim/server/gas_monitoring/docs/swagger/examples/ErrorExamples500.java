package com.elim.server.gas_monitoring.docs.swagger.examples;

public class ErrorExamples500 {


    /**
     * name = "S3 이동 실패(논리 삭제 실패)"
     * */
    public static final String S3_DELETE_FAILED = """
        {
          "statusCode": 500,
          "messageKey": "s3.delete.failed",
          "message": "파일 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.",
          "errors": []
        }
        """;
}
