package ar.edu.um.products.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.products.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistributorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistributorDTO.class);
        DistributorDTO distributorDTO1 = new DistributorDTO();
        distributorDTO1.setId(1L);
        DistributorDTO distributorDTO2 = new DistributorDTO();
        assertThat(distributorDTO1).isNotEqualTo(distributorDTO2);
        distributorDTO2.setId(distributorDTO1.getId());
        assertThat(distributorDTO1).isEqualTo(distributorDTO2);
        distributorDTO2.setId(2L);
        assertThat(distributorDTO1).isNotEqualTo(distributorDTO2);
        distributorDTO1.setId(null);
        assertThat(distributorDTO1).isNotEqualTo(distributorDTO2);
    }
}
