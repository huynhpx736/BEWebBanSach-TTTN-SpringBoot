package com.example.bookshop.service;

import com.example.bookshop.dto.OrderDetailDTO;
import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> getAllOrderDetails();
    OrderDetailDTO getOrderDetailById(Integer id);
    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO updateOrderDetail(Integer id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(Integer id);

    List<OrderDetailDTO> getOrderDetailsByOrderId(Integer orderId);
    void addProductToCart(Integer userId, Integer productId, Integer quantity);
    void placeOrder(Integer userId, String receiverPhone, String receiverAddress, String receiverName);
}
