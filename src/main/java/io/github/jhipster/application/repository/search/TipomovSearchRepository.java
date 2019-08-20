package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Tipomov;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Tipomov} entity.
 */
public interface TipomovSearchRepository extends ElasticsearchRepository<Tipomov, Long> {
}
