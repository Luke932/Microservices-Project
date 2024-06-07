package luke932.order_service.impService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import luke932.order_service.config.UserEventListener;
import luke932.order_service.entities.Order;
import luke932.order_service.exceptions.UserNotFoundException;
import luke932.order_service.repositories.OrderRepository;
import luke932.order_service.services.OrderService;

@Service
public class OrderServiceImp implements OrderService {

	private final OrderRepository orRepo;
	private final UserEventListener userEventListener;

	public OrderServiceImp(OrderRepository orRepo, UserEventListener userEventListener) {
		super();
		this.orRepo = orRepo;
		this.userEventListener = userEventListener;
	}

	@Override
	@Transactional
	public Order createOrder(Order order) {
	    // Imposta la data di creazione dell'ordine alla data corrente
	    order.setCreatedDate(LocalDate.now());

	    // Ottieni la lista degli utenti
	    List<Map<String, Object>> userList = userEventListener.getUserList();
	    System.out.println(userList);

	    // Verifica se l'ID utente fornito è valido
	    Long userId = order.getUserId();
	    boolean isUserFound = userList.stream().anyMatch(user -> {
	        Object id = user.get("id");
	        if (id instanceof Integer) {
	            id = ((Integer) id).longValue(); // Converti l'ID in Long
	        }
	        System.out.println(id);
	        return id instanceof Long && ((Long) id).equals(userId);
	    });
	    
	    if (!isUserFound) {
	        throw new UserNotFoundException("Utente non trovato con ID: " + userId);
	    }

	    return orRepo.save(order);
	}



	@Override
	public List<Order> getAllOrders() {
		return orRepo.findAll();
	}

	@Override
	public Order getOrderById(Long id) {
		return orRepo.findById(id).orElse(null);
	}

	@Override
	public Order updateorder(Long id, Order order) {
		Order justOrder = this.getOrderById(id);
		if (justOrder != null) {

		}
		return null;
	}

	@Override
	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> findByUserId(Long UserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByArticoliOrdinatiContaining(String articoli) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findyByMetodoPagamento(String metodo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByInfoSpedizioneContaining(String info) {
		// TODO Auto-generated method stub
		return null;
	}

}
