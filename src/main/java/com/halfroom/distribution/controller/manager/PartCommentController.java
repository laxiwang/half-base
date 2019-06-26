package com.halfroom.distribution.controller.manager;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.dao.BookDao;
import com.halfroom.distribution.persistence.dao.PartCommentMapper;
import com.halfroom.distribution.persistence.model.BookChapterPartComment;
import com.halfroom.distribution.service.IPartCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 评论控制器
 */
@Controller
@RequestMapping("/partComment")
public class PartCommentController extends BaseController {
    @Resource
    private IPartCommentService iPartCommentService;
    @Resource
    private PartCommentMapper partCommentMapper;
    @Resource
    private BookDao bookDao;
    private static String PREFIX = "/system/partcomment";
    /**
     * 跳转到评论列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/partcomment.html";
    }

    /**
     * 查询普通用户列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(
            @RequestParam(name="offset",required=false)Integer offset,
            @RequestParam(name="limit",required=false)Integer limit,
            @RequestParam(name = "sort", required = false, defaultValue = "") String sort,
            @RequestParam(name = "order", required = false, defaultValue = "") String order,
            @RequestParam(name="condition",required=false,defaultValue="")String condition,
            @RequestParam(name="type",required=false)Integer type,
            @RequestParam(name="hot",required=false)Integer hot,
            @RequestParam(name="reply",required=false)Integer reply,
            @RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
            @RequestParam(name="endTime",required=false,defaultValue="")String endTime
            ) {
        return  iPartCommentService.list(offset,limit,sort,order,condition,type,hot,reply,beginTime,endTime);
    }
    /**
     * 设置或取消热门评论
     */
    @RequestMapping("/setHot")
    @ResponseBody
    public Object setHot(  @RequestParam(name="id",required=false)Integer id){
        BookChapterPartComment bookChapterPartComment = partCommentMapper.selectById(id);
        if(bookChapterPartComment!=null){
            int host=0;
            if(bookChapterPartComment.getIsHotComment()==0){
                host=1;
            }
            if(bookChapterPartComment.getIsHotComment()==1){
                host=0;
            }
            bookDao.updateCommentHot(id,host);
            return 1;
        }
        return 0;
    }

    /**
     * 回复评论跳转页面
     * @return
     */
    @RequestMapping("/reply_edit/{id}")
    public String reply_edit(@PathVariable long id, Model model) {
        BookChapterPartComment bookChapterPartComment = new BookChapterPartComment();
        Wrapper<BookChapterPartComment> wrapper = new EntityWrapper<>();
        wrapper.eq("at_comment_id",id);
        bookChapterPartComment= bookChapterPartComment.selectOne(wrapper);

        String content="";
        if(bookChapterPartComment!=null){
            content=bookChapterPartComment.getContent();
        }
        model.addAttribute("id",id);
        model.addAttribute("content",content);
        return PREFIX + "/reply_edit.html";
    }
    @RequestMapping("/reply")
    @ResponseBody
    public Object reply(@RequestParam(name="id",required=false)Long id,
                        @RequestParam(name="content",required=false)String content) {
        BookChapterPartComment bookChapterPartComment = new BookChapterPartComment();
        Wrapper<BookChapterPartComment> wrapper = new EntityWrapper<>();
        wrapper.eq("at_comment_id",id);
        bookChapterPartComment= bookChapterPartComment.selectOne(wrapper);
        if(bookChapterPartComment==null){
            BookChapterPartComment bookChapterPartComment1 =partCommentMapper.selectById(id);
            BookChapterPartComment bookChapterPartComment2 = new BookChapterPartComment();

            BeanUtils.copyProperties(bookChapterPartComment1,bookChapterPartComment2);
            bookChapterPartComment2.setId(null);
            bookChapterPartComment2.setAtCommentId(id);
            bookChapterPartComment2.setContent(content);
            bookChapterPartComment2.setUserId((long)1);
            bookChapterPartComment2.setType((byte)0);
            bookChapterPartComment2.setIsHotComment(null);
            bookChapterPartComment2.setCreateTime(new Date());
            bookChapterPartComment2.setUpdateTime(new Date());
            bookChapterPartComment2.insert();
        }else{
            bookChapterPartComment.setContent(content);
            partCommentMapper.updateById(bookChapterPartComment);
        }
        return 1;
    }
    /**
     * 删除评论
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(  @RequestParam(name="id",required=false)long id){
        partCommentMapper.deleteById(id);
        Wrapper<BookChapterPartComment> wrapper = new EntityWrapper<>();
        wrapper.eq("at_comment_id",id);
        partCommentMapper.delete(wrapper);
        return 1;
    }
}
