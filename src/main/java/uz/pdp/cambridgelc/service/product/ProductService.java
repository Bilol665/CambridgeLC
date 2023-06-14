package uz.pdp.cambridgelc.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.entity.product.ProductEntity;
import uz.pdp.cambridgelc.entity.product.history.ProductHistoryEntity;
import uz.pdp.cambridgelc.entity.product.ProductType;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.RequestValidationException;
import uz.pdp.cambridgelc.repository.HistoryRepository;
import uz.pdp.cambridgelc.repository.ProductRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProductEntity save(ProductDto productDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
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

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public void editTitle(String title, UUID id) {
        productRepository.update(title,id);
    }


    public ProductHistoryEntity history(UUID userId) {
        return historyRepository.findProductHistoryEntityByOwner(userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        )).orElseThrow(
                () -> new DataNotFoundException("You didn't bought anything yet!")
        );
    }
}
