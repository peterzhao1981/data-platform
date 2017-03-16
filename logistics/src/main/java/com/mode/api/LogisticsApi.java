package com.mode.api;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mode.common.ClubFactoryData;
import com.mode.service.LogisticsService;
import com.mode.service.impl.ClubFactoryCrawler;
import com.mode.test.A;
import com.mode.test.B;
import com.mode.test.Com1;
import com.mode.test.Com2;
import com.mode.test.TestRepo;

/**
 * Created by zhaoweiwei on 16/12/28.
 */
@RestController
public class LogisticsApi {

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private ClubFactoryCrawler clubFactoryCrawler;

//    @Autowired
//    private A a;
//
//    @Autowired
//    private B b;
//
//    @Autowired
//    private Com1 com1;

//    @Autowired
//    private TestRepo testRepo;

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Map<String, String> getLogistics(@RequestBody List<String> trackNums) {
        System.out.println(trackNums.size());
        return logisticsService.getLogistics(trackNums);
    }

//    @RequestMapping(value = "/process", method = RequestMethod.POST)
//    public void genClubFactoryData() throws Exception {
//        clubFactoryCrawler.process();
//    }

    @RequestMapping(value = "/clubFactory", method = RequestMethod.GET)
    public Map<String, Object> listClubFactoryData(@RequestParam(value = "limit") Integer limit,
                                                     @RequestParam(value = "offset") Integer offset) {
        Map<String, Object> result = new HashMap<>();
        result.put("list", clubFactoryCrawler.listClubFactoryData(limit, offset));
        result.put("total", clubFactoryCrawler.countClubFactoryData());

        return result;
    }

//    protected void beforeBodyWriteInternal() {
//        // Identify of filter to use; if empty String (""), no filter is to be used.
//        //JsonFilter annotation = returnType.getMethodAnnotation(JsonFilter.class);
//        //String filterName = annotation.value();
////
////        // Get included fields from request
////        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
////        String[] includedFields = servletRequest.getParameterValues(Defaults.JSON_ENTITY_SELECTOR);
////        System.err.println("includedFields 1:" + Arrays.toString(includedFields));
////        if ((includedFields == null) || (includedFields.length == 0)) {
////            includedFields = new String[]{"**"};
////            System.err.println("includedFields 2:" + Arrays.toString(includedFields));
////        }
////
////        // Apply the filter
////        System.err.println("includedFields 3:" + Arrays.toString(includedFields));
//
//        String[] inclued = {"id", "username", "authorities"};
//        //HttpServletRequest servletRequest = ((ServletServerHttpRequest) request)
//         //       .getServletRequest();
//        //String[] includedFields = servletRequest.getParameterValues(Defaults
//        // .JSON_ENTITY_SELECTOR);
//       //System.err.println("includedFields:" + String.format("%s", ));
//        String filterName = "abc";
//        FilterProvider filters = new SimpleFilterProvider()
//                .addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(String.format("%s", includedFields)));
//        //bodyContainer.setFilters(filters);
//
//        //System.err.println("bodyContainer:" + bodyContainer.getFilters() + ", " + bodyContainer
//        //        .getValue());
//
//    }
//
////    @RequestMapping(value = "/test", method = RequestMethod.GET)
////    public int get() {
////        return b.getB();
////    }
////
////    @RequestMapping(value = "/test1", method = RequestMethod.GET)
////    public int get1() {
////        return testRepo.getC();
////    }
////
////    @RequestMapping(value = "/test2", method = RequestMethod.GET)
////    public int get2() {
////        A a1 = com1.createA();
////        System.out.println(a1);
////        System.out.println(a);
////        if (a1 != a) {
////            System.out.println("not equals");
////        }
////        return com1.createA().getA();
////    }

}
