package cn.et.food.controller;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.et.food.entity.Result;
import cn.et.food.entity.Student;
import cn.et.food.service.StudentService;
import cn.et.food.utils.PageTools;

@Controller
public class StudentController {
	@Autowired
	StudentService service;
	@ResponseBody
	/**
	 * 查询学生信息
	 * @param sname
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/queryStudent")
	public PageTools queryStudent(String sname,Integer page,Integer rows){
		PageTools pt= service.queryStudent(sname,page,rows);
		return pt;
	}
	/**
	 * 删除学生信息
	 * @param sid
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/student/{sid}",method=RequestMethod.DELETE)
	public Result deleteStudent(@PathVariable String sid,Integer page,Integer rows){
		String[] str=sid.split(",");
		Result r = new Result();
		r.setCode(1);
		
		try {
			for(String s:str){
				service.deleteStudent(Integer.parseInt(s));
			}
			//删除单行
			//service.deleteStudent(sid);
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
	/**
	 * 修改学生信息
	 * @param sid
	 * @param student
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/student/{sid}",method=RequestMethod.PUT)
	public Result updateStudent(@PathVariable Integer sid,Student student,Integer page,Integer rows){
		student.setSid(sid);
		Result r = new Result();
		r.setCode(1);
		
		try {
			service.updateStudent(student);
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
	/**
	 * 添加学生信息
	 * @param sid
	 * @param student
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/student",method=RequestMethod.POST)
	public Result insertStudent(Student student,MultipartFile myImage){
		Result r = new Result();
		r.setCode(1);
		
		try {
			//获取文件名
			String fileName=myImage.getOriginalFilename();
			//图片文件存放路径
			File file = new File("F:\\myfile\\"+fileName);
			myImage.transferTo(file);
			//调用后台添加方法
			service.saveStudent(student);
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
}
