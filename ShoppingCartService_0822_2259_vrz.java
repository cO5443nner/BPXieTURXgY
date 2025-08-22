// 代码生成时间: 2025-08-22 22:59:10
package com.example.service;

import com.example.mapper.CartItemMapper;
import com.example.model.CartItem;
import com.example.model.ShoppingCart;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private SqlSession sqlSession = sqlSessionFactory.openSession();
    private CartItemMapper cartItemMapper = sqlSession.getMapper(CartItemMapper.class);

    /**
     * 添加商品到购物车
     * @param userId 用户ID
     * @param productId 产品ID
     * @param quantity 数量
     * @return 返回购物车项ID
     */
    @Transactional
    public String addItemToCart(String userId, String productId, int quantity) {
        try {
            // 创建购物车项
            CartItem cartItem = new CartItem();
            cartItem.setId(UUID.randomUUID().toString());
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);

            // 添加到数据库
            cartItemMapper.insertCartItem(cartItem);

            return cartItem.getId();
        } catch (Exception e) {
            // 错误处理
            throw new RuntimeException("Error adding item to cart", e);
        } finally {
            // 关闭SqlSession
            sqlSession.close();
        }
    }

    /**
     * 从购物车中移除商品
     * @param cartItemId 购物车项ID
     */
    @Transactional
    public void removeItemFromCart(String cartItemId) {
        try {
            cartItemMapper.deleteCartItem(cartItemId);
        } catch (Exception e) {
            throw new RuntimeException("Error removing item from cart", e);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 获取用户的购物车
     * @param userId 用户ID
     * @return 用户的购物车信息
     */
    public ShoppingCart getShoppingCart(String userId) {
        try {
            // 获取购物车项列表
            List<CartItem> cartItems = cartItemMapper.selectCartItemsByUserId(userId);

            // 构造购物车信息
            ShoppingCart cart = new ShoppingCart();
            cart.setItems(cartItems);

            return cart;
        } catch (Exception e) {
            throw new RuntimeException("Error getting shopping cart", e);
        } finally {
            sqlSession.close();
        }
    }
}
