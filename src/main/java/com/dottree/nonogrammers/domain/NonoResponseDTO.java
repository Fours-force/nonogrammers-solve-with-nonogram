package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.Nono;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonoResponseDTO {
    private int nonoId;
    private String nonoImgUrl;

    public NonoResponseDTO(Nono nono){
        this.nonoId = nono.getNonoId();
        this.nonoImgUrl = nono.getNonoImgUrl();
    }
}