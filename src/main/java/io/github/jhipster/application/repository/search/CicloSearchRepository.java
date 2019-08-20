package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Ciclo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ciclo} entity.
 */
public interface CicloSearchRepository extends ElasticsearchRepository<Ciclo, Long> {
}
