package cn.et.shiro.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;

import cn.et.shiro.entity.Menu;
import cn.et.shiro.entity.UserInfo;

public interface UserMapper {
	/**
	 * 查询用户名
	 * @param userName
	 * @return
	 */
	@Select("select user_name as userName,pass_word as password from user_info where user_name=#{0}")
	public UserInfo queryUser(String userName);
	/**
	 * 查看角色
	 * @param userName
	 * @return
	 */
	@Select("SELECT r.role_name FROM user_info u "
			+ "INNER JOIN user_role_relation urr ON u.user_id=urr.user_id "
			+ "INNER JOIN role r ON urr.role_id=r.role_id "
			+ "WHERE user_name=#{0}")
	public Set<String> queryRoleByName(String userName);
	
	/**
	 * 查看当前用户所有的权限
	 * @param userName
	 * @return
	 */
	@Select("SELECT p.pem_tag FROM user_info u INNER JOIN user_role_relation urr ON u.user_id=urr.user_id "
			+ "INNER JOIN role r ON urr.role_id=r.role_id "
			+ "INNER JOIN role_perms_relation rpr ON r.role_id=rpr.role_id "
			+ "INNER JOIN perms p ON rpr.pem_id=p.pem_id "
			+ "WHERE user_name=#{0}")
	public Set<String> queryPermsByName(String userName);
	/**
	 * 查询菜单
	 * @return
	 */
	@Select("select menu_id as menuid,menu_name as menuname,menu_url as menuurl,menu_filter as menufilter,is_menu as ismenu from menu")
	public List<Menu> queryMenu();
	/**
	 * 根据url路径查询菜单权限
	 * @param url
	 * @return
	 */
	@Select("select menu_id as menuid,menu_name as menuname,menu_url as menuurl,menu_filter as menufilter,is_menu as ismenu from menu where menu_url=#{0}")
	public List<Menu> queryMenuByUrl(String url);
	
	/**
	 * 查看用户所有菜单
	 * @param userName
	 * @return
	 */
	@Select("SELECT menu_name as menuname,menu_url as menuurl,menu_filter as menufilter,is_menu as ismenu FROM menu m INNER JOIN user_menu_relation umr ON m.menu_id=umr.menu_id "
			+ "INNER JOIN user_info u ON umr.user_id=u.user_id "
			+ "WHERE u.user_name=#{0}")
	public List<Menu> queryMenuByUser(String userName);
}
