package cn.edu.zucc.common;

public enum ResultCode {
    SUCCESS(200,"成功"),
    FAILURE(400,"失败");
    final int code;
    final String message;
    public int getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    private ResultCode(final int code,final String message)
    {
        this.code=code;
        this.message=message;
    }
}
