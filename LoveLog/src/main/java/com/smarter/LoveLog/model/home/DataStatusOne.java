package com.smarter.LoveLog.model.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class DataStatusOne implements Serializable {
  private DataStatus status ;
    /**
     * address_id : 2611
     */

    private DataEntity data;


    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int address_id;

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public int getAddress_id() {
            return address_id;
        }
    }
}
