package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPWDTO {
    String password;
    String correctPassword;
}
