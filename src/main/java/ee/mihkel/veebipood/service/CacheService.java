package ee.mihkel.veebipood.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Log4j2
@Service
@RequiredArgsConstructor
public class CacheService {
    private final ProductRepository productRepository;

    private LoadingCache<Long, Product> productLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(Duration.ofSeconds(10))
            .build(
                    new CacheLoader<Long, Product>() {
                        @Override
                        public Product load(Long key) {
                            log.info("Ei võtnud cache-st, vaid andmebaasist");
                            return productRepository.findById(key).orElseThrow(); // VÕTAME ANDMEBAASIST ja paneme CACHE-i
                        }
                    });

    public Product getProductFromCache(Long id) throws ExecutionException {
        return productLoadingCache.get(id);
    }

    public void deleteFromCache(Long id) {
        productLoadingCache.invalidate(id);
    }

    public void updateProduct(Product product){
        productLoadingCache.put(product.getId(),product);
    }
}
