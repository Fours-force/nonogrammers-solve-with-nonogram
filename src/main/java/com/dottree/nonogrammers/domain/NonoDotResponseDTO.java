package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.Dot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NonoDotResponseDTO {

    private List<List<Dot>> totalRowList;
    private String [] urlAry;
    private int progress;
    private int nonoId;
}
