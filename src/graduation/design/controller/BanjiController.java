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
@RequestMapping(value = "Banji")
public class BanjiController {
	@Autowired
	private BanjiMapper banjiMapper;
	@Autowired
	private XibieMapper xibieMapper;

	@RequestMapping(value = "/initUtil.do")
	public String initUtil(HttpServletRequest request, Model model) {
		List<Xibie> listXibie = xibieMapper.getObjectList(null, null);
		model.addAttribute("listXibie", listXibie);
		return "Banji/saveOrUpdate";
	}

	@RequestMapping(value = "/selectUtil.do")
	public String selectUtil(HttpServletRequest request, Banji util, Model model) {
		util = banjiMapper.selectObject(util.getId());
		model.addAttribute("util", util);
		List<Xibie> listXibie = xibieMapper.getObjectList(null, null);
		model.addAttribute("listXibie", listXibie);
		return "Banji/saveOrUpdate";
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
		List<Banji> list = banjiMapper.getObjectList(field, fieldValue);
		PageModel pageModel = new PageModel();
		pageModel = pageModel.getUtilByController(list, currentPageNo);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("fieldValue", fieldValue);
		model.addAttribute("field", field);
		return "Banji/find";	
}

	@RequestMapping(value = "/deleteUtil.do")
	public String deleteUtil(HttpServletRequest request, Banji util, Model model) {
		try{
			banjiMapper.deleteObject(util.getId());
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
				banjiMapper.deleteObject(util.getId());
			}catch(Exception e){}
		}                                                                
		return this.getAllUtil(request, model);                    
	}                                                                  

	@RequestMapping(value = "/saveOrUpdateObject.do")
	public String saveOrUpdateObject(HttpServletRequest request, Banji util, Model model) {
		List<Banji> list = banjiMapper.getObjectList("s_0", util.getS_0());
		List<Xibie> listXibie = xibieMapper.getObjectList(null, null);
		model.addAttribute("listXibie", listXibie);
		if (0 == util.getId()) {
			if (list.size() > 0) {
				model.addAttribute("util", util);
				model.addAttribute("errMsg", "该信息已存在!");
				return "Banji/saveOrUpdate";
			}
			banjiMapper.insertObject(util);
		} else {
			if (list.size() > 1) {
				model.addAttribute("util", util);
				model.addAttribute("errMsg", "该信息已存在!");
				return "Banji/saveOrUpdate";
			}
			banjiMapper.updateObject(util);
		}
		return this.getAllUtil(request, model);
	}
}
