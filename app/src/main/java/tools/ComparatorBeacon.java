package tools;

import org.altbeacon.beacon.Beacon;

import java.util.Comparator;

public class ComparatorBeacon implements Comparator<Beacon> {

    public int compare(Beacon o1, Beacon o2) {
        if (o1 == null)
            return -1;
        if (o2 == null)
            return 1;
        if (o1 == o2)
            return 0;
        if (o1.getDistance() > o2.getDistance() && o1.getRssi() < o2.getRssi()) {
            return 1;
        }
        if (o2.getDistance() == o1.getDistance()) {
            return 0;
        }
        return -1;
    }
}