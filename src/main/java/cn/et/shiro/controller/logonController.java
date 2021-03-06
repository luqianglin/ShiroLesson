package cn.et.shiro.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Servlet implementation class logonController
 */
public class logonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public logonController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		//获取当前的用户
		Subject currentUser = SecurityUtils.getSubject();
		//用戶輸入的用戶名和密码
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		try{
			currentUser.login(token);
			request.getRequestDispatcher("/suc.jsp").forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
