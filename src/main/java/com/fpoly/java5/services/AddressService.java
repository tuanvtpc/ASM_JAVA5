package com.fpoly.java5.services;


import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.Province;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.AddressJPA;

import reactor.core.publisher.Mono;


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
    
    private final WebClient webClient = WebClient.create("https://provinces.open-api.vn"); 

    public List<Province> getProvinces() {
        Mono<Province[]> response = webClient.get()
                .uri("/api/?depth=2")
                .retrieve()
                .bodyToMono(Province[].class);

        Province[] provinces = response.block(); 
        
        return provinces != null ? Arrays.asList(provinces) : List.of();
    }
}
