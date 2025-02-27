package com.fpoly.java5.entity;

import java.util.List;

import lombok.Data;

@Data
public class Province {
	private String code;
	private String name;
	private List<District> districts;
}

@Data
class District {
	private String code;
	private String name;
}
