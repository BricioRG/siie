package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Plaza;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Plaza} entity.
 */
public interface PlazaSearchRepository extends ElasticsearchRepository<Plaza, Long> {
}
