package com.example.traditionalmarket.dto.request.visitMarket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VisitedMarketDto {
    @NotBlank(message = "현재 위치의 x 좌표를 알려주세요.")
    private String x;

    @NotBlank(message = "현재 위치의 y 좌표를 알려주세요")
    private String y;
}
