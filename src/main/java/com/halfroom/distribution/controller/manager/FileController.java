package com.halfroom.distribution.controller.manager;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.QiniuUtil;
import com.halfroom.distribution.core.util.RadomUtil;
import com.halfroom.distribution.dao.BranchsalerFileDao;
import com.halfroom.distribution.persistence.dao.BranchsalerFileMapper;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.BranchsalerFile;
import com.halfroom.distribution.persistence.vo.BucketBranchsalerFile;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件库控制器
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    private String prefix = "/system/file/";
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BranchsalerFileDao fileDao;

    @Resource
    private BranchsalerFileMapper fileMapper;

    @Resource
    private BranchsalerMapper branchsalerMapper;
    /**
     * 跳转到文件库管理首页
     */
    @RequestMapping("")
    public String index() {
        return prefix + "file.html";
    }
    /**
     * 获取所有文件列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
            @RequestParam(name="condition",required=false)String condition,
            @RequestParam(name="folderId",required=false)Integer folderId,
            @RequestParam(name="inOut",required=false)Boolean inOut
           ){
        Map<String,Object> map  = new HashMap<>();
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        if (branchsaler == null) {
            return null;
        }
        map.put("role", branchsaler.getLevel()+1);
        if(inOut==null){
            map.put("condition",condition);
            map.put("folderId",folderId );
            return fileDao.list(map);
        }
        if(inOut){
            map.put("folderId",folderId);
            ShiroKit.getUser().setFolderId(folderId);
            BranchsalerFile branchsalerFile = fileMapper.selectById(folderId);
            if(branchsalerFile!=null){
                ShiroKit.getUser().setFileUrl(ShiroKit.getUser().getFileUrl()+" 》"+branchsalerFile.getFileName());
            }
            return fileDao.list(map);
        }else{
            Integer outId = ShiroKit.getUser().getFolderId();
            BranchsalerFile branchsalerFile1=new BranchsalerFile();
            branchsalerFile1.setId(outId);
            if(outId!=null){
                BranchsalerFile branchsalerFile2 =  fileMapper.selectOne(branchsalerFile1);
                if(branchsalerFile2!=null){
                    ShiroKit.getUser().setFolderId(branchsalerFile2.getFolderId());
                    map.put("folderId",branchsalerFile2.getFolderId() );
                    ShiroKit.getUser().setFileUrl(ShiroKit.getUser().getFileUrl().replace(" 》"+branchsalerFile2.getFileName(),""));
                    return fileDao.list(map);
                }
            }
        }
        ShiroKit.getUser().setFolderId(null);
        map.put("condition",null);
        map.put("folderId",null );
        ShiroKit.getUser().setFileUrl("文件管理 》");
        return fileDao.list(map);
    }

    /**
     * 添加文件夹页面
     * @return
     */
    @RequestMapping("/folder_add")
    public String folder_add() {
        return prefix + "folder_add.html";
    }

    /**
     * 添加文件夹
     * @param name
     * @return
     */
    @RequestMapping("/folderAdd")
    @ResponseBody
    public Object folderAdd( @RequestParam(name="name",required=true)String name,
                             @RequestParam(name="role",required=true)Integer role) {
        BranchsalerFile branchsalerFile=new BranchsalerFile();
        branchsalerFile.setFolderId(ShiroKit.getUser().getFolderId());
        branchsalerFile.setIsFolder(1);
        branchsalerFile.setFileName(name);
        branchsalerFile.setFileRole(role);
        branchsalerFile.insert();
        return 0;
    }
    /**
     * 添加文件页面
     * @return
     */
    @RequestMapping("/file_add")
    public String file_add() {
        return prefix + "file_add.html";
    }


    @RequestMapping("/fileAdd")
    @ResponseBody
    public Object fileAdd(MultipartFile file) {
        String token=QiniuUtil.getToken(new BucketBranchsalerFile());
        try{
            byte[] bs=file.getBytes();
            String filename= RadomUtil.getRandomSalt(20);
            String url=  QiniuUtil.upLoadForInputStream(new BucketBranchsalerFile(),filename,bs,token);
            if(StringUtils.isNotBlank(url)){
                BranchsalerFile branchsalerFile = new BranchsalerFile();
                String fileType=file.getOriginalFilename();
                fileType=fileType.substring(fileType.lastIndexOf(".")+1,fileType.length());
                branchsalerFile.setFileName(file.getOriginalFilename());
                branchsalerFile.setFileUrl(filename);
                branchsalerFile.setFileType(fileType);
                branchsalerFile.setIsFolder(0);
                BranchsalerFile folder=fileMapper.selectById(ShiroKit.getUser().getFolderId());
                if(folder!=null){
                    branchsalerFile.setFolderId(ShiroKit.getUser().getFolderId());
                    branchsalerFile.setFileRole(folder.getFileRole());
                }
                branchsalerFile.insert();
            }
        }catch (Exception e){
            return -1;
        }
       return 1;
    }
    /**
     * 删除文件/文件夹
     * @param id
     * @return
     */
    @RequestMapping("/delFile")
    @ResponseBody
    public Object delFile( @RequestParam(name="id",required=true)Integer id) {
        EntityWrapper<BranchsalerFile> wrapper = new EntityWrapper<BranchsalerFile>();
        wrapper.eq("folder_id", id);
        List<BranchsalerFile> list= fileMapper.selectList(wrapper);
        if(list.size()>0){
            return -1;
        }
        BranchsalerFile branchsalerFile = fileMapper.selectById(id);
        if(branchsalerFile!=null){
            QiniuUtil.delFile(new BucketBranchsalerFile(),branchsalerFile.getFileUrl());
            fileMapper.deleteById(id);
        }
        return 0;
    }
    /**
     * 下载
     * @param response
     * @param id
     */
    @RequestMapping("/upLoadFile/{id}")
    public void upLoadFile(HttpServletResponse response,@PathVariable Integer id) {
        BranchsalerFile branchsalerFile = fileMapper.selectById(id);
        if(branchsalerFile!=null){
            String fileUrl=QiniuUtil.getRestUrl(new BucketBranchsalerFile(),branchsalerFile.getFileUrl(),3600);
            QiniuUtil.httpUrlupLoadFile(response,fileUrl,branchsalerFile.getFileName());
        }
    }
}
