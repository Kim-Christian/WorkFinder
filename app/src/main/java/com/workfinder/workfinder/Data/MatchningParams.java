package com.workfinder.workfinder.Data;

/**
 * Created by Kim-Christian on 2017-09-03.
 */

public class MatchningParams {
    private String nyckelord;
    private String fritext;
    private String lan;
    private String lanKod;
    private String kommun;
    private String kommunKod;
    private String anstallningstyp;
    private String anstallningstypKod;

    public MatchningParams() {
    }

    public String getNyckelord() {
        return nyckelord;
    }

    public String getFritext() {
        return fritext;
    }

    public String getLan() {
        return lan;
    }

    public String getLanKod() { return lanKod; }

    public String getKommun() {
        return kommun;
    }

    public String getKommunKod() { return kommunKod; }

    public String getAnstallningstyp() {
        return anstallningstyp;
    }

    public String getAnstallningstypKod() { return anstallningstypKod; }

    private void updateNyckelord() {
        nyckelord = "";
        if (fritext != null) {
            String word = "";
            char c;
            for (int i = 0; i < fritext.length(); i++) {
                c = fritext.charAt(i);
                if (Character.isLetter(c)) {
                    word += c;
                } else if (word.length() > 0) {
                    if (!nyckelord.equals("")) {
                        nyckelord += "&";
                    }
                    nyckelord += word;
                    word = "";
                }
            }
            if (word.length() > 0) {
                if (!nyckelord.equals("")) {
                    nyckelord += "&";
                }
                nyckelord += word;
            }
        }
    }

    public void setFritext(String fritext) {
        this.fritext = fritext;
        updateNyckelord();
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public void setLanKod(String lanKod) {
        this.lanKod = lanKod;
    }

    public void setKommun(String kommun) {
        this.kommun = kommun;
    }

    public void setKommunKod(String kommunKod) {
        this.kommunKod = kommunKod;
    }

    public void setAnstallningstyp(String anstallningstyp) {
        this.anstallningstyp = anstallningstyp;
    }

    public void setAnstallningstypKod(String anstallningstypKod) {
        this.anstallningstypKod = anstallningstypKod;
    }

    public boolean hasNyckelord() {
        return nyckelord.length() > 0;
    }

    public boolean hasLan() {
        return !(lanKod.equals("0") || lanKod.equals("") || lanKod == null);
    }

    public boolean hasKommun() {
        return !(kommunKod.equals("0") || kommunKod.equals("") || kommunKod == null);
    }

    public boolean hasAnstallningstyp() {
        return !(anstallningstypKod.equals("0") || anstallningstypKod.equals("") || anstallningstypKod == null);
    }
}
