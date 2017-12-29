package cn.et.food.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.et.food.dao.DeptMapper;
import cn.et.food.dao.EmpMapper;
import cn.et.food.entity.Dept;
import cn.et.food.entity.DeptExample;
import cn.et.food.entity.Emp;
import cn.et.food.entity.EmpExample;
import cn.et.food.entity.TreeNode;
import cn.et.food.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService{
	@Autowired
	DeptMapper dm;
	@Autowired
	EmpMapper em;
	
	/* 
	 * 差部门表
	 */
	public List<TreeNode> queryTreeNode(Integer pid){
		DeptExample de = new DeptExample();
		de.createCriteria().andPidEqualTo(pid);
		List<Dept> dept =dm.selectByExample(de);
		List<TreeNode> deptList = new ArrayList<TreeNode>();
		//用for循环将部门转换成对象
		for(Dept d:dept){
			TreeNode tn = new TreeNode();
			tn.setId(d.getId());
			tn.setText(d.getDname());
			//判断当前节点下是否还有存在子节点
			if(queryTreeNode(d.getId()).size()==0){
				tn.setState("open");
			}
			deptList.add(tn);
		}
		return deptList;
		
	}
	/* 
	 * 查询员工表
	 */
	public List<Emp> queryEmp(Integer id){
		EmpExample ee = new EmpExample();
		if(id!=null)
			ee.createCriteria().andDeptidEqualTo(id);
		System.out.println("--------------------------------"+ee+"==========="+id);
		return em.selectByExample(ee);
	}
}
