package com.example.bookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private int id;
    private int productId;
    private int userId;
    private int rating;
    private String comment;
}
