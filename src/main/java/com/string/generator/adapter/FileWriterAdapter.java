package com.string.generator.adapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterAdapter
{
    private final FileWriter fileWriter;

    public FileWriterAdapter(String path) throws IOException
    {
        File generatedFile = new File(path);
        this.fileWriter = new FileWriter(generatedFile, true);
    }

    public void writeStringToFile(String currentString) throws IOException
    {
        this.fileWriter.write(currentString);
        this.fileWriter.write('\n');
        this.fileWriter.flush();
    }

    public void closeFile() throws IOException
    {
        this.fileWriter.close();
    }
}


