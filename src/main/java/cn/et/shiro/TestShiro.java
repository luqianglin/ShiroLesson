package cn.et.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestShiro {

	public static void main(String[] args) {
		//从ini中读取用户的权限信息 构建SecurityManager对象
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		//获取当前的用户
		Subject currentUser = SecurityUtils.getSubject();
		//当前用户的回话
		Session session = currentUser.getSession();
		//判断是否登录 未登录才需要登录
		/**
		 * 用户包括两部分
		 * 	principals and credentials
		 * 		principals(本人)表示用户的标识信息 比如 用户名 用户地址等
		 * 		credentials(凭证)表示用户用于登录的凭证 比如证书等
		 */
		//isAuthenticated判断是否登录过  
		if(!currentUser.isAuthenticated()){
			//用戶輸入的用戶名和密码
			//令牌 用户名和密码 其实就是credentials  
			UsernamePasswordToken token = new UsernamePasswordToken("jiaozi","123456");
			//设置记住密码 true
			token.setRememberMe(true);
			try {
				//开始登录操作  操作后 isAuthenticated就是true  
			    currentUser.login( token );
			    System.out.println("登录成功");
			    System.out.println(currentUser.isAuthenticated());
			    System.out.println(currentUser.isRemembered());
			    //检查登录后的用户是否拥有某个角色
			    if(currentUser.hasRole("role1")){
			    	System.out.println("是否拥有role1角色");
			    }
			    
			    if(currentUser.isPermitted("user:delete:2")){
			    	System.out.println("拥有查询1号用户的权限");
			    }
			} catch ( UnknownAccountException uae ) {
			   System.out.println("账号错误");
			} catch ( IncorrectCredentialsException ice ) {
				System.out.println("密码不匹配");
			} catch ( LockedAccountException lae ) {
				System.out.println("账号被锁定");
			} catch ( AuthenticationException ae ) {
				System.out.println("位置异常");
			}
			
		}

	}

}
