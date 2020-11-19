package com.yjf;

import com.yjf.dao.ArticleJpaDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public  class XbApplicationTests {
  @Autowired
  ArticleJpaDao articleDao;
    @Test
    public   void test01(){

    }

}
