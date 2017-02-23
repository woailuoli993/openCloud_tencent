package udp_model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by vici on 09/02/2017.
 */

public class UrlInfo {
    private String url;
    private int status;
    private int urltype;
    private int eviltype;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUrltype() {
        return urltype;
    }

    public void setUrltype(int urltype) {
        this.urltype = urltype;
    }

    public List<Integer> getEviltype() {

        String temp = Integer.toString(eviltype);
        int[] newGuess = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++)
        {
            newGuess[i] = temp.charAt(i) - '0';
        }
        List<Integer> eviltypes= new ArrayList<>();
        for (int index = 0; index < newGuess.length; index++)
        {
            eviltypes.add(newGuess[index]);
        }
        return eviltypes;
    }

    public void setEviltype(int eviltype) {
        this.eviltype = eviltype;
    }

    public String getUrlTypeCH() {
        switch (this.getUrltype()) {
            case 0:
            case 1:
                return "未知";
            case 2:
                return "风险";
            case 3:
                return "安全";
        }
        return "未知";
    }


    public List<String> getEvilTypeCH() {
        List<String> evillist_ch = new ArrayList<String>();

        for (int parttype : this.getEviltype()
                ) {

            switch (parttype) {
                case 0:
                    evillist_ch.add("绿色网站");
                    break;
                case 1:
                    evillist_ch.add("社工欺诈");
                    break;
                case 2:
                    evillist_ch.add("信息诈骗");
                    break;
                case 3:
                    evillist_ch.add("虚假诈骗");
                    break;
                case 4:
                    evillist_ch.add("恶意文件");
                    break;
                case 5:
                    evillist_ch.add("博彩网站");
                    break;
                case 6:
                    evillist_ch.add("色情网站");
                    break;
                case 7:
                    evillist_ch.add("风险网站");
                    break;
                case 8:
                    evillist_ch.add("非法内容");
                    break;

            }
        }
        return evillist_ch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("URL=" + getUrl() + "\n");
        sb.append("STATUS=" + getStatus() + "\n");
        sb.append("URLTYPE=" + getUrltype() + "\n");
        sb.append("EVILTYPE=" + getEviltype() + "\n");
        sb.append("URLTYPE_CH=" + getUrlTypeCH() + "\n");
        sb.append("EVILTYPE_CH=" + getEvilTypeCH() + "\n");

        return sb.toString();
    }
}
