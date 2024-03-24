package com.kevinAri.example.model.jasper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {
    private String nama;
    private Integer nomor;
    private Date createdDate;
}
