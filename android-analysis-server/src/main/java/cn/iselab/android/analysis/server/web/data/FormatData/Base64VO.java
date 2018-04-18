package cn.iselab.android.analysis.server.web.data.FormatData;

import java.util.ArrayList;

/**
 * Description: the class of the Base64 type vulnerability
 * encode_string
 * decode_string
 * class_list : the list of the class where the string appears
 */
public class Base64VO extends FormatFileVO{
    private String encode_string;
    private String decode_string;
    private ArrayList<CmdVO> class_list;

    public Base64VO() {
    }

    public Base64VO(String encode_string, String decode_string, ArrayList<CmdVO> class_list) {
        this.encode_string = encode_string;
        this.decode_string = decode_string;
        this.class_list = class_list;
    }

    public String getEncode_string() {
        return encode_string;
    }

    public void setEncode_string(String encode_string) {
        this.encode_string = encode_string;
    }

    public String getDecode_string() {
        return decode_string;
    }

    public void setDecode_string(String decode_string) {
        this.decode_string = decode_string;
    }

    public ArrayList<CmdVO> getClass_list() {
        return class_list;
    }

    public void setClass_list(ArrayList<CmdVO> class_list) {
        this.class_list = class_list;
    }
}
