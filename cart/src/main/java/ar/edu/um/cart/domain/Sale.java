package ar.edu.um.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "total")
    private Float total;

    @OneToMany(mappedBy = "sale")
    @JsonIgnoreProperties(value = { "cart", "sale" }, allowSetters = true)
    private Set<PurchasedProduct> products = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Sale date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getTotal() {
        return this.total;
    }

    public Sale total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Set<PurchasedProduct> getProducts() {
        return this.products;
    }

    public void setProducts(Set<PurchasedProduct> purchasedProducts) {
        if (this.products != null) {
            this.products.forEach(i -> i.setSale(null));
        }
        if (purchasedProducts != null) {
            purchasedProducts.forEach(i -> i.setSale(this));
        }
        this.products = purchasedProducts;
    }

    public Sale products(Set<PurchasedProduct> purchasedProducts) {
        this.setProducts(purchasedProducts);
        return this;
    }

    public Sale addProducts(PurchasedProduct purchasedProduct) {
        this.products.add(purchasedProduct);
        purchasedProduct.setSale(this);
        return this;
    }

    public Sale removeProducts(PurchasedProduct purchasedProduct) {
        this.products.remove(purchasedProduct);
        purchasedProduct.setSale(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sale user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return id != null && id.equals(((Sale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
