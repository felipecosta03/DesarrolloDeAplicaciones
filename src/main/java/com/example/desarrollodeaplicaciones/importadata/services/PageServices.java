package com.example.desarrollodeaplicaciones.importadata.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.desarrollodeaplicaciones.importdata.models.Page;
import com.example.desarrollodeaplicaciones.importdata.repositories.IPageRepository;

@Service
public class PageServices implements IPageServices{
	private final IPageRepository pageRepository;

	public PageServices(IPageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}
	public Page add() {
		Page page = Page.builder()
				.id(Long.valueOf(1))
				.build();
		pageRepository.save(page);
		return page;
	}
	@Override
	public Page increment() {
    return pageRepository.findById(1L)
        .map(page -> {
            page.setNumero(page.getNumero() + 1);
            return pageRepository.save(page);
        })
        .orElseGet(() -> {
        		Page newPage = Page.builder()
			        	.id(1L)
			        	.numero(1)
			        	.build();
        		return pageRepository.save(newPage);
        });
	}

	

}
