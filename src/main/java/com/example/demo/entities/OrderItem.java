package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private int price;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "prod_id")
	private Product product;
}

//
//CREATE TABLE [dbo].[order_items] (
//	    [id]       INT IDENTITY (1, 1) NOT NULL,
//	    [order_id] INT NOT NULL,
//	    [prod_id]  INT NOT NULL,
//	    [quantity] INT NOT NULL,
//	    [price]    INT NOT NULL,
//	    CONSTRAINT [PK_order_items] PRIMARY KEY CLUSTERED ([id] ASC),
//	    CONSTRAINT [FK_order_items_orders] FOREIGN KEY ([order_id]) REFERENCES [dbo].[orders] ([id]),
//	    CONSTRAINT [FK_order_items_products] FOREIGN KEY ([prod_id]) REFERENCES [dbo].[products] ([id])
//	);
//
