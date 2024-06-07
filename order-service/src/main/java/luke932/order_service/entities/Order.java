package luke932.order_service.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import luke932.order_service.enums.MetodoDiPagamento;
import luke932.order_service.enums.StatoSpedizione;


@Entity
@Table(name="Ordini")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Articoli ordinati is mandatory")
    private String articoli_ordinati;
    @NotNull(message = "Info Spedizione is mandatory")
    private String info_spedizione;
    @NotNull(message = "Metodo di pagamento is mandatory")
    @Column(unique = true)
    private MetodoDiPagamento metodo_pagamento;
    @NotNull(message = "Stato spedizione is mandatory")
    private StatoSpedizione status;
    @NotNull(message = "Data is mandatory")
    private LocalDate created_date;
    
    private Long userId;

	public Order() {
		super();
	}

	public Order(Long id) {
		super();
		this.id = id;
	}

	public Order(Long id, String articoli_ordinati, String info_spedizione, MetodoDiPagamento metodo_pagamento, StatoSpedizione status,
			LocalDate created_date, Long userId) {
		super();
		this.id = id;
		this.articoli_ordinati = articoli_ordinati;
		this.info_spedizione = info_spedizione;
		this.metodo_pagamento = metodo_pagamento;
		this.status = status;
		this.created_date = created_date;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticoliOrdinati() {
		return articoli_ordinati;
	}


	public void setArticoli_ordinati(String articoli_ordinati) {
		this.articoli_ordinati = articoli_ordinati;
	}

	public String getInfo_spedizione() {
		return info_spedizione;
	}

	public void setInfo_spedizione(String info_spedizione) {
		this.info_spedizione = info_spedizione;
	}


	public MetodoDiPagamento getMetodo_pagamento() {
		return metodo_pagamento;
	}

	public void setMetodo_pagamento(MetodoDiPagamento metodo_pagamento) {
		this.metodo_pagamento = metodo_pagamento;
	}

	public StatoSpedizione getStatus() {
		return status;
	}

	public void setStatus(StatoSpedizione status) {
		this.status = status;
	}

	public LocalDate getCreatedDate() {
		return created_date;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.created_date = createdDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	} 
}
