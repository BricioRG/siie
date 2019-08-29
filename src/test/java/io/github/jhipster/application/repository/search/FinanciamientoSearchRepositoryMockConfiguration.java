package io.github.jhipster.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FinanciamientoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FinanciamientoSearchRepositoryMockConfiguration {

    @MockBean
    private FinanciamientoSearchRepository mockFinanciamientoSearchRepository;

}
