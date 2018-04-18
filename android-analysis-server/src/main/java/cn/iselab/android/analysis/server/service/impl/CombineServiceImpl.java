package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.service.CombineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CombineServiceImpl implements CombineService{
    @Override
    public ArrayList<Vulnerability> combine(ArrayList<Vulnerability> manifest, ArrayList<Vulnerability> androbugs) {
        ArrayList<String> t1=new ArrayList<String>();
        ArrayList<String> t2=new ArrayList<String>();
        for(Vulnerability v:manifest){
            t1.add(v.getTitle());
        }
        for(Vulnerability v:androbugs){
            t2.add(v.getTitle());
        }

        for(int i=0;i<t1.size();++i){
            if(!t2.contains(t1.get(i))){
                androbugs.add(manifest.get(i));
            }
        }

        return androbugs;
    }
}
