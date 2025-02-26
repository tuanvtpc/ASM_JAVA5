package com.fpoly.java5.services;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.AddressJPA;


@Service
public class AddressService {
	@Autowired
    private AddressJPA addressJPA;

    public List<AddressEntity> getAddressesByUser(UserEntity user) {
        return addressJPA.findByUser(user);
    }

    public AddressEntity saveAddress(AddressEntity address) {
        return addressJPA.save(address);
    }
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://provinces.open-api.vn/api/?depth=2";

    public List<Map<String, Object>> getProvinces() {
        ResponseEntity<List> response = restTemplate.getForEntity(API_URL, List.class);
        return response.getBody();
    }
}
