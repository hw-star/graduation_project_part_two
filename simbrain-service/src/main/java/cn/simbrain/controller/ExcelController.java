package cn.simbrain.controller;

import cn.simbrain.pojo.Orders;
import cn.simbrain.pojo.excel.ListPerson;
import cn.simbrain.provide.ExportProvide;
import cn.simbrain.service.OrdersService;
import cn.simbrain.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/20
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserService userService;

    @GetMapping("/getexcel/{id}")
    public void getExcelList(@PathVariable String id, HttpServletResponse response) {
        List<Orders> list = ordersService.list(new QueryWrapper<Orders>().eq("or_acid",id));
        List<ListPerson> listPeople = new ArrayList<>();
//        for (Orders item:list){
//            User user = userService.getOne(new QueryWrapper<User>().eq("user_id",item.getOrId()));
//            ListPerson even = new ListPerson();
//            even.setId(user.getUserId());
//            even.setName(user.getUserName());
//            even.setPhone(user.getUserId());
//            even.setSex(user.getUserSex() == 1 ? "男":"女");
//            listPeople.add(even);
//        }
        listPeople.add(new ListPerson("15536869272","测试","男","13203427964"));
        String fileName = list.get(0).getOrName();
        try {
            ExportProvide.writeExcel(response, listPeople, fileName, "人员名单",ListPerson.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
