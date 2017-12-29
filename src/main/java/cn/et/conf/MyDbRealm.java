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
 * AuthenticatingRealm ��¼��֤��
 * AuthorizingRealm ��Ȩ
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
	 * ��֤
	 * 	����¼������û�������������ݿ��е��û���������Ա��Ƿ����
	 * 	����ֵnull��ʾ��֤ʧ�� ��null��֤ͨ��
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("-----------------------------------------");
		//ҳ�洫���token
		UsernamePasswordToken upt =(UsernamePasswordToken)token;
		//��ȡ�û���
		UserInfo quseryUser = userMapper.queryUser(token.getPrincipal().toString());
		if(quseryUser!=null && quseryUser.getPassword().equals(new String(upt.getPassword()))){
			System.out.println("�ɹ�");
			//��ȡһ���˺�
			SimpleAccount sa = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			return sa;
		}
		System.out.println("ʧ��");
		return null;
	}

	/**
	 * ��ȡ��ǰ�û�����Ȩ����
	 * ����ǰ�û������ݿ�Ľ�ɫ��Ȩ�� ���ص�AuthorizationInfo
	 * Ĭ���ڽ�����Ȩ��֤�ǵ���
	 * 	 ���Ȩ�޵��� checkRole checkPerm
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("��ȡȨ����Ϣ");
		//��ȡ�û���
		String userName = principals.getPrimaryPrincipal().toString();
		//��ȡ��ɫ
		Set<String> roleList = userMapper.queryRoleByName(userName);
		//��ȡȨ��
		Set<String> permsList = userMapper.queryPermsByName(userName);
		//��ɫ��Ȩ�޸����϶���
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleList);
		authorizationInfo.setStringPermissions(permsList);
		return authorizationInfo;
	}

}
