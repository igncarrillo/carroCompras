package ar.edu.um.products.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.products.domain.Distributor} entity.
 */
public class DistributorDTO implements Serializable {

    private Long id;

    @NotBlank(message = "product name cannot be empty")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @NotNull(message = "isEnabled must be a valid boolean")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isEnabled;

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

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistributorDTO)) {
            return false;
        }

        DistributorDTO distributorDTO = (DistributorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, distributorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistributorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
