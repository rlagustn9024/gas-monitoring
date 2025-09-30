package com.elim.server.gas_monitoring.docs.swagger.examples;

public class ErrorExamples404 {


    /**
     * name = "인증코드 조회 실패"
     */
    public static final String VERIFY_CODE_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "verifyCode.not.found",
          "message": "인증코드를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * 역할 조회 실패
     */
    public static final String ROLE_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "role.not.found",
          "message": "역할 정보를 찾을 수 없습니다. {0}",
          "errors": []
        }
        """;


    /**
     * 권한 조회 실패
     */
    public static final String PERMISSION_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "permission.not.found",
          "message": "권한 정보를 찾을 수 없습니다. {0}",
          "errors": []
        }
        """;
    
    
    
    /**
     * name = "회원 조회 실패"
     */
    public static final String MEMBER_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "member.not.found",
          "message": "사용자 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;

    /**
     * name = "기술진 조회 실패"
     */
    public static final String ENGINEER_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "engineer.not.found",
          "message": "해당 기술자를 찾을 수 없습니다. [55].",
          "errors": []
        }
        """;


    /**
     * name = "라이선스 조회 실패"
     */
    public static final String LICENSE_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "license.not.found",
          "message": "회사 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "프로젝트 조회 실패"
     */
    public static final String MACHINE_PROJECT_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineProject.not.found",
          "message": "요청하신 현장 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "점검일정 조회 실패"
     */
    public static final String MACHINE_PROJECT_SCHEDULE_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineProjectSchedule.not.found",
          "message": "해당 점검일정 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "점검설비 조회 실패"
     */
    public static final String MACHINE_INSPECTION_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineInspection.not.found",
          "message": "해당 점검 설비를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "점검결과 조회 실패"
     */
    public static final String MACHINE_INSPECTION_PIC_CATE_RESULT_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineInspectionChecklistItemResult.not.found",
          "message": "해당 항목의 점검 결과를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "사진 조회 실패"
     */
    public static final String MACHINE_PIC_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machinePic.not.found",
          "message": "해당 사진을 찾을 수 없습니다.",
          "errors": []
        }
        """;

    /**
     * name = "checklist 조회 실패"
     */
    public static final String MACHINE_CHECKLIST_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineChecklist.not.found",
          "message": "해당 체크리스트를 찾을 수 없습니다.",
          "errors": []
        }
        """;

    /**
     * name = "점검 항목 조회 실패"
     */
    public static final String MACHINE_PIC_CATE_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineChecklistItem.not.found",
          "message": "해당 점검 항목을 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "세부 항목 조회 실패"
     */
    public static final String MACHINE_PIC_SUB_CATE_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "machineChecklistSubItem.not.found",
          "message": "해당 세부 항목(예: 현황사진, 명판...)을 찾을 수 없습니다. {0}",
          "errors": []
        }
        """;

    
    /**
     * name = "가스 측정 정보 조회 실패"
     */
    public static final String GAS_MEASUREMENT_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "gasMeasurement.not.found",
          "message": "해당 가스 측정 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;

    
    /**
     * name = "풍량 측정 정보 조회 실패"
     */
    public static final String WIND_MEASUREMENT_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "windMeasurement.not.found",
          "message": "해당 풍량 측정 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "배관 측정 정보 조회 실패"
     */
    public static final String PIPE_MEASUREMENT_NOT_FOUND = """
        {
          "code": 404,
          "messageKey": "pipeMeasurement.not.found",
          "message": "해당 배관 측정 정보를 찾을 수 없습니다.",
          "errors": []
        }
        """;
    
}
