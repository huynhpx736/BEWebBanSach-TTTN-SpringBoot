package com.example.bookshop.service.impl;

import com.example.bookshop.dto.OrderDTO;
import com.example.bookshop.dto.OrderDetailDTO;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.OrderDetail;
import com.example.bookshop.entity.User;
import com.example.bookshop.mapper.OrderDetailMapper;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.repository.OrderDetailRepository;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public void cancelOrder(int id, String status) {
        orderRepository.findById(id).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }

    @Override
    public void placeOrder(Integer userId, String receiverPhone, String receiverAddress, String receiverName, Float shippingFee, Float discount, Float total) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

//        Order order = orderRepository.findActiveOrderByUserId(userId)
//        Order order = orderRepository.findByUserIdAndStatus(userId, "PENDING")
        Order order = orderRepository.FindOrderIsPending(userId);
        //neu khong tim thay thi throw exception
        if (order == null) {
            throw new RuntimeException("No active order found for user");
        }

//            .orElseThrow(() -> new RuntimeException("No active order found for user"));

        order.setUser(user);
        order.setShippingFee(shippingFee);
        order.setDiscount(discount);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverName(receiverName);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(total);
        //ban tong tien cua cac order detail =sum( price * quantity) cua tung order detail
//            order.setTotal((float)(order.getOrderDetails().stream().mapToDouble(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity()).sum())+order.getShippingFee()-order.getDiscount());
//
//        order.setReceiverPhone(receiverPhone);
//        order.setReceiverAddress(receiverAddress);
//        order.setReceiverName(receiverName);
//        order.setStatus("PLACED");
//        order.setOrderDate(LocalDateTime.now());
//        //ban tong tien cua cac order detail =sum( price * quantity) cua tung order detail
//        order.setTotal((float)(order.getOrderDetails().stream().mapToDouble(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity()).sum())+order.getShippingFee()-order.getDiscount());

//        order.setTotal((float) order.getOrderDetails().stream().mapToDouble(OrderDetail::getPrice).sum())  ;

        orderRepository.save(order);
        //sau khi đặt hàng xong cập nhật lại số lượng sách trong kho
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        for (OrderDetail orderDetail : orderDetails) {
            oderDetailService.updateBookQuantity(orderDetail.getBook().getId(), orderDetail.getQuantity());



    }


    @Override
    public List<OrderDTO> getOrderByUserAndStatus(Integer userId, String status) {
//        return orderRepository.findByUserIdAndStatus(userId, status).stream()
        return orderRepository.findAllByUserIdAndStatus(userId, status).stream()

                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(int id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElse(null);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        if (orderRepository.existsById(id)) {
            Order order = orderMapper.toEntity(orderDTO);
            order.setId(id);
            return orderMapper.toDTO(orderRepository.save(order));
        }
        return null;
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

//    @Override
//    public OrderDTO placeOrder(int userId, List<OrderDetailDTO> orderDetails, OrderDTO orderDTO) {
//        // Create a new order with "Pending" status
//        User user = userRepository.findById(userId).orElse(null);
//        orderDTO.setUser(user);
//        OrderDTO createdOrder = createOrder(orderDTO);
//
//        // Add order details
//        for (OrderDetailDTO detailDTO : orderDetails) {
//            OrderDetail orderDetail = orderDetailMapper.toEntity(detailDTO);
//            orderDetail.setOrder(orderMapper.toEntity(createdOrder));
//            orderDetailRepository.save(orderDetail);
//        }
//
//        // Update status to "Placed"
//        createdOrder.setStatus("Placed");
//        return updateOrder(createdOrder.getId(), createdOrder);
//    }

    @Override
    public void updateOrderStatus(int id, String status) {
        OrderDTO orderDTO = getOrderById(id);
        if (orderDTO != null) {
            orderDTO.setStatus(status);
            updateOrder(id, orderDTO);
        }
    }


}

