package com.elim.server.gas_monitoring.docs.swagger.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docs/errors")
@Tag(name = "❗공통 오류 응답", description = "전 API에서 사용하는 오류 응답 구조입니다.")
public class CommonErrorDocsController {

    @Operation(
            summary = "오류 응답 형식 통합 문서",
            description = """
            모든 API는 예외 발생 시 아래의 형식으로 응답합니다.

            ---

            ### ❌ 유효성 검사 실패 (400 Bad Request)

            ```json
            {
              "code": 400,
              "messageKey": "common.invalid.input",
              "message": "요청값이 올바르지 않습니다.",
              "errors": [
                {
                  "field": "email",
                  "message": "올바른 형식의 이메일 주소여야 합니다."
                }
              ]
            }
            ```

            - @Valid / @Validated 실패 시 발생
            - 필드 단위 에러는 `errors` 배열에 담김

            ---

            ### ❌ 권한 부족 (403 Forbidden)

            ```json
            {
              "code": 403,
              "messageKey": "auth.forbidden",
              "message": "접근 권한이 없습니다.",
              "errors": []
            }
            ```

            - 일반사용자가 관리자 기능 접근 등

            ---

            ### ❌ 리소스 없음 (404 Not Found)

            ```json
            {
              "code": 404,
              "messageKey": "member.not.found",
              "message": "사용자 정보를 찾을 수 없습니다.",
              "errors": []
            }
            ```

            - 엔티티 조회 실패 (예: 회원, 프로젝트, 설비 등)

            ---

            ### ❌ 서버 내부 오류 (500 Internal Server Error)

            ```json
            {
              "code": 500,
              "messageKey": "common.unexpected.error",
              "message": "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.",
              "errors": []
            }
            ```

            - 미처리된 런타임 예외
            - 시스템 장애 등

            ---
            <br>
            ### ✅ 오류 공통 응답 필드

            | 필드명       | 타입      | 설명                             |
            |-------------|-----------|----------------------------------|
            | code        | int       | HTTP 상태 코드와 동일            |
            | messageKey  | string    | 다국어 메시지 키 (i18n 대응)     |
            | message     | string    | 사용자에게 보여질 메시지        |
            | errors      | array     | 필드 에러 목록 (Validation만 해당) |

            - 각 오류는 동일한 `ErrorResponse` 포맷을 사용합니다.
            - Validation 오류 외에는 `errors`는 빈 배열입니다.
            """
    )
    @GetMapping
    public void errorResponseFormatDoc() {
        // 문서 전용 - Swagger 설명용 dummy endpoint
    }
}
