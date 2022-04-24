package com.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entity.StatusEntity;

public interface StatusRepo extends JpaRepository<StatusEntity, Integer> {

}
