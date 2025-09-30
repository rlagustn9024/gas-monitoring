package com.elim.server.gas_monitoring.docs.swagger.examples;

public class ErrorExamples409 {


    /**
     * name = "버전 충돌"
     */
    public static final String ENGINEER_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "engineer.conflict.version",
          "message": "해당 기술자 정보는 다른 사용자에 의해 이미 수정 또는 삭제되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String LICENSE_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "license.conflict.version",
          "message": "해당 라이선스 정보는 다른 사용자에 의해 이미 수정 또는 삭제되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;



    /**
     * name = "버전 충돌"
     */
    public static final String MACHINE_PROJECT_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "machineProject.conflict.version",
          "message": "해당 프로젝트는 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String MACHINE_PROJECT_SCHEDULE_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "machineProjectSchedule.conflict.version",
          "message": "해당 점검일정은 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String MACHINE_INSPECTION_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "machineInspection.conflict.version",
          "message": "해당 설비는 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String MACHINE_INSPECTION_PIC_CATE_RESULT_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "machineInspectionChecklistItemResult.conflict.version",
          "message": "해당 점검 결과는 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요. {0}",
          "errors": []
        }
        """;



    /**
     * name = "버전 충돌"
     */
    public static final String MACHINE_PIC_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "machinePic.conflict.version",
          "message": "해당 사진은 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String GAS_MEASUREMENT_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "gasMeasurement.conflict.version",
          "message": "해당 가스 측정 정보는 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String WIND_MEASUREMENT_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "windMeasurement.conflict.version",
          "message": "해당 풍량 측정 정보는 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "버전 충돌"
     */
    public static final String PIPE_MEASUREMENT_CONFLICT_VERSION = """
        {
          "code": 409,
          "messageKey": "pipeMeasurement.conflict.version",
          "message": "해당 배관 측정 정보는 다른 사용자에 의해 이미 수정 또는 삭제 되었습니다. 최신 정보를 확인한 후 다시 시도해주세요.",
          "errors": []
        }
        """;
}
