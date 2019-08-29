package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Permiso;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Permiso} entity.
 */
public interface PermisoSearchRepository extends ElasticsearchRepository<Permiso, Long> {
}
