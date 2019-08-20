package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Funcionlaboral;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Funcionlaboral} entity.
 */
public interface FuncionlaboralSearchRepository extends ElasticsearchRepository<Funcionlaboral, Long> {
}
