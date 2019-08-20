package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Funcion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Funcion} entity.
 */
public interface FuncionSearchRepository extends ElasticsearchRepository<Funcion, Long> {
}
