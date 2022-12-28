package graduation.design.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import graduation.design.model.Sexguanli;

public interface SexguanliMapper {

	public void insertObject(Sexguanli sexguanli);

	public void deleteObject(int id);

	public void updateObject(Sexguanli sexguanli);

	public Sexguanli selectObject(int id);

	public List<Sexguanli> getObjectList(@Param("field") String field,
			@Param("fieldValue") String fieldValue);

}
