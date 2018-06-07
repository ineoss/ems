package cn.unuai.util;

/**
 * Created by SunShuai on 2018/4/22.
 */
public class Res {
    private String state = "success";//success or error
    private Object data = null;

    public Res(String state, Object data) {
        this.state = state;
        this.data = data;
    }

    public Res(Object data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
