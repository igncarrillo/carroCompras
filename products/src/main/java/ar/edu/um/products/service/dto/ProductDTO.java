package ar.edu.um.products.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.products.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotBlank(message = "product name cannot be empty")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @NotNull(message = "product price cannot be empty")
    @DecimalMin(value = "0", message = "product price must be greater than zero")
    private Float price;

    @JsonIgnore
    private Integer soldQuantity;

    @NotNull(message = "isEnabled must be a valid boolean")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isEnabled;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "distributor cannot be empty")
    private DistributorDTO distributor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public DistributorDTO getDistributor() {
        return distributor;
    }

    public void setDistributor(DistributorDTO distributor) {
        this.distributor = distributor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", distributor=" + getDistributor() +
            "}";
    }
}
