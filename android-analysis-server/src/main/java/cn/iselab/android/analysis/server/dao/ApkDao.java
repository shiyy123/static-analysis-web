package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.Apk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ApkDao extends JpaRepository< Apk, Long>  {
    public Apk findByName(String name);
    public Apk findByMd5(String md5);
    public Apk findById(Long id);
    public List<Apk> findAll();

    @Modifying
    @Query("update Apk a set a.status=?1 where a.md5=?2")
    public int update(String status,String id);
}
