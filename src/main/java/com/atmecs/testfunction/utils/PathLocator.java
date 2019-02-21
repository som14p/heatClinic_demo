package com.atmecs.testfunction.utils;

import java.io.File;

public class PathLocator {

    private final String separator = File.separator;

    public String getSrcMainResourcePath(String fileName) {
        return System.getProperty("user.dir") + separator + "src" + separator + "main" + separator
                + "resources" + separator + fileName;
    }

    public String getTestDataPath(String fileName) {
        return System.getProperty("user.dir") + separator + "src" + separator + "main" + separator
                + "java" + separator + "com" + separator + "atmecs" + separator + "testdata"
                + separator + fileName;

    }

}
