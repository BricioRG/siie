package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Direccion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Direccion} entity.
 */
public interface DireccionSearchRepository extends ElasticsearchRepository<Direccion, Long> {
}
