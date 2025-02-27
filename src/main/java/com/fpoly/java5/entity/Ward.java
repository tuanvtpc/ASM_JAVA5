package com.fpoly.java5.entity;

import lombok.Data;

@Data
public class Ward {
    private String name; // Tên xã/phường
    private int code; // Mã xã/phường
    private String division_type; // Loại đơn vị hành chính
    private String codename; // Tên mã hóa
    private int district_code; // Mã quận/huyện
}