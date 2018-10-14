package com.intertech.lab6;

import org.springframework.messaging.Message;

import com.intertech.domain.Shiporder;
import com.intertech.domain.Shiporder.Item;

public class ShipOrderEnricher {

	public double computeTotal(Message<Shiporder> order) {
		double sum = 0;
		for (Item item : order.getPayload().getItem()) {
			sum = sum
					+ (item.getPrice().doubleValue() * item.getQuantity()
							.intValue());
		}
		return sum;
	}

}
