package com.fpoly.java5.beans;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryBean {
		private Integer id;

	    @NotBlank(message = "Tên danh mục không được để trống")
	    @Size(min = 3, max = 50, message = "Tên danh mục phải từ 3 đến 50 ký tự")
	    @Pattern(regexp = "^[\\p{L}0-9\\s]+$", message = "Tên danh mục chỉ được chứa chữ cái, số và khoảng trắng")
	    private String name;

	    @NotNull(message = "Trạng thái không được để trống")
	    private Boolean status;
}
