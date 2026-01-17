package club.main.sample.persistence;

import club.main.sample.entity.Sample;
import org.springframework.data.repository.CrudRepository;

public interface SampleRepository extends CrudRepository<Sample, Long> {
}