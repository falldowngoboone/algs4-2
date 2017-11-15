package com.falldowngoboone.classwork;

public class TestFolder {
    private static final String TEST_FILE_DIR = System.getProperty("user.dir") + "/src/test";
    private String folder;


    public TestFolder(String folder) {
        this.folder = folder;
    }

    public String getFilePath(String filename) {
        return TEST_FILE_DIR.concat("/").concat(folder).concat("/").concat(filename);
    }
}