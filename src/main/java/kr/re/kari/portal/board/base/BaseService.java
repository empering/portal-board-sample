package kr.re.kari.portal.board.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseService<T, ID> {
	<S extends BaseSearchDto> Page<T> findAll(S dto, Pageable pageable);

	Optional<T> findById(ID id);

	Optional<T> save(T entity);

	Optional<T> update(T entity);

	void delete(ID id);
}
