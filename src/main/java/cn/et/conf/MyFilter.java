package cn.et.conf;



import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.et.shiro.dao.UserMapper;
import cn.et.shiro.entity.Menu;

@Component  
public class MyFilter extends AuthorizationFilter {  
    /**  
     * ƥ��ָ�������������url  
     * @param regex  
     * @param url  
     * @return  
     */  
    public static boolean matchUrl(String regex,String url){ 
    	
        regex=regex.replaceAll("/+", "/");  
        if(regex.equals(url.trim())){  
            return true;  
        }  
        regex=regex.replaceAll("\\.", "\\\\.");  
        // /login.html  /l*.html  
        regex=regex.replaceAll("\\*", ".*");  
        // /**/login.html  /a/b/login.html  
        if(regex.indexOf("/.*.*/")>=0){  
            regex=regex.replaceAll("/\\.\\*\\.\\*/", "((/.*/)+|/)");  
        }  
        return Pattern.matches(regex, url);  
    } 
    /**  
     * ����  
     * @param args  
     */  
    public static void main(String[] args) {  
        System.out.println(matchUrl("/**/s*.html","/t/g/login.html"));
        System.out.println(matchUrl("/**/*.png,jpg", "/a/b.png,jpg"));
    }  
    @Autowired
    UserMapper userMapper;
    
  
    /**  
     * isAccessAllowed�����жϵ�ǰurl�������Ƿ�����֤ͨ��  �����֤ʧ�� ���ø����onAccessDenied������ת����¼ʧ��ҳ������Ȩʧ��ҳ��  
     */  
    @Override  
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  
            throws Exception {  
    	
        HttpServletRequest req=(HttpServletRequest)request; 
        String contextPath=req.getContextPath();
        
        //��ȡ�û����ʵ���Դ��·��
        String url=req.getRequestURI(); 
        url=url.split(contextPath)[1];
        //��ȡ��Щurl��Ҫ��Щ��֤
        //List<Menu> queryMenu = userMapper.queryMenuByUrl(url);
        List<Menu> queryMenu = userMapper.queryMenu();
        //������ݿ�û�����õ�ǰurl����Ȩ  ����false
        if(queryMenu.size()==0){
        	return false;
        }
        //String urlAuth=queryMenu.get(0).getMenuFilter(); 
        
        String urlAuth=null;
        for(Menu menu:queryMenu){
        	//�ж����ݿ����url��	�����url�Ƿ����
        	System.out.println(matchUrl(menu.getMenuUrl().trim(),url));
        	if(matchUrl(menu.getMenuUrl().trim(),url)){
        		urlAuth = menu.getMenuFilter().trim();
        	}
        }
        
        
        
        if(urlAuth==null){  
        	System.out.println("urlAuth   false-------------------------------MyFilter line 92");
            return false;  
        }  
        //���õĹ�������anon ֱ�ӷŹ�  
        if(urlAuth.startsWith("anon")){  
            return true;  
        }  
        //���õ���authc �жϵ�ǰ�û��Ƿ���֤ͨ��  
        Subject subject = getSubject(request, response);  
        if(urlAuth.startsWith("authc")){  
            return subject.isAuthenticated();  
        }  
        //��Ȩ��֤ Ҳ��Ҫ�ж��Ƿ��¼ û�е�¼���� ��¼�����������֤  
        boolean ifAuthc=subject.isAuthenticated();  
        if(!ifAuthc)  
            return ifAuthc;  
        //����Ƕ����roles������  ��ȡ���е�roles һ����roles[a,b]  
        if(urlAuth.startsWith("roles")){  
            String[] rolesArray=urlAuth.split("roles\\[")[1].split("\\]")[0].split(",");  
            if (rolesArray == null || rolesArray.length == 0) {  
                return true;  
            }  
            Set<String> roles = CollectionUtils.asSet(rolesArray);  
            return subject.hasAllRoles(roles);  
        }  
        if(urlAuth.startsWith("perms")){  
            String[] perms=urlAuth.split("perms\\[")[1].split("\\]")[0].split(",");  
            boolean isPermitted = true;  
            if (perms != null && perms.length > 0) {  
                if (perms.length == 1) {  
                    if (!subject.isPermitted(perms[0])) {  
                        isPermitted = false;  
                    }  
                } else {  
                    if (!subject.isPermittedAll(perms)) {  
                        isPermitted = false;  
                    }  
                }  
            }  
  
            return isPermitted;  
        }  
        return false;  
    }  
  
}  