package com.atmecs.testfunction.utils;

public class DataProvider {

    private final static PathLocator pathLocator = new PathLocator();

    @org.testng.annotations.DataProvider(name = "loginData")
    public static Object[][] createData1() {
        ExcelData excelObject = new ExcelData();
        Object[][] excelData =
                excelObject.getExcelData(pathLocator.getTestDataPath("loginData.xlsx"), "Sheet1");
        return excelData;
    }
    
    @org.testng.annotations.DataProvider(name = "loginData2")
    public static Object[][] createData11() {
        ExcelData excelObject = new ExcelData();
        Object[][] excelData =
                excelObject.getExcelData(pathLocator.getTestDataPath("loginData2.xlsx"), "Sheet1");
        return excelData;
    }
    
    
    @org.testng.annotations.DataProvider(name = "RegisterData")
    public static Object[][] createData2()
    {
    	ExcelData excelObject=new ExcelData();
    	Object[][] exceldata=excelObject.getExcelData(pathLocator.getTestDataPath("RegisterData.xlsx"), "Sheet1");
    	return exceldata;
    }
}