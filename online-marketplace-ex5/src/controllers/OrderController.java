import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
        return "Order placed successfully";
    }

    @GetMapping("/{userId}")
    public List<Order> getOrdersByUser(@PathVariable int userId) {
        return orderService.getOrdersByUser(userId);
    }
}
