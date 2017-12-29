package cn.et.conf;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * AuthenticatingRealm 登录认证的
 * AuthorizingRealm 授权
 * @author Administrator
 *
 */

import cn.et.shiro.dao.UserMapper;
import cn.et.shiro.entity.UserInfo;
@Component
public class MyDbRealm extends AuthorizingRealm{
	@Autowired
	UserMapper userMapper;
	/**
	 * 认证
	 * 	将登录输入的用户名和密码和数据库中的用户名和密码对比是否相等
	 * 	返回值null表示认证失败 非null认证通过
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("-----------------------------------------");
		//页面传入的token
		UsernamePasswordToken upt =(UsernamePasswordToken)token;
		//获取用户名
		UserInfo quseryUser = userMapper.queryUser(token.getPrincipal().toString());
		if(quseryUser!=null && quseryUser.getPassword().equals(new String(upt.getPassword()))){
			System.out.println("成功");
			//获取一个账号
			SimpleAccount sa = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			return sa;
		}
		System.out.println("失败");
		return null;
	}

	/**
	 * 获取当前用户的授权数据
	 * 将当前用户在数据库的角色和权限 加载到AuthorizationInfo
	 * 默认在进行授权认证是调用
	 * 	 检查权限调用 checkRole checkPerm
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("获取权限信息");
		//获取用户名
		String userName = principals.getPrimaryPrincipal().toString();
		//获取角色
		Set<String> roleList = userMapper.queryRoleByName(userName);
		//获取权限
		Set<String> permsList = userMapper.queryPermsByName(userName);
		//角色和权限跟集合对象
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleList);
		authorizationInfo.setStringPermissions(permsList);
		return authorizationInfo;
	}

}
