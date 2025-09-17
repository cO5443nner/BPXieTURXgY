// 代码生成时间: 2025-09-17 12:50:12
package com.example.restfulapi;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class RestfulApiService {

    private final ItemMapper itemMapper;

    public RestfulApiService(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    // 获取所有项目
    @GetMapping
    public List<Item> getAllItems() {
        return itemMapper.findAll();
    }

    // 根据ID获取项目
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") Long id) {
        return itemMapper.findById(id);
    }

    // 创建新项目
    @PostMapping
    public Item createItem(@RequestBody Item newItem) {
        return itemMapper.create(newItem);
    }

    // 更新现有项目
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable("id") Long id, @RequestBody Item updatedItem) {
        return itemMapper.update(id, updatedItem);
    }

    // 删除项目
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        itemMapper.delete(id);
    }
}

@Mapper
interface ItemMapper {
    List<Item> findAll();
    Item findById(@Param("id") Long id);
    Item create(Item item);
    Item update(@Param("id\) Long id, @Param("item\) Item item);
    void delete(@Param("id") Long id);
}

// Item类定义
class Item {
    private Long id;
    private String name;
    private String description;

    // 构造函数, getters和setters省略
}