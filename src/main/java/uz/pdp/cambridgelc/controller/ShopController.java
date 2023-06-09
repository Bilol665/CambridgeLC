package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.service.ProductService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {
    private final ProductService productService;

    @PostMapping("/product/add-product")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> addProduct(
            @RequestBody ProductDto productDto
    ){
        return new ResponseEntity<>(productService.save(productDto), HttpStatus.OK);
    }
    @GetMapping("/product/get-all")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<Object> getAll(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }
    @DeleteMapping("/product/delete/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id){
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/product/update-title/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT','SUPER_ADMIN')")
    public ResponseEntity<Object> updateTitle(
            @RequestParam String title,
            @PathVariable UUID id
    ) {
        productService.editTitle(title,id);
        return ResponseEntity.ok("");
    }
    @PostMapping("/product/buy/{productId}")
    @PreAuthorize(value = "hasRole('STUDENT')")
    public ResponseEntity<Object> buy(
            Principal principal,
            @PathVariable UUID productId
    ) {
        productService.buy(productId,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
