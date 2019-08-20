package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Escuela;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Escuela} entity.
 */
public interface EscuelaSearchRepository extends ElasticsearchRepository<Escuela, Long> {
}
