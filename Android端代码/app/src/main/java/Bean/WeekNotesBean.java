package Bean;

import java.util.List;

public class WeekNotesBean {
    private String day;
    private List<DaynotesBean> daynotesBeanList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<DaynotesBean> getDaynotesBeanList() {
        return daynotesBeanList;
    }

    public void setDaynotesBeanList(List<DaynotesBean> daynotesBeanList) {
        this.daynotesBeanList = daynotesBeanList;
    }

    public WeekNotesBean(String day, List<DaynotesBean> daynotesBeanList) {
        this.day = day;
        this.daynotesBeanList = daynotesBeanList;
    }

    @Override
    public String toString() {
        return "WeekNotesBean{" +
                "day='" + day + '\'' +
                ", daynotesBeanList=" + daynotesBeanList +
                '}';
    }
}
