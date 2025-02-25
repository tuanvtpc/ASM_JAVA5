package com.fpoly.java5.jpas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java5.entity.AddressEntity;

public interface AddressJPA extends JpaRepository<AddressEntity, Integer>{

}
