package com.hadlink.lay_s.ui.datamanager.bean;

import java.util.List;

/**
 * Created by zhouml on 2015/12/5.
 */
public class Test {



        public int classNum;
        public String className;
        /**
         * id : 1
         * name : 姚伟辉0
         * sex : 1
         * age : 11
         * listLogin : [{"expertID":null,"expertPhone":"100860","expertStatus":1,"disableStartTime":null,"disableEndTime":null,"password":"6666660"},{"expertID":null,"expertPhone":"100861","expertStatus":2,"disableStartTime":null,"disableEndTime":null,"password":"6666661"},{"expertID":null,"expertPhone":"100862","expertStatus":3,"disableStartTime":null,"disableEndTime":null,"password":"6666662"},{"expertID":null,"expertPhone":"100863","expertStatus":4,"disableStartTime":null,"disableEndTime":null,"password":"6666663"},{"expertID":null,"expertPhone":"100864","expertStatus":5,"disableStartTime":null,"disableEndTime":null,"password":"6666664"},{"expertID":null,"expertPhone":"100865","expertStatus":6,"disableStartTime":null,"disableEndTime":null,"password":"6666665"},{"expertID":null,"expertPhone":"100866","expertStatus":7,"disableStartTime":null,"disableEndTime":null,"password":"6666666"},{"expertID":null,"expertPhone":"100867","expertStatus":8,"disableStartTime":null,"disableEndTime":null,"password":"6666667"},{"expertID":null,"expertPhone":"100868","expertStatus":9,"disableStartTime":null,"disableEndTime":null,"password":"6666668"},{"expertID":null,"expertPhone":"100869","expertStatus":10,"disableStartTime":null,"disableEndTime":null,"password":"6666669"}]
         */

        public List<TestBeanEntity> testBean;

        public static class TestBeanEntity {
            public int id;
            public String name;
            public int sex;
            public int age;
            /**
             * expertID : null
             * expertPhone : 100860
             * expertStatus : 1
             * disableStartTime : null
             * disableEndTime : null
             * password : 6666660
             */

            public List<ListLoginEntity> listLogin;

            public static class ListLoginEntity {
                public Object expertID;
                public String expertPhone;
                public int expertStatus;
                public Object disableStartTime;
                public Object disableEndTime;
                public String password;
            }
        }

}
