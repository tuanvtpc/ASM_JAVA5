package com.fpoly.java5.entity;

import lombok.Data;
import java.util.List;

@Data
public class District {
    private String name; // Tên quận/huyện
    private int code; // Mã quận/huyện
    private String division_type; // Loại đơn vị hành chính
    private String codename; // Tên mã hóa
    private int province_code; // Mã tỉnh/thành phố
    private List<Ward> wards; // Danh sách xã/phường
}