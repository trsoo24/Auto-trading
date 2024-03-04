package com.project.market.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Change {
    EVEN("보합"),
    RISE("상승"),
    FALL("하락");

    private final String description;
}
