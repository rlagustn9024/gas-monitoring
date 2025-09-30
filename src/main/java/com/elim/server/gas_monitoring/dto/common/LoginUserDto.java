package com.elim.server.gas_monitoring.dto.common;

//@Getter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//public class LoginUserDto {
//
//    private Long id;
//    private Role permission;
//    private String tokenType;
//
//    // 권한 이름을 String 타입으로 반환, 토큰 만들 때 String 타입으로 바꿔서 제작
//    public String getRole() { // @Getter가 만든 메서드는 자동으로 무시돼서 오버라이드 어노테이션 안써도 됨
//        return "ROLE_" + permission.name();
//    }
//
//
//    /**
//     * Role prefix(ROLE_)가 포함된 문자열을 enum으로 변환하여 DTO 생성
//     * ex) "ROLE_ADMIN" → Role.ADMIN
//     */
//    public static LoginUserDto from(Long memberId, String roleWithPrefix, String tokenType) {
//        if (!roleWithPrefix.startsWith("ROLE_")) {
//            throw new IllegalArgumentException("Invalid permission format: " + roleWithPrefix);
//        }
//
//        // 스프링 시큐리티에서는 권한을 ROLE_XXX 형태로 관리하는 것이 기본 convention이기 때문에 앞에 ROLE_를 붙여줬음
//        Role permission = Role.fromPrefixedName(roleWithPrefix);
//
//        return LoginUserDto.builder()
//                .id(memberId)
//                .permission(permission)
//                .tokenType(tokenType)
//                .build();
//    }
//
//
//    /**
//     * ID, Role만 가지고 DTO 생성
//     * */
//    public static LoginUserDto ofIdAndRole(Long memberId, Role permission) {
//        return LoginUserDto.builder()
//                .id(memberId)
//                .permission(permission)
//                .build();
//    }
//}
