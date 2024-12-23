package com.example.bookshop.dto;

import com.example.bookshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer id;
    private User user;
    private User shipper;
    private LocalDateTime orderDate;
    private String status;
    private Float total;
    private Float shippingFee;
    private Float discount;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String cancelReason;
    private String failureReason;
    private String shipperNote;
    private String note;
}
