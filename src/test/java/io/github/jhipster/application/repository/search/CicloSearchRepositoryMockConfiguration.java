package io.github.jhipster.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CicloSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CicloSearchRepositoryMockConfiguration {

    @MockBean
    private CicloSearchRepository mockCicloSearchRepository;

}
