package com.hard.qroyal.integration.controllers.client.order;

import com.hard.qroyal.auth.config.VNPayConfig;
import com.hard.qroyal.domain.dtos.order.request.CreateOrderRequest;
import com.hard.qroyal.domain.dtos.order.request.OrderDetailRequest;
import com.hard.qroyal.domain.dtos.order.response.OrderResponse;
import com.hard.qroyal.domain.dtos.order.response.TransactionResponse;
import com.hard.qroyal.domain.entities.Order;
import com.hard.qroyal.domain.entities.OrderDetail;
import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.domain.mappers.OrderMapper;
import com.hard.qroyal.infrastructure.services.commands.OrderService;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import com.hard.qroyal.infrastructure.services.commands.UserService;
import com.hard.qroyal.integration.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/client/order")
public class OrderController extends BaseController<OrderService, OrderMapper> {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@PostMapping("/payment")
	public ResponseEntity<?> payment(@RequestBody CreateOrderRequest createOrderRequest,
			HttpServletRequest request) throws UnsupportedEncodingException {
		TransactionResponse transactionResponse = new TransactionResponse();
		User user = userService.getCurrentUser();
		Order order = mapper.mapCreateOrderRequestToOrder(createOrderRequest);
		List<OrderDetail> orderDetails = new ArrayList<>();
		int amount = 0;
		for (OrderDetailRequest orderDetailRequest : createOrderRequest.getOrderDetailRequests()) {
			OrderDetail orderDetail = new OrderDetail();
			Product product = productService.findById(orderDetailRequest.getProductId());
			if (product != null) {
				orderDetail.setProduct(product);
				orderDetail.setQuantity(orderDetailRequest.getQuantity());
				orderDetail.setTotal(BigDecimal.valueOf(
						(product.getPrice().intValue() * orderDetail.getQuantity()) -
								(product.getPrice().intValue() * orderDetail.getQuantity())
										* product.getDiscount() / 100));
				amount += orderDetail.getTotal().intValue();
				orderDetail.setOrder(order);
			} else {
				throw new EntityNotFoundException("Error");
			}
			orderDetails.add(orderDetail);
		}
		order.setUser(user);
		order.setOrderDetails(orderDetails);
		if (createOrderRequest.getPaymentRequest() == null) {
			order.setPayment("Non Payment");
			service.save(order);
			transactionResponse.setCode("00");
			transactionResponse.setMessage("Success");
			OrderResponse orderResponse = mapper.mapOrderToOrderResponse(order);
			transactionResponse.setOrderResponse(orderResponse);
			return ResponseEntity.ok(transactionResponse);
		}
		order.setPayment("Waiting");
		service.save(order);
		String vnp_Version = "2.1.0";
		String vnp_Command = "2.0.0";
		String vnp_OrderInfo = order.getId().toString();
		String orderType = "billpayment";
		String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
		String vnp_IpAddr = "0:0:0:0:0:0:0:1";
		String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
		vnp_Params.put("vnp_CurrCode", "VND");
		String bank_code = createOrderRequest.getPaymentRequest().getBankCode();
		if (bank_code != null && !bank_code.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bank_code);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", orderType);
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", createOrderRequest.getPaymentRequest().getReturnUrl());
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		String vnp_CreateDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Saigon"))
				.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		log.info(vnp_CreateDate);
		String vnp_ExpireDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Saigon"))
				.toLocalDateTime().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		log.info(vnp_ExpireDate);
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				//Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				//Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
		transactionResponse.setCode("00");
		transactionResponse.setMessage("Success");
		transactionResponse.setOrderResponse(mapper.mapOrderToOrderResponse(order));
		transactionResponse.setData(paymentUrl);
		return ResponseEntity.ok(transactionResponse);
	}

	@GetMapping("/info")
	public ResponseEntity<?> infor(@RequestParam(value = "vnp_TxnRef", required = false) String vnpTxnRef,
			@RequestParam(value = "vnp_Amount", required = false) String vnpAmount,
			@RequestParam(value = "vnp_OrderInfo", required = false) String vnpOrderInfo,
			@RequestParam(value = "vnp_ResponseCode", required = false) String vnpResponseCode,
			@RequestParam(value = "vnp_TransactionNo", required = false) String vnpTransactionNo,
			@RequestParam(value = "vnp_BankCode", required = false) String vnpBankCode,
			@RequestParam(value = "vnp_PayDate", required = false) String vnpPayDate,
			@RequestParam(value = "vnp_CardType", required = false) String vnpCardType,
			@RequestParam(value = "vnp_BankTranNo", required = false) String vnpBankTranNo,
			@RequestParam(value = "vnp_SecureHash", required = false) String vnpSecureHash,
			HttpServletRequest request) {
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			Map fields = new HashMap();
			for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
				String fieldName = (String) params.nextElement();
				String fieldValue = request.getParameter(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					fields.put(fieldName, fieldValue);
				}
			}
			String vnp_SecureHash = request.getParameter("vnp_SecureHash");
			if (fields.containsKey("vnp_SecureHashType")) {
				fields.remove("vnp_SecureHashType");
			}
			if (fields.containsKey("vnp_SecureHash")) {
				fields.remove("vnp_SecureHash");
			}
			String signValue = VNPayConfig.hashAllFields(fields);
			if (signValue.equals(vnp_SecureHash)) {
				boolean checkOrderId = true;
				boolean checkAmount = true;
				boolean checkOrderStatus = true;
				if (checkOrderId) {
					if (checkAmount) {
						if (checkOrderStatus) {
							if ("00".equals(vnpResponseCode)) {
								Order order = service.findById(Long.parseLong(vnpOrderInfo));
								if (order != null) {
									order.setPayment("Paid");
									service.save(order);
									transactionResponse.setCode("01");
									transactionResponse.setMessage("Success");
									transactionResponse.setOrderResponse(
											mapper.mapOrderToOrderResponse(order));
									transactionResponse.setData(null);
								} else {
									throw new EntityNotFoundException("Order not Found!");
								}
							} else {
								Order order = service.findById(Long.parseLong(vnpOrderInfo));
								if (order != null) {
									service.delete(order);
									//									order.setPayment("Paid Failure");
									//									service.save(order);
									transactionResponse.setCode("02");
									transactionResponse.setMessage("Failure");
									transactionResponse.setOrderResponse(
											mapper.mapOrderToOrderResponse(order));
									transactionResponse.setData(null);
								} else {
									throw new EntityNotFoundException("Order not Found!");
								}
							}
							log.info("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
						} else {
							log.info("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
						}
					} else {
						log.info("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
					}
				} else {
					log.info("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
				}
			} else {
				log.info("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
			}
		} catch (Exception e) {
			log.info("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
		}
		return ResponseEntity.ok(transactionResponse);
	}
}


