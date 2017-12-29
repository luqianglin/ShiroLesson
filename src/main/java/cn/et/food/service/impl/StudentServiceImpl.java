package cn.et.food.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.et.food.dao.StudentMapper;
import cn.et.food.entity.Student;
import cn.et.food.entity.StudentExample;
import cn.et.food.service.StudentService;
import cn.et.food.utils.PageTools;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	StudentMapper sm;
	/* (non-Javadoc)
	 * @see cn.et.lesson03.food.service.impl.StudentService#queryStudent(java.lang.String)
	 */
	public PageTools queryStudent(String sname,Integer page,Integer rows){
		
		if(sname==null){
			sname="";
		}
		StudentExample se = new StudentExample();
		se.createCriteria().andSnameLike("%"+sname+"%");
		
		//发起sql语句查询总记录数
		int total=queryStudentCount(se);
		//limit 开始位置， 总记录数
		PageTools pts=new PageTools(page, total, rows);
		RowBounds rb=new RowBounds(pts.getStartIndex()-1,rows);
		List<Student> studentList=sm.selectByExampleWithRowbounds(se, rb);
		pts.setRows(studentList);
		return pts;
	}
	/**
	 *  通过sid删除学生
	 */
	public void deleteStudent(Integer sid){
		sm.deleteByPrimaryKey(sid);
	}
	/**
	 * 修改学生
	 */
	public void updateStudent(Student student){
		sm.updateByPrimaryKey(student);
	}
	/**
	 * 添加学生
	 */
	public void saveStudent(Student student){
		sm.insert(student);
	}
	/**
	 * 获取总行数
	 */
	public int queryStudentCount(StudentExample se){
		int total=(int)sm.countByExample(se);
		return total;
	}
}
