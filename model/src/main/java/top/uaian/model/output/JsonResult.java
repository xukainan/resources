package top.uaian.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * description:  <br>
 * date: 2020/5/7 16:10 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Data
public class JsonResult {
    private String status;
    private boolean success;
    private String msg;
    private Object result;

    public JsonResult renderSuccess() {
        setMsg("成功");
        setSuccess(true);
        setStatus("200");
        return this;
    }

    public JsonResult renderSuccess(String msg) {
        setMsg(msg);
        setSuccess(true);
        setStatus("200");
        return this;
    }

    public JsonResult renderSuccess(String msg, String status) {
        renderSuccess(msg);
        setStatus(status);
        return this;
    }

    public JsonResult renderSuccess(String msg, String status, Object obj) {
        renderSuccess(msg, status);
        setResult(obj);
        return this;
    }

    public JsonResult renderError() {
        setMsg("失败");
        setSuccess(false);
        setStatus("500");
        return this;
    }

    public JsonResult renderError(String msg) {
        renderError();
        setMsg(msg);
        return this;
    }

    public JsonResult renderError(String msg, String status) {
        renderError(msg);
        setStatus(status);
        return this;
    }

    public JsonResult renderError(String msg, String status, Object obj) {
        renderError(msg, status);
        setResult(obj);
        return this;
    }

}
