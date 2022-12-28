package graduation.design.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import graduation.design.model.*;
import graduation.design.mapper.*;

import system.common.util.PageModel;

@Controller
@RequestMapping(value = "Sexguanli")
public class SexguanliController {
	@Autowired
	private SexguanliMapper sexguanliMapper;

	@RequestMapping(value = "/initUtil.do")
	public String initUtil(HttpServletRequest request, Model model) {
		return "Sexguanli/saveOrUpdate";
	}

	@RequestMapping(value = "/selectUtil.do")
	public String selectUtil(HttpServletRequest request, Sexguanli util, Model model) {
		util = sexguanliMapper.selectObject(util.getId());
		model.addAttribute("util", util);
		return "Sexguanli/saveOrUpdate";
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
		List<Sexguanli> list = sexguanliMapper.getObjectList(field, fieldValue);
		PageModel pageModel = new PageModel();
		pageModel = pageModel.getUtilByController(list, currentPageNo);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("fieldValue", fieldValue);
		model.addAttribute("field", field);
		return "Sexguanli/find";	
}

	@RequestMapping(value = "/deleteUtil.do")
	public String deleteUtil(HttpServletRequest request, Sexguanli util, Model model) {
		try{
			sexguanliMapper.deleteObject(util.getId());
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
				sexguanliMapper.deleteObject(util.getId());
			}catch(Exception e){}
		}                                                                
		return this.getAllUtil(request, model);                    
	}                                                                  

	@RequestMapping(value = "/saveOrUpdateObject.do")
	public String saveOrUpdateObject(HttpServletRequest request, Sexguanli util, Model model) {
		List<Sexguanli> list = sexguanliMapper.getObjectList("s_0", util.getS_0());
		if (0 == util.getId()) {
			if (list.size() > 0) {
				model.addAttribute("util", util);
				model.addAttribute("errMsg", "该信息已存在!");
				return "Sexguanli/saveOrUpdate";
			}
			sexguanliMapper.insertObject(util);
		} else {
			if (list.size() > 1) {
				model.addAttribute("util", util);
				model.addAttribute("errMsg", "该信息已存在!");
				return "Sexguanli/saveOrUpdate";
			}
			sexguanliMapper.updateObject(util);
		}
		return this.getAllUtil(request, model);
	}
}
