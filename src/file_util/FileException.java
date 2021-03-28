package file_util;

public class FileException extends Exception {

    public enum FileError {
        COLUMN_TYPE_SPECIFIED_BY_FILE_INVALID
    }

    FileError fileError;
    int lineNo;

    public FileException(FileError fileError, int lineNo) {
        this.lineNo = lineNo;
        this.fileError = fileError;
    }

    @Override
    public String getMessage() {
        return (this.fileError.name() + " " + "on line" + " " + this.lineNo);
    }
}
