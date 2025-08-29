// 代码生成时间: 2025-08-29 14:15:49
package com.example.shopping;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ShoppingCartService {

    private SqlSessionFactory sqlSessionFactory;

    // Constructor
    public ShoppingCartService(String resource) throws Exception {
        String resourcePath = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resourcePath);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /*
    * Adds an item to the shopping cart.
    *
    * @param cartItemId The ID of the item to add.
    * @param quantity The quantity of the item to add.
    * @return A boolean indicating the success of the operation.
    */
    public boolean addItemToCart(int cartItemId, int quantity) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Call the mapper method to add an item to the cart
            int result = session.update("ShoppingCartMapper.addItemToCart", Map.of("cartItemId", cartItemId, "quantity", quantity));
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    * Removes an item from the shopping cart.
    *
    * @param cartItemId The ID of the item to remove.
    * @return A boolean indicating the success of the operation.
    */
    public boolean removeItemFromCart(int cartItemId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Call the mapper method to remove an item from the cart
            int result = session.update("ShoppingCartMapper.removeItemFromCart", cartItemId);
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    * Updates the quantity of an item in the shopping cart.
    *
    * @param cartItemId The ID of the item to update.
    * @param quantity The new quantity of the item.
    * @return A boolean indicating the success of the operation.
    */
    public boolean updateItemQuantity(int cartItemId, int quantity) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Call the mapper method to update the item quantity
            int result = session.update("ShoppingCartMapper.updateItemQuantity", Map.of("cartItemId", cartItemId, "quantity", quantity));
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    * Retrieves the shopping cart items for a user.
    *
    * @return A list of shopping cart items.
    */
    public List<CartItem> getCartItems() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("ShoppingCartMapper.getCartItems");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add more methods as needed for shopping cart functionality
}

// Define the CartItem class to hold item details
class CartItem {
    private int id;
    private String name;
    private int quantity;
    // Getters and setters
}