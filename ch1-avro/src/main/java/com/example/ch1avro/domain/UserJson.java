package com.example.ch1avro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserJson {
    private String name;
    private Integer favorite_number;
    private String favorite_color;
}