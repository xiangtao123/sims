package com.jsrush.security.rbac.web;

import com.jsrush.security.rbac.entity.Ec;
import com.jsrush.security.rbac.service.EcService;
import com.jsrush.util.SystemUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "ecinfor")
public class EcController {

    @Autowired
    private EcService ecService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Map<String, Object>> list() {
        List<Ec> list = ecService.getAll();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Ec ec : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("corpName", ec.getCorpName());
            map.put("id", ec.getId());
            maps.add(map);
        }
        return maps;
    }

    @RequestMapping(value = "/plist")
    @ResponseBody
    public Map<String, Object> listWithPage(@RequestParam(value = "params", required = false, defaultValue = "{}") String params,
                                            @RequestParam(defaultValue = "1", value = "page") int pageNo,
                                            @RequestParam(defaultValue = "10", value = "rows") int pageSize) {
        JSONObject json = JSONObject.fromObject(params);
        String name = json.has("corpName") ? json.getString("corpName") : "";
        int index = SystemUtil.firstNo(pageNo, pageSize);
        List<Ec> list = ecService.findByCorpNameLike(name, index, pageSize);
        int count = ecService.findCountByCorpNameLike(name);
        return SystemUtil.createPageInfoMap(pageNo, pageSize, count, list);
    }

   /* @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String view(@RequestParam(value = "id", required = false, defaultValue = "0") Long id, Model model) {
        Ec ec = ecService.getEc(id);
        if (ec != null)
            model.addAttribute("ec", ec);
        return "/system/biz_ec_edit";
    }*/

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Ec view(@RequestParam(value = "id") Long id) {
        return ecService.getEc(id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public int edit(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                    @RequestParam("params") String params) {
        JSONObject jsonObject = JSONObject.fromObject(params);
        String name = jsonObject.getString("corpName");// 集团名称
        String corpAccount = jsonObject.getString("corpAccount");// 集团客户帐号
        String cardid = jsonObject.getString("cardid");// 集团证件号
        String email = jsonObject.getString("email");// 集团邮箱
        String contact = jsonObject.getString("linkMan");// 集团联系人
        String contact_phone = jsonObject.getString("phoneNum");// 集团联系人电话

        Ec ec = ecService.getEc(id);
        if (ec == null) {
            if (!ecService.valadateCorpName(name))
                return 2;// "集团名称已存在！";
            if (!ecService.validateCardid(cardid))
                return 3;// "企业证件号已存在！";
            if (!ecService.validateCorpAccount(corpAccount))
                return 4;// "企业客户帐号已存在！";

            ec = new Ec();
        }
        ec.setCorpName(name);
        ec.setCorpAccount(corpAccount);
        ec.setCardid(cardid);
        ec.setEmail(email);
        ec.setLinkMan(contact);
        ec.setPhoneNum(contact_phone);

        ecService.saveEc(ec);
        return 1;// "";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Boolean delete(@RequestParam(value = "params") List<Long> ids) {
        return ecService.delByIds(ids);
    }
}
