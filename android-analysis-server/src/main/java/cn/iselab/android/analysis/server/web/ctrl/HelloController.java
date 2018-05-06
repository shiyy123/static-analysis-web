package cn.iselab.android.analysis.server.web.ctrl;

import cn.iselab.android.analysis.server.constant.ResStatus;
import cn.iselab.android.analysis.server.service.ApkAnalysisService;
import cn.iselab.android.analysis.server.service.SCAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloController {
    @Autowired
    private ApkAnalysisService as;

    @Autowired
    private SCAnalysisService scAnalysisService;

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public void analysis(@RequestParam("MD5") String MD5, HttpServletResponse response) {
//        System.out.println("MD5:"+MD5);
        //MD5="9ad368a091028d3988429b92eaec36b9";

//        int result=as.analysis(MD5);
        int result = scAnalysisService.analysis(MD5);

        if(result == 1) {
            response.setStatus(ResStatus.analysis_error);
        } else {
            response.setStatus(ResStatus.success);
        }
        //new Thread(new Thread1(MD5)).start();
    }

    class Thread1 implements Runnable{
        private String md5;
        Thread1(String md5){
            this.md5=md5;
        }
        @Override
        public void run(){
            as.analysis(md5);
        }
    }
}
