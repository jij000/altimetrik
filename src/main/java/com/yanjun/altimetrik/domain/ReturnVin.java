package com.yanjun.altimetrik.domain;

import lombok.Data;

import java.util.List;

@Data
public class ReturnVin extends Head {
    List<VinResult> Results;
}
