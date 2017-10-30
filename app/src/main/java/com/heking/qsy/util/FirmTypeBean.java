package com.heking.qsy.util;

import java.io.Serializable;
import java.util.ArrayList;

public class FirmTypeBean {

    private ArrayList<Data> Data;

    public ArrayList<Data> getData() {
        return Data;
    }

    public void setData(ArrayList<Data> data) {
        Data = data;
    }

    public class Data implements Serializable {
        private String FirmID;// "F500234000000294",
        private String FirmName;// "罗医生诊所",
        private String Address;// "攀枝花市仁和区土城南街134号 ",
        private String Email;// "186786413@qq.com",
        private String LegalRepresentative;// "黄崇",
        private String LegalRepresentativePhones;// "0812-5193437 ",
        private String Manager;// "李久全",
        private String ManagerPhones;// "13890725735",
        private String HeadOfQuality;// "马苏",
        private String QualityHeadPhones;// "18523600232",
        private String FirmTypeName;// "罗医生诊所",
        private String FirmTypeName1;// "罗医生诊所",
        private String AreaName;
        private String AreaCode;

        private String FirmTypeID;// 15,
        private ArrayList<AnnualRating> AnnualRating;
        private ArrayList<Monitors> Monitors;
        private ArrayList<FirmLicenseData> FirmLicenseData;
        private boolean Ioos;
        private int mRating;
        private double FirmReviewAverage;
        double longitude;
        double latitude;
        String city;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getFirmReviewAverage() {
            return FirmReviewAverage;
        }

        public void setFirmReviewAverage(double firmReviewAverage) {
            FirmReviewAverage = firmReviewAverage;
        }

        public String getFirmTypeName1() {
            return FirmTypeName1;
        }

        public void setFirmTypeName1(String firmTypeName1) {
            FirmTypeName1 = firmTypeName1;
        }

        public int getmRating() {
            return mRating;
        }

        public void setmRating(int mRating) {
            this.mRating = mRating;
        }

        public boolean isIoos() {
            return Ioos;
        }

        public void setIoos(boolean ioos) {
            Ioos = ioos;
        }

        public String getFirmID() {
            return FirmID;
        }

        public void setFirmID(String firmID) {
            FirmID = firmID;
        }

        public String getFirmName() {
            return FirmName;
        }

