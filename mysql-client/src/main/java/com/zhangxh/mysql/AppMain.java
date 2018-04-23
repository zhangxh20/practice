package com.zhangxh.mysql;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;
import com.mysql.cj.xdevapi.Schema;
import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.SessionFactory;

public class AppMain {

    public static void main(String[] args) {
        Session mySession = new SessionFactory().getSession("mysqlx://localhost:33060/java?user=root&password=");
        Schema myDb = mySession.getSchema("java");
        Collection coll = myDb.getCollection("user");
        coll.add("{\"name\":\"zhang\",\"age\":33}").execute();
        DocResult result =  coll.find("name like :name").bind("name", "zhang").execute();
        while (result.hasNext()) {
            DbDoc doc = result.next();
            System.out.println(doc.get("age"));
        }
    }
}
