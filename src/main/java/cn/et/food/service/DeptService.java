package cn.et.food.service;

import java.util.List;

import cn.et.food.entity.Emp;
import cn.et.food.entity.TreeNode;

public interface DeptService {

	public abstract List<TreeNode> queryTreeNode(Integer id);

	public abstract List<Emp> queryEmp(Integer id);
	
}