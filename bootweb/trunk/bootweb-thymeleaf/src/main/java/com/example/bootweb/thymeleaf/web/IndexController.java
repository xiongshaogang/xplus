package com.example.bootweb.thymeleaf.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bootweb.thymeleaf.web.view.Dashboard;
import com.example.bootweb.thymeleaf.web.view.Index;

@Controller
public class IndexController {

  @Value("${dashboard.number:4}")
  private Integer dashboardNumber;
  
  @RequestMapping(value = {"/index.html", "/index", "/"})
  public String index(Model model) {
    model.addAttribute("title", "Thymeleaf示例的标题");
    Index index = new Index();
    
    index.getDashboards().add(make("Git 入门到专家指南", //
        "https://progit.bootcss.com/", //
        "data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==", //
        "Git中文版（第二版）是一本详细的Git指南，主要介绍了Git的使用基础和原理，让你从Git初学者成为Git专家。"));
    
    index.getDashboards().add(make("Metro 风格的 Bootstrap", //
        "http://www.bootcss.com/p/flat-ui/", //
        "data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==", //
        "Flat UI 是基于Bootstrap做的Metro化改造。Flat UI包含了很多Bootstrap提供的组件，但是外观更加漂亮。"));
    
    index.getDashboards().add(make("菜鸟教程", //
        "http://www.runoob.com/", //
        "data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==", //
        "大量入门级的菜鸟教程，包括前端、服务端入门级教程。"));
    
    index.getDashboards().add(make("Bootstrap框架", //
        "http://www.bootcss.com/", //
        "data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==", //
        "简洁、直观、强悍的前端开发框架，让web开发更迅速、简单。"));
    
    index.setDashboards(new ArrayList<>(index.getDashboards()).subList(0, //
        index.getDashboards().size() >= dashboardNumber ? dashboardNumber : index.getDashboards().size()));
    
    index.getList().addAll(index.getDashboards());
    index.getList().add(make("Thymeleaf模板", //
        "http://www.thymeleaf.org/", //
        null, //
        "Thymeleaf is a modern server-side Java template engine for both web and standalone environments."));
    
    model.addAttribute("data", index);
    
    return "index";
  }
  
  private Dashboard make(String text, String url, String src, String desc) {
    Dashboard dashboard = new Dashboard();
    dashboard.setText(text);
    dashboard.setSrc(src);
    dashboard.setUrl(url);
    dashboard.setDesc(desc);
    return dashboard;
  }
  
}
