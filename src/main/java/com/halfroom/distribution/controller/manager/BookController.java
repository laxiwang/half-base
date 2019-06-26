package com.halfroom.distribution.controller.manager;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.annotation.Resource;

import com.halfroom.distribution.persistence.vo.BucketHalfRoom;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.core.util.QiniuUtil;
import com.halfroom.distribution.service.IBookService;

/**
 * 课程
 */
@Controller
@RequestMapping("/book")
public class BookController extends BaseController {

	private static String PREFIX = "/system/book";
	
	@Resource
	IBookService iBookService;

	/**
	 * 跳转到展示节视频页面
	 */
	@RequestMapping("/partVideos_index")
	public String partVideos_index() {
		return PREFIX + "/partVideos.html";
	}
	
    @RequestMapping("/partVideos")
    @ResponseBody
    public Object partVideos(
			@RequestParam(name="condition",required=false,defaultValue="")String condition) {
        return  iBookService.partList(condition);
    }
	@RequestMapping("/getRes")
	@ResponseBody
	public Object getRes(@RequestParam Integer id,Model model){
		if(id<60){
			return null;
		}
		return  iBookService.getResById(id);
	}

    @RequestMapping("/play/{res}")
    public Object play(
			@PathVariable String res,Model model) throws UnsupportedEncodingException {
    	 Base64.Decoder decoder = Base64.getDecoder();
    	 res=new String(decoder.decode(res.getBytes()), "UTF-8");
    	 res= QiniuUtil.getRestUrl(new BucketHalfRoom(),res,3600);
    	 model.addAttribute("res", res);
         return  PREFIX+"/play.html";
    }
}
