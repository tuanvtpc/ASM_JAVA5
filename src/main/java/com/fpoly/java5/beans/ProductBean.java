package com.fpoly.java5.beans;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductBean {

    private int id;

    @NotBlank(message = "Tên sản phẩm không được bỏ trống")
    @Size(max = 250, message = "Tên sản phẩm không được vượt quá 250 ký tự")
    private String name;

    @NotBlank(message = "Mô tả sản phẩm không được bỏ trống")
    @Size(max = 500, message = "Mô tả sản phẩm không được vượt quá 500 ký tự")
    private String desc;

    @NotNull(message = "Giá sản phẩm không được bỏ trống")
    @Min(value = 10000, message = "Giá sản phẩm không được nhỏ hơn 10000")
    private Integer price;

    @NotNull(message = "Số lượng sản phẩm không được bỏ trống")
    @Min(value = 0, message = "Số lượng sản phẩm không được nhỏ hơn 0")
    private Integer quantitty;

    @NotNull(message = "Danh mục sản phẩm không được bỏ trống")
    private Integer cat_id;

    @NotNull(message = "Hình ảnh sản phẩm không được bỏ trống")
    private List<MultipartFile> images;

    public String isImagesError() {
        if (images == null || images.isEmpty()) {
            return "Vui lòng chọn ảnh sản phẩm";
        }

        for (MultipartFile file : images) {
            if (file.isEmpty()) {
                return "Vui lòng chọn ảnh sản phẩm";
            }

            double size = file.getSize() / 1024.0 / 1024.0;
            if (size > 20) {
                return "Kích thước ảnh không được vượt quá 20MB";
            }
        }

        return null;
    }
}