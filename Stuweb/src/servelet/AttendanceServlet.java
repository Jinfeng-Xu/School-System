package servelet;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AttendanceDao;
import dao.CourseDao;
import dao.SelectedCourseDao;
import model.Attendance;
import model.Course;
import model.Page;
import model.SelectedCourse;
import model.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DateFormatUtil;

public class AttendanceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6934483447878351498L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toAttendanceServletListView".equals(method)){
			try {
				request.getRequestDispatcher("view/attendanceList.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("AddAttendance".equals(method)){
			AddAttendance(request,response);
		}else if("AttendanceList".equals(method)){
			attendanceList(request,response);
		}else if("DeleteAttendance".equals(method)){
			deleteAttendance(request,response);
		}else if("getStudentSelectedCourseList".equals(method)){
			getStudentSelectedCourseList(request, response);
		}
//		else if("getCourseDate".equals(method)){
//			getCourseDate(request, response);
//		}
	}
	private void deleteAttendance(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		AttendanceDao attendanceDao = new AttendanceDao();
		String msg = "success";
		if(!attendanceDao.deleteAttendance(id)){
			msg = "error";
		}
		attendanceDao.closeCon();
		response.getWriter().write(msg);
	}
	private void attendanceList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
		String type = request.getParameter("type");
		String date = request.getParameter("date");
		String cdate = request.getParameter("cdate");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Attendance attendance = new Attendance();
		//获取当前登录用户类型
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		if(userType == 2){
			//如果是学生，只能查看自己的信息
			Student currentUser = (Student)request.getSession().getAttribute("user");
			studentId = currentUser.getId();
		}
		attendance.setCourseId(courseId);
		attendance.setStudentId(studentId);
		attendance.setDate(date);
		attendance.setType(type);
		attendance.setCourseDate(cdate);
		AttendanceDao attendanceDao = new AttendanceDao();
		List<Attendance> attendanceList = attendanceDao.getSelectedCourseList(attendance, new Page(currentPage, pageSize));
		int total = attendanceDao.getAttendanceListTotal(attendance);
		attendanceDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", attendanceList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(attendanceList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void AddAttendance(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
		//String courseDate = request.getParameter("cdate") == null ? "0000-00-00 00:00:00" : request.getParameter("cdate").toString();
		String type = request.getParameter("type").toString();
		Course course = new Course();
		course.setId(courseId);
		CourseDao courseDao = new CourseDao();
		List<Course> courseList = courseDao.getCourse(String.valueOf(course.getId()));
