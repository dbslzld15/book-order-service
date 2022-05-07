package org.prgrms.kdt.domain.order.controller;

import org.prgrms.kdt.domain.order.entity.Order;
import org.prgrms.kdt.domain.order.request.OrderCreateRequest;
import org.prgrms.kdt.domain.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@Valid @RequestBody OrderCreateRequest createRequest, HttpSession session) {
        long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
        orderService.save(userId, createRequest);
        return "redirect:/orders/history";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable long orderId){
        orderService.cancel(orderId);
        return "redirect:/orders/history";
    }

    @DeleteMapping("/{orderId}")
    public String removeOrder(@PathVariable long orderId) {
        orderService.remove(orderId);
        return "orders/admin_list";
    }

    @GetMapping("/history")
    public String getUserOrderHistory(Model model, HttpSession session) {
        long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
        List<Order> orders = orderService.getHistory(userId);
        model.addAttribute("orders", orders);
        return "orders/order_history";
    }

    @GetMapping("/admin")
    public String getAllAdminOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/admin_list";
    }

}
