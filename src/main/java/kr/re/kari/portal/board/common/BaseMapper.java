package kr.re.kari.portal.board.common;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseMapper<T, ID> {
	List<T> findAll(@Param("search") T entity, @Param("pageable") Pageable pageable);

	T findById(ID id);

	<S extends T> void save(S entity);

	<S extends T> void update(S entity);

	void delete(ID id);
}
