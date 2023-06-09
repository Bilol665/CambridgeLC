package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.ProductDto;
import uz.pdp.cambridgelc.service.history.HistoryService;
import uz.pdp.cambridgelc.service.product.ProductService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {
    private final ProductService productService;
    private final HistoryService historyService;

    @PostMapping("/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> addProduct(
            @Valid @RequestBody ProductDto productDto,
            BindingResult bindingResult
    ){
        return new ResponseEntity<>(productService.save(productDto,bindingResult), HttpStatus.OK);
    }
    @GetMapping("/get-all")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<Object> getAll(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id){
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/product/update-title/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT','SUPER_ADMIN')")
    public ResponseEntity<HttpStatus> updateTitle(
            @RequestParam(required = false,defaultValue = "") String title,
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
