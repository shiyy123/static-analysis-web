package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Transactional
public interface CheckListDao extends JpaRepository<CheckList, Long> {
    public CheckList findById(Long id);
    public CheckList findByTitle(String title);
    public CheckList findByTitleAndLevel(String title,String level);
}