//		int count = courseList.get(0).getCount() + 1;
//		courseList.get(0).setCount(count);
//		courseDao.editCourse(courseList.get(0));
//		courseDao.closeCon();
		
		SelectedCourse selectedCourse = new SelectedCourse();
		selectedCourse.setCourseId(courseId);
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		SelectedCourse sCourse = selectedCourseDao.getSelectedCourse2(selectedCourse.getCourseId());
		int count;
		count = sCourse.getCount();
		System.out.println(sCourse.getId());
		selectedCourseDao.editSelectedCourse(sCourse);
		selectedCourseDao.closeCon();
		String courseDate = courseList.get(0).getCourseDate();
		System.out.println(courseDate);
		AttendanceDao attendanceDao = new AttendanceDao();
		Attendance attendance = new Attendance();
		attendance.setCourseId(courseId);
		attendance.setStudentId(studentId);
		attendance.setType(type);
		attendance.setCourseDate(courseDate);
		System.out.println(count);
		attendance.setDate(DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		String Date = attendance.getDate();
		String msg = "success";	
		System.out.println("Checkin_date: " + attendance.getCourseDate() + "RealDate: " + Date);
//			try {
//				System.out.println(util.TimeDeteUtil.timeJudgement(Date,attendance.getCourseDate(),count-1));
//				System.out.println(util.TimeDeteUtil.timeJudgement("2021-05-05 20:21:00","2021-05-05 20:19:00",count-1));
//			} catch (ParseException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
			System.out.println(attendance.getCourseDate());
			try {
				if(util.TimeDeteUtil.timeJudgement(Date,attendance.getCourseDate(),count) == 3) {
					msg = "Sign-in succeed!";
					count += 1;
				}
				else if(util.TimeDeteUtil.timeJudgement(Date,attendance.getCourseDate(),count) == 4) {
					msg = "You've already sign-in!";
				}
				else if(util.TimeDeteUtil.timeJudgement(Date,attendance.getCourseDate(),count) == 5){
					msg = "Not in sign-in time!";
				}
//				else if(!attendanceDao.addAttendance(attendance)){
//					msg = "System internal error. Please contact the administrator！";
//					count -= 1;
//				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			CourseDao courseDao2= new CourseDao();
//			List<Course> courseList2 = courseDao2.getCourse(String.valueOf(course.getId()));
//			courseList2.get(0).setCount(count);
//			courseDao2.editCourse(courseList2.get(0));
//			courseDao2.closeCon();
			SelectedCourseDao selectedCourseDao2 = new SelectedCourseDao();
			SelectedCourse sCourse2 = selectedCourseDao2.getSelectedCourse2(selectedCourse.getCourseId());
			System.out.println(sCourse2.getCount());
			if(count == 1 && count != sCourse2.getCount())
				if(!attendanceDao.addAttendance(attendance)){
					msg = "System internal error. Please contact the administrator！";
				}
			sCourse2.setCount(count);
//			System.out.println(sCourse2.getId());
			selectedCourseDao2.editSelectedCourse(sCourse2);
			selectedCourseDao2.closeCon();
//		if(Date.compareTo(attendance.getCourseDate()) >= 0 && count == 1) {
//			msg = "1";
//		}
//		if(Date.compareTo(attendance.getCourseDate()) >= 0 && count == 2) {
//			msg = "2";
//		}
//		response.setCharacterEncoding("UTF-8");
//		if(attendance.getDate().compareTo(attendance.getCourseDate()) >= 0 && count > 2) {
//			msg = "签到过两次了，别签了";
//		}
//		else if(!attendanceDao.addAttendance(attendance)){
//			msg = "System internal error. Please contact the administrator！";
//		}
//		if(attendanceDao.isAttendanced(studentId, courseId, type,DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd"))){
//			msg = "Sign in, please do not double check in！";
//		}else if(!attendanceDao.addAttendance(attendance)){
//			msg = "System internal error. Please contact the administrator！";
//		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getStudentSelectedCourseList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("student_id") == null ? 0 : Integer.parseInt(request.getParameter("student_id").toString());
		SelectedCourse selectedCourse = new SelectedCourse();
		selectedCourse.setStudentId(studentId);
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		List<SelectedCourse> selectedCourseList = selectedCourseDao.getSelectedCourseList(selectedCourse, new Page(1, 999));
		selectedCourseDao.closeCon();
		String courseId = "";
		for(SelectedCourse sc : selectedCourseList){
			courseId += sc.getCourseId()+ ",";
		}
		courseId = courseId.substring(0,courseId.length()-1);
		CourseDao courseDao = new CourseDao();
		List<Course> courseList = courseDao.getCourse(courseId);
		courseDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(JSONArray.fromObject(courseList).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取课程时间
	 */
//	private void getCourseDate(HttpServletRequest request,
//			HttpServletResponse response) {
//		int courseId = request.getParameter("course_id") == null ? 0 : Integer.parseInt(request.getParameter("course_id").toString());
//		Course course = new Course();
//		course.setId(courseId);
//		System.out.println(courseId);
//		CourseDao courseDao = new CourseDao();
//		List<Course> courseList = courseDao.getCourseList(course,new Page(1,999));
//		courseDao.closeCon();
//		String courseDate = "";
//		for(Course c : courseList){
//			courseDate += c.getCourseDate()+ ",";
//		}
//		courseDate = courseDate.substring(0,courseDate.length()-1);
//		CourseDao courseDao2 = new CourseDao();
//		List<Course> courseList2 = courseDao2.getCourseC(courseDate);
//		System.out.println(courseList2);
//		courseDao2.closeCon();
//		response.setCharacterEncoding("UTF-8");
//		try {
//			response.getWriter().write(JSONArray.fromObject(courseList2).toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
