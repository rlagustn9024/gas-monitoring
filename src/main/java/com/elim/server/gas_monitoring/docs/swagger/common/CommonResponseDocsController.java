package com.elim.server.gas_monitoring.docs.swagger.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docs/common")
@Tag(name = "📄 공통 응답 형식", description = "전 API에서 사용하는 응답 구조입니다.")
public class CommonResponseDocsController {

    @Operation(
            summary = "공통 응답 구조",
            description = """
            ### ✅ 성공 시
            
            ```json
            {
              "code": 200,
              "message": "성공",
              "data": {
                "memberId": 1,
                "name": "user1"
              }
            }
            ```

            ### ✅ 성공 시 (응답 데이터 없음)

            ```json
            {
              "code": 200,
              "message": "성공",
              "data": null
            }
            ```
            
            ### ✅ 성공 시 (커스텀 메세지)

            ```json
            {
              "code": 200,
              "message": "API Server Running",
              "data": null
            }
            ```

            - `CommonResponse.success(data)`: 성공 + 기본 메시지 `"성공"`
            - `CommonResponse.success(String message)`: 성공 + 커스텀 메세지
            - `CommonResponse.success()`: 성공 + 기본 메시지 `"성공"` + 데이터 없음

            ### ❌ 실패 시 (유효성 검사 실패)

            ```json
            {
              "code": 400,
              "messageKey": "common.invalid.input",
              "message": "요청값이 올바르지 않습니다.",
              "errors": [
                {
                  "field": "memberId",
                  "message": "ID 값은 필수입니다."
                }
              ]
            }
            ```
            - 유효성 검사 실패 시 필드 단위 오류가 `errors` 배열에 포함됩니다.

            ### ❌ 실패 시 (일반 비즈니스 예외)

            ```json
            {
              "code": 404,
              "messageKey": "member.not.found",
              "message": "사용자 정보를 찾을 수 없습니다.",
              "errors": []
            }
            ```

            - 시스템/비즈니스 오류 발생 시에도 같은 포맷을 사용하며, `errors` 배열은 비워질 수 있습니다.
            - 주요 예외:
                - 인증 실패: `"auth.unauthorized"` → 401
                - 권한 부족: `"auth.forbidden"` → 403
                - 리소스 없음: `"member.not.found"`, `"machine.not.found"` 등 → 404
                - 잘못된 요청 파라미터: `"request.param.missing"`, `"request.param.type.invalid"` → 400
                - JSON 파싱 오류: `"common.invalid.json"` → 400
                - 형식 오류: `"common.invalid.format"` → 400
                - 파일 업로드 오류: `"file.upload.size.exceeded"`, `"file.multipart.required"` 등 → 400
                - 서버 오류: `"common.unexpected.error"` → 500
            """
    )
    @GetMapping
    public void commonFormatDoc() {
        // 문서 전용
    }
}