        public void setFirmName(String firmName) {
            FirmName = firmName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getLegalRepresentative() {
            return LegalRepresentative;
        }

        public void setLegalRepresentative(String legalRepresentative) {
            LegalRepresentative = legalRepresentative;
        }

        public String getLegalRepresentativePhones() {
            return LegalRepresentativePhones;
        }

        public void setLegalRepresentativePhones(String legalRepresentativePhones) {
            LegalRepresentativePhones = legalRepresentativePhones;
        }

        public String getManager() {
            return Manager;
        }

        public void setManager(String manager) {
            Manager = manager;
        }

        public String getManagerPhones() {
            return ManagerPhones;
        }

        public void setManagerPhones(String managerPhones) {
            ManagerPhones = managerPhones;
        }

        public String getHeadOfQuality() {
            return HeadOfQuality;
        }

        public void setHeadOfQuality(String headOfQuality) {
            HeadOfQuality = headOfQuality;
        }

        public String getQualityHeadPhones() {
            return QualityHeadPhones;
        }

        public void setQualityHeadPhones(String qualityHeadPhones) {
            QualityHeadPhones = qualityHeadPhones;
        }

        public String getFirmTypeName() {
            return FirmTypeName;
        }

        public void setFirmTypeName(String firmTypeName) {
            FirmTypeName = firmTypeName;
        }

        public String getFirmTypeID() {
            return FirmTypeID;
        }

        public void setFirmTypeID(String firmTypeID) {
            FirmTypeID = firmTypeID;
        }

        public ArrayList<AnnualRating> getAnnualRating() {
            return AnnualRating;
        }

        public void setAnnualRating(ArrayList<AnnualRating> annualRating) {
            AnnualRating = annualRating;
        }

        public ArrayList<Monitors> getMonitors() {
            return Monitors;
        }

        public void setMonitors(ArrayList<Monitors> monitors) {
            Monitors = monitors;
        }

        public ArrayList<FirmLicenseData> getFirmLicenseData() {
            return FirmLicenseData;
        }

        public void setFirmLicenseData(ArrayList<FirmLicenseData> firmLicenseData) {
            FirmLicenseData = firmLicenseData;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String areaName) {
            AreaName = areaName;
        }

        public String getAreaCode() {
            return AreaCode;
        }

        public void setAreaCode(String areaCode) {
            AreaCode = areaCode;
        }


        public class AnnualRating implements Serializable {
            private String ID;// 35,
            private String FirmID;// "F500234000000294",
            private String EvaluationTtime;// "2016-08-08T00:00:00",
            private String AnnualAverageAcore;// 6,
            private String InternOpinion;// null,
            private String ReviewOpinion;// null,
            private String Reviewcomments;// null,
            private String EnterpriseRankingID;// "35",
            private String Rating;// "不予评级"

            public String getID() {
                return ID;
            }

            public void setID(String iD) {
                ID = iD;
            }

            public String getFirmID() {
                return FirmID;
            }

            public void setFirmID(String firmID) {
                FirmID = firmID;
            }

            public String getEvaluationTtime() {
                return EvaluationTtime;
            }

            public void setEvaluationTtime(String evaluationTtime) {
                EvaluationTtime = evaluationTtime;
            }

            public String getAnnualAverageAcore() {
                return AnnualAverageAcore;
            }

            public void setAnnualAverageAcore(String annualAverageAcore) {
                AnnualAverageAcore = annualAverageAcore;
            }

            public String getInternOpinion() {
                return InternOpinion;
            }

            public void setInternOpinion(String internOpinion) {
                InternOpinion = internOpinion;
            }

            public String getReviewOpinion() {
                return ReviewOpinion;
            }

            public void setReviewOpinion(String reviewOpinion) {
                ReviewOpinion = reviewOpinion;
            }

            public String getReviewcomments() {
                return Reviewcomments;
            }

            public void setReviewcomments(String reviewcomments) {
                Reviewcomments = reviewcomments;
            }

            public String getEnterpriseRankingID() {
                return EnterpriseRankingID;
            }

            public void setEnterpriseRankingID(String enterpriseRankingID) {
                EnterpriseRankingID = enterpriseRankingID;
            }

            public String getRating() {
                return Rating;
            }

            public void setRating(String rating) {
                Rating = rating;
            }

        }

        public class Monitors implements Serializable {
            private String ID;// 10,
            private String FirmID;// "F500234000000294",
            private String Name;// "视频1",
            private String Model;// null,
            private String IP;// "192.168.0.100",
            private String Port;// 8000,
            private String UserName;// "admin",
            private String Password;// "hk123*456"

            public String getID() {
                return ID;
            }

            public void setID(String iD) {
                ID = iD;
            }

            public String getFirmID() {
                return FirmID;
            }

            public void setFirmID(String firmID) {
                FirmID = firmID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getModel() {
                return Model;
            }

            public void setModel(String model) {
                Model = model;
            }

            public String getIP() {
                return IP;
            }

            public void setIP(String iP) {
                IP = iP;
            }

            public String getPort() {
                return Port;
            }

            public void setPort(String port) {
                Port = port;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String userName) {
                UserName = userName;
            }

            public String getPassword() {
                return Password;
            }

            public void setPassword(String password) {
                Password = password;
            }

        }

        public class FirmLicenseData implements Serializable {
            private String ID;// 16,
            private String FirmID;// "F500234000000294",
            private String LocalID;// null,
            private String LicenseType;// "营业执照",
            private String Name;// "营业执照",
            private String LicenseCode;// "YYZZ2016080504",
            private String DateOfIssue;// "2016-08-05T00:00:00",
            private String DateOfDeadline;// "2018-08-05T00:00:00",
            private String Publisher;// "食品药品监督管理局",
            private String Content;// null,
            private String FileID;// null,
            private String UploadTime;// null,
            private String FileString;// null,
            private String FileExtension;// null,
            private String EarlyWarningTime;// 90

            public String getID() {
                return ID;
            }

            public void setID(String iD) {
                ID = iD;
            }

            public String getFirmID() {
                return FirmID;
            }

            public void setFirmID(String firmID) {
                FirmID = firmID;
            }

            public String getLocalID() {
                return LocalID;
            }

            public void setLocalID(String localID) {
                LocalID = localID;
            }

            public String getLicenseType() {
                return LicenseType;
            }

            public void setLicenseType(String licenseType) {
                LicenseType = licenseType;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getLicenseCode() {
                return LicenseCode;
            }

            public void setLicenseCode(String licenseCode) {
                LicenseCode = licenseCode;
            }

            public String getDateOfIssue() {
                return DateOfIssue;
            }

            public void setDateOfIssue(String dateOfIssue) {
                DateOfIssue = dateOfIssue;
            }

            public String getDateOfDeadline() {
                return DateOfDeadline;
            }

            public void setDateOfDeadline(String dateOfDeadline) {
                DateOfDeadline = dateOfDeadline;
            }

            public String getPublisher() {
                return Publisher;
            }

            public void setPublisher(String publisher) {
                Publisher = publisher;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String content) {
                Content = content;
            }

            public String getFileID() {
                return FileID;
            }

            public void setFileID(String fileID) {
                FileID = fileID;
            }

            public String getUploadTime() {
                return UploadTime;
            }

            public void setUploadTime(String uploadTime) {
                UploadTime = uploadTime;
            }

            public String getFileString() {
                return FileString;
            }

            public void setFileString(String fileString) {
                FileString = fileString;
            }

            public String getFileExtension() {
                return FileExtension;
            }

            public void setFileExtension(String fileExtension) {
                FileExtension = fileExtension;
            }

            public String getEarlyWarningTime() {
                return EarlyWarningTime;
            }

            public void setEarlyWarningTime(String earlyWarningTime) {
                EarlyWarningTime = earlyWarningTime;
            }

        }

    }

}


