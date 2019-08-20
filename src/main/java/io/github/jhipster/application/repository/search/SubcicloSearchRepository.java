package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Subciclo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Subciclo} entity.
 */
public interface SubcicloSearchRepository extends ElasticsearchRepository<Subciclo, Long> {
}
