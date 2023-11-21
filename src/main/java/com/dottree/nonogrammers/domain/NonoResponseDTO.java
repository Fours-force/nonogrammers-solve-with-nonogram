package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.Nono;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NonoResponseDTO {
    private int nonoId;
    private String nonoImgUrl;

    public NonoResponseDTO(Nono nono){
        this.nonoId = nono.getNonoId();
        this.nonoImgUrl = nono.getNonoImgUrl();
    }
}