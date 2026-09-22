package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.dto.Greeting;
import ee.mihkel.veebipood.dto.HelloMessage;
import ee.mihkel.veebipood.dto.ProductPageRequest;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://veebipood-09-2026-frontend.onrender.com"})
public class GreetingController {
    private final ProductRepository productRepository;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/update") // uuendamiseks
    @SendTo("/topic/discount") // kuulamiseks
    public Page<Product> getPublicProducts(ProductPageRequest request) {
        // Pageable ei tööta STOMP @MessageMapping meetodi parameetrina (see on Spring MVC/HTTP asi),
        // seega ehitame lehe ise, aga leheküljenumbri saame nüüd frontendilt (payload'ist)
        PageRequest pageable = PageRequest.of(request.getPage(), 6, Sort.by("stock").ascending());
        return productRepository.findByDiscountGreaterThanAndStockGreaterThan(24, 0, pageable);
    }

}
