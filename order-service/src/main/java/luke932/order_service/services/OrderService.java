package luke932.order_service.services;

import java.util.List;

import luke932.order_service.entities.Order;

public interface OrderService {
	
	Order createOrder(Order order);
	
	List<Order> getAllOrders();
	
	Order getOrderById(Long id);
	
	Order updateorder (Long id, Order order);
	
	void deleteOrder (Long id);
	
	List<Order> findByUserId(Long UserId);
	
	List<Order> findByArticoliOrdinatiContaining(String articoli);
	
	List<Order> findyByMetodoPagamento(String metodo);
	
	List<Order> findByInfoSpedizioneContaining(String info);

}
