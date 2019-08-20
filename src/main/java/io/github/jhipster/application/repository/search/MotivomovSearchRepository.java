package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Motivomov;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Motivomov} entity.
 */
public interface MotivomovSearchRepository extends ElasticsearchRepository<Motivomov, Long> {
}
