package com.nameplz.baedal.domain.user.domain;

import com.nameplz.baedal.global.common.exception.GlobalException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    CUSTOMER("ROLE_CUSTOMER"),
    OWNER("ROLE_OWNER"),
    MASTER("ROLE_MASTER");

    private final String authority;

    // Secured 처리시 하드코딩을 줄이기 위한 클래스
    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
        public static final String MASTER = "ROLE_MASTER";
    }

    // TODO : 추가로 외부에서 UserRole 중 맞는 것을 찾기보다, 아래처럼 UserRole 내부에 메서드를 만들어두면 상태와 행위를 한 곳에서 관리할 수 있어서 더욱 응집된 객체가 될 것 같습니다!!
    //    @Getter
    //    @RequiredArgsConstructor
    //    public enum OrderType {
    //        DELIVERY("배달"),
    //        TAKEOUT("포장"),
    //        EAT_IN("매장 식사");
    //
    //        private final String description;
    //
    //        public static OrderType findByName(String name) {
    //
    //            return Arrays.stream(OrderType.values())
    //                    .filter(orderType -> orderType.name().equals(name))
    //                    .findAny()
    //                    .orElseThrow(() -> new GlobalException(ResultCase.ORDER_TYPE_NOT_FOUND));
    //        }
    //    }

}
