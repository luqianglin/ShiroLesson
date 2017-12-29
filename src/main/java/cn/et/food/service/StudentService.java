package cn.et.food.service;



import cn.et.food.entity.Student;
import cn.et.food.utils.PageTools;

public interface StudentService {

	public abstract PageTools queryStudent(String sname,Integer page,Integer rows);
	public void deleteStudent(Integer sid);
	public void updateStudent(Student student);
	public void saveStudent(Student student);
}