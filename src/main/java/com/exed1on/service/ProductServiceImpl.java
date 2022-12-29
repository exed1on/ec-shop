package com.exed1on.service;

import com.exed1on.dto.ProductDTO;
import com.exed1on.mapper.ProductMapper;
import com.exed1on.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper = ProductMapper.MAPPER;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }
}
