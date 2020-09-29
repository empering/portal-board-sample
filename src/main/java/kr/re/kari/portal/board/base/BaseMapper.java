package kr.re.kari.portal.board.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseMapper<T, ID> {
	<S extends BaseSearchDto> List<T> findAll(@Param("search") S dto, @Param("pageable") Pageable pageable);

	<S extends BaseSearchDto> int count(@Param("search") S dto);

	Optional<T> findById(ID id);

	<S extends T> void save(S entity);

	<S extends T> void update(S entity);

	void delete(ID id);
}
