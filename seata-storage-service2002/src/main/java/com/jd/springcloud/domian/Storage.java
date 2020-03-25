package com.jd.springcloud.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Storage {
    /**
     *库存id
     *
     */
    private Long id;
    /**
     *产品id
     *
     */
    private Long productId;
    /**
     *总库存
     *
     */
    private Integer total;
    /**
     *已用库存
     *
     */
    private Integer used;
    /**
     *库存剩余
     *
     */
    private Integer residue;
}
