package com.cc.business.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SCDto {
    String status;
    List<Float> all;
    List<Float> back;
    List<Float> cover;
    List<Float> side;
}
