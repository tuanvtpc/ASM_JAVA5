package com.fpoly.java5.services;

import com.fpoly.java5.entity.District;
import com.fpoly.java5.entity.Province;
import com.fpoly.java5.entity.Ward;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AddressService {

	private final WebClient webClient = WebClient.create("https://provinces.open-api.vn");

    public List<Province> getProvinces() {
        Mono<Province[]> response = webClient.get()
                .uri("/api/p/") // Lấy danh sách tỉnh/thành phố
                .retrieve()
                .bodyToMono(Province[].class);

        Province[] provinces = response.block();
        return provinces != null ? Arrays.asList(provinces) : List.of();
    }

    public List<District> getDistrictsByProvince(int provinceCode) {
        Mono<Province> response = webClient.get()
                .uri("/api/p/{provinceCode}?depth=2", provinceCode) 
                .retrieve()
                .bodyToMono(Province.class);

        Province province = response.block();
        return province != null ? province.getDistricts() : List.of();
    }

    public List<Ward> getWardsByDistrict(int districtCode) {
        Mono<District> response = webClient.get()
                .uri("/api/d/{districtCode}?depth=2", districtCode) 
                .retrieve()
                .bodyToMono(District.class);

        District district = response.block();
        return district != null ? district.getWards() : List.of();
    }
    
    
}