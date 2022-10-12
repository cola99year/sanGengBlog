package com.cola.domain.vo;

/**
 * @Author: cola99year
 * @Date: 2022/10/12 14:19
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    private Long id;
    //分类名
    private String name;
}
