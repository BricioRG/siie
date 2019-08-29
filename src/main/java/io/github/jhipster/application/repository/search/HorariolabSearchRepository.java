package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Horariolab;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Horariolab} entity.
 */
public interface HorariolabSearchRepository extends ElasticsearchRepository<Horariolab, Long> {
}
