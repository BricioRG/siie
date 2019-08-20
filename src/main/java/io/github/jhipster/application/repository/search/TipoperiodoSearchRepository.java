package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Tipoperiodo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Tipoperiodo} entity.
 */
public interface TipoperiodoSearchRepository extends ElasticsearchRepository<Tipoperiodo, Long> {
}
