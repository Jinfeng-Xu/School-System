package servelet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.CourseDao;
import dao.SelectedCourseDao;
import dao.StudentDao;
import dao.TeacherDao;
import model.Admin;
import model.Course;
import model.SelectedCourse;
import model.Student;
import model.Teacher;
import util.DateFormatUtil;

/**
 * 
 * @author xujinfengxu
 * Main interface
 */
public class SystemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3255305068211066251L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getParameter("method");
		if("toPersonalView".equals(method)) {
			personalView(request, response);
			return;
		} else if("EditPasswod".equals(method)) {
			editPassword(request, response);
			return;
		}
		else if("show".equals(method)) {
			show(request, response);
			return;
		}
		else if("toStudentIntro".equals(method)) {
			try {
				request.getRequestDispatcher("view/studentIntro.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("toAdminIntro".equals(method)) {
			try {
				request.getRequestDispatcher("view/adminIntro.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("toTeacherIntro".equals(method)) {
			try {
				request.getRequestDispatcher("view/teacherIntro.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		try {
//			request.getRequestDispatcher("view/system.jsp").forward(request, response);
//		} catch (ServletException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	private void show(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.getSession().setAttribute("Time", "");
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		if(userType == 3) {
			System.out.println("我是老师");
			Teacher teacher = (Teacher)request.getSession().getAttribute("user");
			int teacherId = teacher.getId();
			CourseDao cd = new CourseDao();
			String min = "9999-00-00 00:00:00";
			String name = null;
			String now = DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			List<Course> courseList = cd.getCourseTid(teacherId);
			for(Course course : courseList) {
				if(course.getCourseDate().compareTo(now) > 0) {
					System.out.println("我进来了");
					if(course.getCourseDate().compareTo(min) < 0) {
						min = course.getCourseDate();
						name = course.getName();
					}
				}
			}
			System.out.println(min);
			if(min.equals("9999-00-00 00:00:00"))
				request.getSession().setAttribute("Date", null);
			else
				request.getSession().setAttribute("Date", min);
			request.getSession().setAttribute("CourseName", name);
		}
		if(userType == 2) {
			System.out.println("我是学生");
			Student student = (Student)request.getSession().getAttribute("user");
			int studentId = student.getId();
			SelectedCourseDao selectedDao = new SelectedCourseDao();
			List<SelectedCourse> selectedCourseList = selectedDao.getSelectedCourseList(studentId);
			selectedDao.closeCon();
			CourseDao cd = new CourseDao();
			String now = DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			cd.closeCon();
			List<String> str = new ArrayList<String>();
			String min = "9999-00-00 00:00:00";
			String name = null;
			for(SelectedCourse sc : selectedCourseList) {
				System.out.println("jinruyici");
				//str.add(cd.getCourseCid(sc.getCourseId()).getCourseDate());
				Course c = cd.getCourseCid(sc.getCourseId());
				System.out.println(cd.getCourseCid(sc.getCourseId()).getCourseDate() + " : " + now);
				if(cd.getCourseCid(sc.getCourseId()).getCourseDate().compareTo(now) > 0) {
					System.out.println("我进来了");
					if(cd.getCourseCid(sc.getCourseId()).getCourseDate().compareTo(min) < 0) {
						min = cd.getCourseCid(sc.getCourseId()).getCourseDate();
						name = cd.getCourseCid(sc.getCourseId()).getName();
					}
				}
				//cd.getCourseCid(sc.getCourseId()).getCourseDate();
				try {
					System.out.println(cd.getCourseCid(sc.getCourseId()).getStartDate());
					System.out.println(cd.getCourseCid(sc.getCourseId()).getCourseDate());
					if(util.TimeDeteUtil.timeJudgement(now, cd.getCourseCid(sc.getCourseId()).getCourseDate(), 0) == 3){
						System.out.println("gainimaqiandaole1");
						request.getSession().setAttribute("Time", "Now is the time to check-in, please go check-in");
						}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(min);
			if(min.equals("9999-00-00 00:00:00"))
				request.getSession().setAttribute("Date", null);
			else
				request.getSession().setAttribute("Date", min);
			request.getSession().setAttribute("CourseName", name);
		}
		try {
			request.getRequestDispatcher("view/system.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void editPassword(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		response.setCharacterEncoding("UTF-8");
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		if(userType == 1){
			//管理员
			Admin admin = (Admin)request.getSession().getAttribute("user");
			if(!admin.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			AdminDao adminDao = new AdminDao();
			if(adminDao.editPassword(admin, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					adminDao.closeCon();
				}
			}else{
				try {
					response.getWriter().write("数据库修改错误");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					adminDao.closeCon();
				}
			}
		}
		if(userType == 2){
			//学生
			Student student = (Student)request.getSession().getAttribute("user");
			if(!student.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			StudentDao studentDao = new StudentDao();
			if(studentDao.editPassword(student, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					studentDao.closeCon();
				}
			}else{
				try {
					response.getWriter().write("数据库修改错误");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					studentDao.closeCon();
				}
			}
		}
		if(userType == 3){
			//教师
			Teacher teacher = (Teacher)request.getSession().getAttribute("user");
			if(!teacher.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			TeacherDao teacherDao = new TeacherDao();
			if(teacherDao.editPassword(teacher, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					teacherDao.closeCon();
				}
			}else{
				try {
					response.getWriter().write("数据库修改错误");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					teacherDao.closeCon();
				}
			}
		}
	}
	private void personalView(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("view/personalView.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}