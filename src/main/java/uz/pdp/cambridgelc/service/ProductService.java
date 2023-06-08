package uz.pdp.cambridgelc.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.entity.product.ProductEntity;
import uz.pdp.cambridgelc.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductEntity save(ProductDto productDto){
        ProductEntity map = modelMapper.map(productDto, ProductEntity.class);
        return map;

    }
}
