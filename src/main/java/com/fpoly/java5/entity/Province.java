package com.fpoly.java5.entity;

import lombok.Data;
import java.util.List;

@Data
public class Province {
    private String name; // Tên tỉnh/thành phố
    private int code; // Mã tỉnh/thành phố
    private String division_type; // Loại đơn vị hành chính
    private String codename; // Tên mã hóa
    private int phone_code; // Mã điện thoại
    private List<District> districts; // Danh sách quận/huyện
}