package uz.pdp.cambridgelc.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.entity.product.ProductEntity;
import uz.pdp.cambridgelc.entity.product.ProductHistoryEntity;
import uz.pdp.cambridgelc.entity.product.ProductType;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.NotEnoughCreditsException;
import uz.pdp.cambridgelc.repository.HistoryRepository;
import uz.pdp.cambridgelc.repository.ProductRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
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

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public void editTitle(String title, UUID id) {
        productRepository.update(title,id);
    }

    public void buy(UUID productId, Principal principal) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(
                () -> new DataNotFoundException("Product not found!")
        );
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        if(userEntity.getCredits()<productEntity.getPrice())
            throw new NotEnoughCreditsException("Your credits are insufficient!");

        userEntity.setCredits(userEntity.getCredits()-productEntity.getPrice());
        ProductHistoryEntity productHistoryEntity = historyRepository.findProductHistoryEntityByOwner(userEntity).orElse(null);
        if (productHistoryEntity == null) {
            List<ProductEntity> productEntity1 = List.of(productEntity);
            AtomicInteger total = new AtomicInteger();
            productEntity1.forEach((product) -> total.addAndGet(product.getPrice()));
            ProductHistoryEntity productHistoryEntity1 = new ProductHistoryEntity(productEntity1,total.intValue(),userEntity);
            historyRepository.save(productHistoryEntity1);
        }else{
            ProductHistoryEntity productHistory = historyRepository.findProductHistoryEntityByOwner(userEntity).orElseThrow(
                    () -> new DataNotFoundException("History not found!")
            );
            List<ProductEntity> products = productHistory.getProducts();
            products.add(productEntity);
            productHistory.setProducts(products);
            historyRepository.save(productHistory);
        }
        userRepository.save(userEntity);
    }
}
