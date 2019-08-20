package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Partida;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Partida} entity.
 */
public interface PartidaSearchRepository extends ElasticsearchRepository<Partida, Long> {
}
