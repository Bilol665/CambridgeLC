package uz.pdp.cambridgelc.service.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.product.ProductEntity;
import uz.pdp.cambridgelc.entity.product.history.History;
import uz.pdp.cambridgelc.entity.product.history.ProductHistoryEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.NotEnoughCreditsException;
import uz.pdp.cambridgelc.repository.HistoryRepository;
import uz.pdp.cambridgelc.repository.ProductRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
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
        if(productHistoryEntity == null) {
            List<History> histories = List.of(new History(LocalDateTime.now(),productEntity));
            productHistoryEntity = new ProductHistoryEntity(userEntity,histories,productEntity.getPrice());
        }else{
            List<History> miniHistory = productHistoryEntity.getMiniHistory();
            miniHistory.add(new History(LocalDateTime.now(),productEntity));
            productHistoryEntity.setMiniHistory(miniHistory);
        }
        historyRepository.save(productHistoryEntity);
    }
}
