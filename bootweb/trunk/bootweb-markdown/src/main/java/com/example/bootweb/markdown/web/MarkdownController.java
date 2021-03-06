package com.example.bootweb.markdown.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootweb.markdown.service.MarkdownService;
import com.ibm.icu.text.MessageFormat;

@RestController
@RequestMapping(value = { "/md" })
public class MarkdownController {

  @Autowired
  private MarkdownService markdownService;

  @RequestMapping(value = "html", method = RequestMethod.GET)
  public Map<String, Object> text(@RequestParam(name = "filePath", required = false) String filePath)
      throws IOException {
    InputStream inputStream = null;
    Map<String, Object> result = new HashMap<String, Object>();
    if (StringUtils.isBlank(filePath)) {// 默认文件。
      result.put("html", "没有发现文件。");
      return result;
    } else {
      File targetFile = new File(filePath);
      if (!targetFile.exists()) {
        throw new IOException(//
            MessageFormat.format("文件名称[{0}]不存在。", filePath));
      }
      if (!targetFile.isFile()) {
        throw new IOException(//
            MessageFormat.format("文件名称[{0}]不是一个正常的文件。", filePath));
      }
      inputStream = new FileInputStream(targetFile);
    }
    result.put("html", markdownService.markdownToHtml(inputStream));
    return result;
  }

}
