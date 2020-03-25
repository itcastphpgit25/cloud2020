package com.jd.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 *lch
 *
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    /**
     *余额id
     */
    private Long id;

    /**
     *用户id
     */
    private  Long userId;
    /**
     *总额度
     */
    private BigDecimal total;

    /**
     *已用金额
     */
    private BigDecimal used;

    /**
     *剩余额度
     */
    private BigDecimal residue;
}
