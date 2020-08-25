package kr.re.kari.portal.board.mapper;

import java.util.List;

public interface BaseMapper<T, ID> {
	List<T> findAll();
	T findById(ID id);

	<S extends T> void save(S entity);
}
