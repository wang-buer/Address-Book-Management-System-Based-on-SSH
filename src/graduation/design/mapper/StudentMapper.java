package graduation.design.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import graduation.design.model.Student;

public interface StudentMapper {

	public void insertObject(Student student);

	public void deleteObject(int id);

	public void updateObject(Student student);

	public Student selectObject(int id);

	public List<Student> getObjectList(@Param("field") String field,
			@Param("fieldValue") String fieldValue);

}
