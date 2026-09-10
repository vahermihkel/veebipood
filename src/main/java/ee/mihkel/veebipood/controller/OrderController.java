package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.dto.ParcelMachine;
import ee.mihkel.veebipood.dto.PaymentLink;
import ee.mihkel.veebipood.entity.Order;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.OrderRepository;
import ee.mihkel.veebipood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService; // faili tõmbame sisse

    @GetMapping("orders")
    public List<Order> getOrders() {
        log.info("Kasutaja xx vaatas kõiki tellimusi");
        return orderRepository.findAll();
    }

    @GetMapping("my-orders/{personId}")
    public List<Order> getMyOrders(@PathVariable Long personId) {
        return orderRepository.findByPerson_Id(personId);
    }

    @PostMapping("orders/{personId}")
    public PaymentLink saveOrder(@PathVariable Long personId, @RequestBody List<Product> products) {
        Order order = orderService.saveOrder(personId, products);
        // Miks on vaja tellimus enne makset salvestada
        // 1. Sest siis on meil tema ID käes
        // 2. Kui raha läheb maha aga meieni see info ei jõua, siis ei pea taastama
        return orderService.makePayment(order.getTotal(), order.getId());
        //return orderRepository.findAll();
    }

    @DeleteMapping("orders/{id}")
    public List<Order> deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return orderRepository.findAll();
    }

    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        return orderService.getParcelMachines(country);
    }

    @GetMapping("check-payment")
    public Order checkPaymentStatus(@RequestParam String paymentReference) {
        return orderService.checkIfOrderPaid(paymentReference);
    }

    // Õnnestunud: c3e57a365bddfc87d00b187b2828c6ae05d66314523bbfbe5655eff2e46b913c
    // Mitteõnnestunud: adacdd0a225d361ebc572d6bee37c184c394916716f4ff5485b8a77d7f36aa02
    // Õnnestunud: c1d0196537ba0a5cf6236b490be753bb5e412bc888fbda07f5d08bbd2be310da
}