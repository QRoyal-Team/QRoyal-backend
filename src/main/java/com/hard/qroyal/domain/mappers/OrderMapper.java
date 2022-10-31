package com.hard.qroyal.domain.mappers;

import com.hard.qroyal.domain.BaseMapper;
import com.hard.qroyal.domain.dtos.order.request.CreateOrderRequest;
import com.hard.qroyal.domain.dtos.order.response.OrderResponse;
import com.hard.qroyal.domain.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper {

	@Mapping(target = "payment", ignore = true)
	Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "orderDetails", target = "orderDetailResponses")
	OrderResponse mapOrderToOrderResponse(Order order);
}
