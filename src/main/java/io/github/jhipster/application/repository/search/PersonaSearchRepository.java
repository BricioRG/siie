package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Persona;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Persona} entity.
 */
public interface PersonaSearchRepository extends ElasticsearchRepository<Persona, Long> {
}
