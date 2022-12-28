package graduation.design.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import graduation.design.model.Banji;

public interface BanjiMapper {

	public void insertObject(Banji banji);

	public void deleteObject(int id);

	public void updateObject(Banji banji);

	public Banji selectObject(int id);

	public List<Banji> getObjectList(@Param("field") String field,
			@Param("fieldValue") String fieldValue);

}
