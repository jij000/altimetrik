package com.yanjun.altimetrik.domain;

import lombok.Data;

import java.util.List;

@Data
public class ReturnModel extends Head {
    List<Model> Results;
}
