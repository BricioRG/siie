package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Jornada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Jornada} entity.
 */
public interface JornadaSearchRepository extends ElasticsearchRepository<Jornada, Long> {
}
