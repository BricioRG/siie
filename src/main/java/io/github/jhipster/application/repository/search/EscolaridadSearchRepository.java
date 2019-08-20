package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Escolaridad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Escolaridad} entity.
 */
public interface EscolaridadSearchRepository extends ElasticsearchRepository<Escolaridad, Long> {
}
