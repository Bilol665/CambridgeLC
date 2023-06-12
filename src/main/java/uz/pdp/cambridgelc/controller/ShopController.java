package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.service.product.ProductService;
import uz.pdp.cambridgelc.service.history.HistoryService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {
    private final ProductService productService;
    private final HistoryService historyService;

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
    public ResponseEntity<HttpStatus> updateTitle(
            @RequestParam String title,
            @PathVariable UUID id
    ) {
        productService.editTitle(title,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/product/buy/{productId}")
    @PreAuthorize(value = "hasRole('STUDENT')")
    public ResponseEntity<HttpStatus> buy(
            Principal principal,
            @PathVariable UUID productId
    ) {
        historyService.buy(productId,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/history/{userId}")
    @PreAuthorize(value = "hasRole('STUDENT')")
    public ResponseEntity<Object> history(
            @PathVariable UUID userId
    ){
        return ResponseEntity.ok(productService.history(userId));
    }
}
