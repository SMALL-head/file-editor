package src.context;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * @author zyc
 * @version 1.0
 */
public class FileEditorContext {
    final static FileEditorContext singleton = new FileEditorContext();

    public static FileEditorContext getContext() {
        return singleton;
    }

    BufferedReader reader;
    BufferedWriter writer;
    String activeFile;

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public String getActiveFile() {
        return activeFile;
    }

    public void setActiveFile(String activeFile) {
        this.activeFile = activeFile;
    }
}
