package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.Vulnerability;

import java.util.ArrayList;

public interface CombineService {
    public ArrayList<Vulnerability> combine(ArrayList<Vulnerability> manifest,ArrayList<Vulnerability> androbugs);
}
