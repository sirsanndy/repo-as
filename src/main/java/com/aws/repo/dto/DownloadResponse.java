package com.aws.repo.dto;

public class DownloadResponse<T> {
    private T data;
    private long fileLength;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }
}
