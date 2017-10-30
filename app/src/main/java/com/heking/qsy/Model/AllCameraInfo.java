package com.heking.qsy.Model;

import java.util.List;

/**
 * Created by ljj on 2017/9/29.
 */

public class AllCameraInfo {

    /**
     * total : 413
     * rows : [{"ID":11,"FirmID":"F511323000000018","Name":"一车间换鞋大厅","Model":"51112300001310000110","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":12,"FirmID":"F511323000000018","Name":"一车间办公室","Model":"51112300001310000109","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":14,"FirmID":"F511323000000018","Name":"理化实验室1","Model":"51112300001310000104","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":15,"FirmID":"F511323000000018","Name":"理化实验室2","Model":"51112300001310000103","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":16,"FirmID":"F511323000000018","Name":"理化实验室3","Model":"51112300001310000102","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":17,"FirmID":"F511323000000018","Name":"实验动物房","Model":"51112300001310000036","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":18,"FirmID":"F511323000000018","Name":"阴凉库（I005）","Model":"51112300001310000101","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":19,"FirmID":"F511323000000018","Name":"阴凉库（I006）","Model":"51112300001310000100","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":20,"FirmID":"F511323000000018","Name":"阴凉库（I005）","Model":"51112300001310000099","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":21,"FirmID":"F511323000000018","Name":"阴凉库（I006）","Model":"51112300001310000098","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":22,"FirmID":"F511323000000018","Name":"阴凉库（I007）","Model":"51112300001310000097","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":23,"FirmID":"F511323000000018","Name":"阴凉库（I007）","Model":"51112300001310000096","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":24,"FirmID":"F511323000000018","Name":"阴凉库（I008）","Model":"51112300001310000095","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":25,"FirmID":"F511323000000018","Name":"阴凉库（I008）","Model":"51112300001310000094","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":26,"FirmID":"F511323000000018","Name":"成品库 阴凉库（I012）","Model":"51112300001310000038","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":27,"FirmID":"F511323000000018","Name":"原辅料库1（H023）","Model":"51112300001310000035","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":28,"FirmID":"F511323000000018","Name":"包装材料库3（H021）","Model":"51112300001310000034","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":29,"FirmID":"F511323000000018","Name":"参观走廊","Model":"51112300001310000020","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":30,"FirmID":"F511323000000018","Name":"包装材料库3（H021）","Model":"51112300001310000033","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"},{"ID":31,"FirmID":"F511323000000018","Name":"阿莫西林胶囊生产线","Model":"51112300001310000019","IP":"117.173.43.121","Port":81,"UserName":"admin","Password":"heking08*24"}]
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * ID : 11
         * FirmID : F511323000000018
         * Name : 一车间换鞋大厅
         * Model : 51112300001310000110
         * IP : 117.173.43.121
         * Port : 81
         * UserName : admin
         * Password : heking08*24
         */

        private int ID;
        private String FirmID;
        private String Name;
        private String Model;
        private String IP;
        private int Port;
        private String UserName;
        private String Password;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getFirmID() {
            return FirmID;
        }

        public void setFirmID(String FirmID) {
            this.FirmID = FirmID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getModel() {
            return Model;
        }

        public void setModel(String Model) {
            this.Model = Model;
        }

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }

        public int getPort() {
            return Port;
        }

        public void setPort(int Port) {
            this.Port = Port;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }
    }
}
