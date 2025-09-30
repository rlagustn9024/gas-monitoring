package com.elim.server.gas_monitoring.docs.swagger.examples;

public class ErrorExamples400 {


    /**
     * 비밀번호 형식 오류
     * */
    public static final String COMMON_INVALID_INPUT = """
        {
          "code": 400,
          "messageKey": "common.invalid.input",
          "message": "요청값이 올바르지 않습니다.",
          "errors": [
            {
              "field": "password",
              "message": "비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다."
            }
          ]
        }
        """;


    /**
     * name = "인증코드 불일치"
     * */
    public static final String AUTH_CODE_INVALID = """
        {
          "code": 400,
          "messageKey": "auth.code.invalid",
          "message": "인증 코드가 일치하지 않습니다.",
          "errors": []
        }
        """;


    /**
     * name = "인증코드 만료"
     * */
    public static final String AUTH_CODE_EXPIRED = """
        {
          "code": 400,
          "messageKey": "auth.code.expired",
          "message": "인증코드가 만료되었습니다. 다시 요청해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "중복된 기술진 ID 포함"
     * */
    public static final String ENGINEER_ID_DUPLICATED = """
        {
          "code": 400,
          "messageKey": "engineer.id.duplicated",
          "message": "중복된 기술진 ID가 존재합니다: [0]",
          "errors": []
        }
        """;


    /**
     * name = "기술자 중복 등록"
     * */
    public static final String ENGINEER_DUPLICATED = """
        {
          "code": 400,
          "messageKey": "engineer.duplicated",
          "message": "이미 등록된 기술자입니다. 중복 등록할 수 없습니다.",
          "errors": []
        }
        """;


    /**
     * name = "기존 비밀번호와 동일"
     * */
    public static final String MEMBER_PASSWORD_SAME = """
        {
          "code": 400,
          "messageKey": "member.password.same",
          "message": "기존 비밀번호와 동일한 비밀번호입니다.",
          "errors": []
        }
        """;


    /**
     * name = "라이선스(회사) 이름 중복"
     * */
    public static final String LICENSE_DUPLICATE_COMPANY_NAME = """
        {
          "code": 400,
          "messageKey": "license.duplicate.companyName",
          "message": "이미 등록된 업체명입니다. 다른 이름을 입력해주세요.",
          "errors": []
        }
        """;


    /**
     * name = "프로젝트와 설비의 소속 불일치"
     * */
    public static final String MACHINE_PROJECT_INSPECTION_RELATION_INVALID = """
        {
          "code": 400,
          "messageKey": "machineProject.inspection.relation.invalid",
          "message": "해당 설비는 현장에 속하지 않습니다.",
          "errors": []
        }
        """;


    /**
     * 사진 카테고리(점검 항목)와 점검 설비의 카테고리 불일치
     * */
    public static final String MACHINE_PIC_CATE_INSPECTION_CATE_RELATION_INVALID = """
        {
          "code": 400,
          "messageKey": "machineChecklistItem.inspection.cate.relation.invalid",
          "message": "사진 카테고리(점검 항목)와 점검 설비가 동일한 설비 카테고리에 속하지 않습니다.",
          "errors": []
        }
        """;


    /**
     * name = "설비 카테고리 계층 오류"
     * */
    public static final String MACHINE_CATE_HAS_CHILDREN = """
        {
          "code": 400,
          "messageKey": "machineCategory.has.children",
          "message": "상위 카테고리는 직접 선택할 수 없습니다. 하위 카테고리가 존재하는 항목입니다. [id]",
          "errors": []
        }
        """;


    /**
     * name = "중복된 S3 Key 존재"
     * */
    public static final String MACHINE_PIC_DUPLICATE_S3_KEY = """
        {
          "code": 400,
          "messageKey": "machinePic.duplicate.s3key",
          "message": "중복된 S3 Key가 존재합니다. (Key: {0})",
          "errors": []
        }
        """;


    /**
     * name = "점검 항목 소속 불일치"
     * */
    public static final String MACHINE_PIC_CATE_RELATION_INVALID = """
        {
          "code": 400,
          "messageKey": "machineChecklistItem.relation.invalid",
          "message": "{0}(은)는 기존 점검 항목에 속하지 않습니다.",
          "errors": []
        }
        """;


    /**
     * name = "업로드 타입 미지원"
     * */
    public static final String PRESIGNED_URL_UPLOAD_TYPE_INVALID = """
        {
          "code": 400,
          "messageKey": "presignedUrl.upload.type.invalid",
          "message": "지원하지 않는 업로드 타입입니다. {0}",
          "errors": []
        }
        """;


}
