package uz.pdp.cambridgelc.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.entity.product.ProductEntity;
import uz.pdp.cambridgelc.entity.product.ProductType;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductEntity save(ProductDto productDto){
        ProductEntity map = modelMapper.map(productDto, ProductEntity.class);
        try{
            map.setCategory(ProductType.valueOf(productDto.getType()));
            return productRepository.save(map);
        }catch (Exception e) {
            throw new DataNotFoundException("Product type not found!");
        }
    }

    public List<ProductEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findAll(pageable).getContent();
    }
}
