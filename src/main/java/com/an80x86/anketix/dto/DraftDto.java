package com.an80x86.anketix.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DraftDto extends TokenDto {
    private String beginDate;
    private String endDate;
}
