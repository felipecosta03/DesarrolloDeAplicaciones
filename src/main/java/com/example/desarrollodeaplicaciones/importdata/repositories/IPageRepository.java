package com.example.desarrollodeaplicaciones.importdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.desarrollodeaplicaciones.importdata.models.Page;

@Repository
public interface IPageRepository extends JpaRepository<Page, Long>{
	Page findFirstByOrderByIdAsc();
}
