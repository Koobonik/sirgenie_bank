package com.bank.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ExcelData {

    @Id
    private int year; // 기간 (연도)
    private double utilizationRate; // 이용률
    private double smartPhone; // 스마트폰
    private double desktop; // 데스크탑
    private double notebook; // 노트북
    private double etc; // 기타
    private double smartPad; // 스마트패드

}
