// 代码生成时间: 2025-08-18 01:24:25
package com.inventory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.List;

/**
 * InventoryManagementSystem is a simple demonstration of an inventory management system.
 * It uses MyBatis framework for database operations.
 */
public class InventoryManagementSystem {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor to initialize the SqlSessionFactory.
     */
    public InventoryManagementSystem() {
        try {
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add a new item to the inventory.
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = session.insert("com.inventory.mapper.ItemMapper.addItem", item);
            if (result != 1) {
                throw new RuntimeException("Failed to add item to inventory");
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve all items from the inventory.
     * @return A list of all items in the inventory.
     */
    public List<Item> getAllItems() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.inventory.mapper.ItemMapper.getAllItems");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to update an existing item in the inventory.
     * @param item The item to be updated.
     */
    public void updateItem(Item item) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = session.update("com.inventory.mapper.ItemMapper.updateItem", item);
            if (result != 1) {
                throw new RuntimeException("Failed to update item in inventory");
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to remove an item from the inventory.
     * @param id The ID of the item to be removed.
     */
    public void removeItem(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = session.delete("com.inventory.mapper.ItemMapper.removeItem", id);
            if (result != 1) {
                throw new RuntimeException("Failed to remove item from inventory");
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Define a class to represent an item in the inventory.
    public static class Item {
        private int id;
        private String name;
        private int quantity;

        // Getters and setters for the item properties
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // Main method to run the inventory management system demo.
    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();

        // Example usage of the inventory management system
        ims.addItem(new Item(1, "Laptop", 10));
        List<Item> items = ims.getAllItems();
        for (Item item : items) {
            System.out.println("Item ID: " + item.getId() + ", Name: " + item.getName() + ", Quantity: " + item.getQuantity());
        }

        ims.updateItem(new Item(1, "Laptop Pro", 20));
        ims.removeItem(1);
    }
}
