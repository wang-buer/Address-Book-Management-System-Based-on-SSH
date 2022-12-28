package graduation.design.controller;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import graduation.design.model.*;
import graduation.design.mapper.*;

import system.common.util.PageModel;

@Controller
@RequestMapping(value = "Student")
public class StudentController {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private XibieMapper xibieMapper;
	@Autowired
	private BanjiMapper banjiMapper;
	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/initUtil.do")
	public String initUtil(HttpServletRequest request, Model model) {
		List<Xibie> listXibie = xibieMapper.getObjectList(null, null);
		model.addAttribute("listXibie", listXibie);
		List<Banji> listBanji = banjiMapper.getObjectList(null, null);
		model.addAttribute("listBanji", listBanji);
		List<User> listUser = userMapper.getObjectList(null, null);
		model.addAttribute("listUser", listUser);
		return "Student/saveOrUpdate";
	}

	@RequestMapping(value = "/selectUtil.do")
	public String selectUtil(HttpServletRequest request, Student util, Model model) {
		util = studentMapper.selectObject(util.getId());
		model.addAttribute("util", util);
		List<Xibie> listXibie = xibieMapper.getObjectList(null, null);
		model.addAttribute("listXibie", listXibie);
		List<Banji> listBanji = banjiMapper.getObjectList(null, null);
		model.addAttribute("listBanji", listBanji);
		List<User> listUser = userMapper.getObjectList(null, null);
		model.addAttribute("listUser", listUser);
		return "Student/saveOrUpdate";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getAllUtil.do")
	public String getAllUtil(HttpServletRequest request, Model model) {
		String field = request.getParameter("field");
		String fieldValue = request.getParameter("fieldValue");
		try {
			fieldValue = new String(fieldValue.getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {}
		String pageNo = request.getParameter("pageModel.currentPageNo");
		int currentPageNo = 1;
		try{
			currentPageNo = Integer.parseInt(pageNo);
		}catch(Exception e){
		}
		List<Student> list = studentMapper.getObjectList(field, fieldValue);
		List<Student> listStudent = new ArrayList<Student>();
		HttpSession session = request.getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		String user_type = (String) session.getAttribute("user_type");
		for (Student temp : list) {
			if (user_id == temp.getUser().getId()) {
				listStudent.add(temp);
			}
		}
		if (!"admin".equals(user_type)) {
			//list = listStudent;
		}

		PageModel pageModel = new PageModel();
		pageModel = pageModel.getUtilByController(list, currentPageNo);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("fieldValue", fieldValue);
		model.addAttribute("field", field);
		return "Student/find";	
}

	@RequestMapping(value = "/deleteUtil.do")
	public String deleteUtil(HttpServletRequest request, Student util, Model model) {
		try{
			studentMapper.deleteObject(util.getId());
		}catch(Exception e){
		}
		return this.getAllUtil(request, model);
	}

	@RequestMapping(value = "/deleteManyUtil.do")                    
	public String deleteManyUtil(HttpServletRequest request, User util,
			Model model) {                                                 
		String ids[] = request.getParameterValues("id");               
		for (String id : ids) {                                          
			util = new User();                                             
			util.setId(Integer.parseInt(id));                              
			try{
				studentMapper.deleteObject(util.getId());
			}catch(Exception e){}
		}                                                                
		return this.getAllUtil(request, model);                    
	}                                                                  

	@RequestMapping(value = "/saveOrUpdateObject.do")
	public String saveOrUpdateObject(HttpServletRequest request, Student util, Model model) {
		List<Student> list = studentMapper.getObjectList("s_0", util.getS_0());
		List<Xibie> listXibie = xibieMapper.getObjectList(null, null);
		model.addAttribute("listXibie", listXibie);
		List<Banji> listBanji = banjiMapper.getObjectList(null, null);
		model.addAttribute("listBanji", listBanji);
		List<User> listUser = userMapper.getObjectList(null, null);
		model.addAttribute("listUser", listUser);
		if (0 == util.getId()) {
			if (list.size() > 0) {
				model.addAttribute("util", util);
				model.addAttribute("errMsg", "该信息已存在!");
				return "Student/saveOrUpdate";
			}
			studentMapper.insertObject(util);
		} else {
			if (list.size() > 1) {
				model.addAttribute("util", util);
				model.addAttribute("errMsg", "该信息已存在!");
				return "Student/saveOrUpdate";
			}
			studentMapper.updateObject(util);
		}
		return this.getAllUtil(request, model);
	}
}
