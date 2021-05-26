package cn.simbrain.controller;

import cn.simbrain.pojo.Notice;
import cn.simbrain.service.NoticeService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description 通知公告控制层
 * @date 2021/5/25
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * @description: 条件带分页查询通知公告(管理员使用)
     * @Param current: 当前第几页
     * @Param limit: 页面数量
     * @Param fuzzyquery: 关键字
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("noticelistbysys/{current}/{limit}")
    public Result getNoticeListPageBySys(@PathVariable long current,
                                         @PathVariable long limit,
                                         @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery){
        Page<Notice> noticePage = new Page<>(current,limit);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            wrapper
                    .or().like("no_title",fuzzyquery)
                    .or().like("no_time",fuzzyquery);
        }
        wrapper.orderByDesc("no_time");
        noticeService.page(noticePage,wrapper);
        long total = noticePage.getTotal();
        List<Notice> records = noticePage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("noticedata",records);
        return Result.success(map);
    }

    /**
     * @description: 获取某一个通知公告
     * @Param id: ID
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/noticelist/{id}")
    public Result getOneNotice(@PathVariable Integer id, HttpServletRequest request){
        // 1:普通用户 0:管理员
        int num = 0;
        String token = request.getHeader("X-Token");
        if (token == null){
            num = 1;
        }else {
            Claims claims = Jwt.parseJwt(request.getHeader("X-Token"));
            boolean isUser = (boolean) claims.get("user");
            if (isUser){
                num = 1;
            }
        }
        Notice notice = noticeService.getById(id);
        if (notice == null)
            return Result.failure(ResultCode.DATA_NONE);
        return Result.success(notice);
    }

    /**
     * @description: 开启或关闭某个通知公告
     * @Param id: ID
     * @Param stateCode: 状态码
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/shownotice/{id}/{stateCode}")
    public Result showNotice(@PathVariable String id,
                             @PathVariable Integer stateCode){
        Notice notice = noticeService.getById(id);
        notice.setNoActive(stateCode);
        boolean res = noticeService.updateById(notice);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 删除某个通知公告
     * @Param id: ID
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @DeleteMapping("/deletenotice/{id}")
    public Result deletedNotice(@PathVariable String id){
        boolean res = noticeService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 批量删除通知公告
     * @Param ids: ID
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/moredeletenotice")
    public Result moreDeleteNotice(@RequestBody String[] ids){
        boolean res = noticeService.removeByIds(Arrays.asList(ids));
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 增加通知公告
     * @Param notice: 通知公告实体
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/savenotice")
    public Result addNotice(@RequestBody Notice notice){
        if (notice.getNoTitle() == null || notice.getNoContent() == null || notice.getNoTime() == null){
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        boolean res = noticeService.save(notice);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 更新通知公告
     * @Param policy: 通知公告实体
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/updatenotice")
    public Result updateNotice(@RequestBody Notice notice){
        boolean res = noticeService.updateById(notice);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 分页查询全部通知公告(不带任何条件查询)
     * @Param current: 当前第几页
     * @Param limit: 每页数量
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("noticelist/{current}/{limit}")
    public Result getNoticeListPage(@PathVariable long current,
                                    @PathVariable long limit){
        Page<Notice> noticePage = new Page<>(current,limit);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("no_active",1).orderByDesc("no_time");
        noticeService.page(noticePage,wrapper);
        long total = noticePage.getTotal();
        List<Notice> records = noticePage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("noticedata",records);
        return Result.success(map);
    }
}