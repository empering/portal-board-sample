package kr.re.kari.portal.board.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseService<T, ID> {
	Page<T> findAll(T entity, Pageable pageable);

	Optional<T> findById(ID id);

	<S extends T> Optional<T> save(S entity);

	<S extends T> Optional<T> update(S entity);

	void delete(ID id);
}
