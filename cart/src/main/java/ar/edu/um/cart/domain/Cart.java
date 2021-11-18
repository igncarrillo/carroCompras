package ar.edu.um.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total")
    private Float total;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "cart")
    @JsonIgnoreProperties(value = { "cart", "sale" }, allowSetters = true)
    private Set<PurchasedProduct> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotal() {
        return this.total;
    }

    public Cart total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<PurchasedProduct> getProducts() {
        return this.products;
    }

    public void setProducts(Set<PurchasedProduct> purchasedProducts) {
        if (this.products != null) {
            this.products.forEach(i -> i.setCart(null));
        }
        if (purchasedProducts != null) {
            purchasedProducts.forEach(i -> i.setCart(this));
        }
        this.products = purchasedProducts;
    }

    public Cart products(Set<PurchasedProduct> purchasedProducts) {
        this.setProducts(purchasedProducts);
        return this;
    }

    public Cart addProducts(PurchasedProduct purchasedProduct) {
        this.products.add(purchasedProduct);
        purchasedProduct.setCart(this);
        return this;
    }

    public Cart removeProducts(PurchasedProduct purchasedProduct) {
        this.products.remove(purchasedProduct);
        purchasedProduct.setCart(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            "}";
    }
}
