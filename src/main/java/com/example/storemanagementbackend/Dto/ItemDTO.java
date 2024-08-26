package com.example.storemanagementbackend.Dto;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ItemDTO  implements Serializable {

    private String code;
    private String name;
    private String description;
    private String qty;
    private String unitPrice;
}
