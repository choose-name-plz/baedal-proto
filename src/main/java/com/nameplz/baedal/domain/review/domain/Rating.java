package com.nameplz.baedal.domain.review.domain;

import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating {

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 5;

    private Integer score;

    public Rating(int score) {
        if (!isScoreInRange(score)) {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        }

        this.score = score;
    }

    private boolean isScoreInRange(int score) {
        return MIN_SCORE <= score && score <= MAX_SCORE;
    }
}
