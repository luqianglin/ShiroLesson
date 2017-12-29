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
		//��ȡ��ǰ���û�
		Subject currentUser = SecurityUtils.getSubject();
		//�Ñ�ݔ����Ñ���������
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		try{
			currentUser.login(token);
			request.getRequestDispatcher("/suc.jsp").forward(request, response);
		} catch ( UnknownAccountException uae ) {
		   System.out.println("�˺Ŵ���");
		} catch ( IncorrectCredentialsException ice ) {
			System.out.println("���벻ƥ��");
		} catch ( LockedAccountException lae ) {
			System.out.println("�˺ű�����");
		} catch ( AuthenticationException ae ) {
			System.out.println("λ���쳣");
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
