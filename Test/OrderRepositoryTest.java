package Test;

import com.model.CartItem;
import com.repository.OrderRepository;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class OrderRepositoryTest {

    private static OrderRepository orderRepo;

    @BeforeAll
    public static void setup() {
        orderRepo = new OrderRepository();
    }

    @Test
    @Order(1)
    @DisplayName("Test placing an order")
    public void testPlaceOrder() {
        Map<Integer, CartItem> cart = new HashMap<>();

        cart.put(1, new CartItem(1, "Phone", 15000.0, 2));
        cart.put(2, new CartItem(2, "Charger", 500.0, 3));

        try {
            orderRepo.placeOrder(cart);
            System.out.println("TestPlaceOrder: Success");
        } catch (Exception e) {
            Assertions.fail("Exception occurred while placing order: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test viewing orders for seller products")
    public void testViewOrdersForSellerProducts() {
        long sellerId = 1; 

        try {
            orderRepo.viewOrdersForSellerProducts(sellerId);
            System.out.println("TestViewOrdersForSellerProducts: Success");
        } catch (Exception e) {
            Assertions.fail("Exception occurred while viewing orders: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test placing order with empty cart")
    public void testPlaceOrderEmptyCart() {
        Map<Integer, CartItem> emptyCart = new HashMap<>();

        try {
            orderRepo.placeOrder(emptyCart); 
            System.out.println("TestPlaceOrderEmptyCart: Success");
        } catch (Exception e) {
            Assertions.fail("Exception occurred with empty cart: " + e.getMessage());
        }
    }
}
