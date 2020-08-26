package kr.re.kari.portal.board.common;

import java.util.List;

public interface BaseMapper<T, ID> {
	List<T> findAll();
	T findById(ID id);

	<S extends T> void save(S entity);
	<S extends T> void update(S entity);
	void delete(ID id);
}
