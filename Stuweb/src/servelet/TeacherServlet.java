package servelet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TeacherDao;
import model.Page;
import model.Teacher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.SnGenerateUtil;
/**
 * 
 * @author xujinfengxu
 * Teacher Servlet
 */

public class TeacherServlet extends HttpServlet {

	private static final long serialVersionUID = 5082465592923162657L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toTeacherListView".equals(method)){
			teacherList(request,response);
		}else if("AddTeacher".equals(method)){
			addTeacher(request,response);
		}else if("TeacherList".equals(method)){
			getTeacherList(request,response);
		}else if("EditTeacher".equals(method)){
			editTeacher(request,response);
		}else if("DeleteTeacher".equals(method)){
			deleteTeacher(request,response);
		}
	}
	private void deleteTeacher(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		TeacherDao teacherDao = new TeacherDao();
		if(teacherDao.deleteTeacher(idStr)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				teacherDao.closeCon();
			}
		}
	}
	private void editTeacher(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Teacher teacher = new Teacher();
		teacher.setClazzId(clazzId);
		teacher.setName(name);
		teacher.setId(id);
		TeacherDao teacherDao = new TeacherDao();
		if(teacherDao.editTeacher(teacher)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				teacherDao.closeCon();
			}
		}
	}
	private void getTeacherList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("teacherName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer clazz = request.getParameter("clazzid") == null ? 0 : Integer.parseInt(request.getParameter("clazzid"));
		//获取当前登录用户类型
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setClazzId(clazz);
		if(userType == 3){
			//如果是老师，只能查看自己的信息
			Teacher currentUser = (Teacher)request.getSession().getAttribute("user");
			teacher.setId(currentUser.getId());
		}
		TeacherDao teacherDao = new TeacherDao();
		List<Teacher> teacherList = teacherDao.getTeacherList(teacher, new Page(currentPage, pageSize));
		int total = teacherDao.getTeacherListTotal(teacher);
		teacherDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", teacherList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teacherList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addTeacher(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Teacher teacher = new Teacher();
		teacher.setClazzId(clazzId);
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setSn(SnGenerateUtil.generateTeacherSn(clazzId));
		TeacherDao teacherDao = new TeacherDao();
		if(teacherDao.addTeacher(teacher)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				teacherDao.closeCon();
			}
		}
	}
	private void teacherList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("view/teacherList.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
