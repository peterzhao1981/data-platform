package com.mode.common;

import java.util.List;

/**
 * Created by zhaoweiwei on 17/1/26.
 */
public class Result {

    private String ret;
    private String message;
    private List<Dat> dat;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Dat> getDat() {
        return dat;
    }

    public void setDat(List<Dat> dat) {
        this.dat = dat;
    }

    public static class Dat {
        private String no;
        private Track track;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public Track getTrack() {
            return track;
        }

        public void setTrack(Track track) {
            this.track = track;
        }
    }

    public static class Track {
        private Z z0;
        private List<Z> z1;
        private List<Z> z2;

        public Z getZ0() {
            return z0;
        }

        public void setZ0(Z z0) {
            this.z0 = z0;
        }

        public List<Z> getZ1() {
            return z1;
        }

        public void setZ1(List<Z> z1) {
            this.z1 = z1;
        }

        public List<Z> getZ2() {
            return z2;
        }

        public void setZ2(List<Z> z2) {
            this.z2 = z2;
        }
    }

    public static class Z {
        private String a;
        private String b;
        private String c;
        private String d;
        private String z;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getZ() {
            return z;
        }

        public void setZ(String z) {
            this.z = z;
        }
    }
}
