package com.example.oams.connection;

public class Constants {
    public static final String ROOT_IP = "http://192.168.43.49/";
    public static final String ROOT_URL = ROOT_IP+"android_attendence/";
    public static final String ROOT_AD = ROOT_IP+"android_attendence/admin/";
    public static final String ROOT_IMG = ROOT_IP+"android_attendence/admin/teacher_image/";
    public static final String ROOT_PDF = ROOT_IP+"android_attendence/mpdf/PDFReport.php?";
    public static final String ROOT_XSL = ROOT_IP+"android_attendence/phpspreadsheet/phpspreadsheet.php?";

    public  static final String URL_LOGIN = ROOT_URL+"check_teacher_login.php";
    public  static final String URL_ADDATN = ROOT_URL+"attendance_action.php";
    public  static final String URL_STD_LIST= ROOT_URL+"list.php";
    public  static final String URL_SHORTAGE_LIST= ROOT_URL+"shortage-list.php";
    public  static final String URL_DASH_DATA= ROOT_URL+"index1.php";


    public  static final String URL_ADMIN_LOGIN= ROOT_AD+"check_admin_login.php";
    public  static final String URL_GRADE= ROOT_AD+"grade_action.php";
    public  static final String URL_TEACH_LIST= ROOT_AD+"teacher_action.php";
    public  static final String URL_STD= ROOT_AD+"student_action.php";
}