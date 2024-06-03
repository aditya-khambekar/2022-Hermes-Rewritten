package network;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Table {
    public final String name;
    // The networktable for the limelight
    private final NetworkTable table;
    
    // Cache the NetworkTableEntries so we don't have to keep accessing it
    private final Map<String, NetworkTableEntry> entryCache = new HashMap<>();

    protected Table(String name, NetworkTable table){
        this.name = name;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    private NetworkTableEntry getEntry(String key){
        if(entryCache.containsKey(key))
            return entryCache.get(key);
        var entry = table.getEntry(key);
        entryCache.put(key, entry);
        return entry;
    }

    private interface foo<T> {
        T apply(NetworkTableEntry entry, T defaultValue);
    }
    private interface bar<T> {
        void apply(NetworkTableEntry entry, T value);
    }

    private <T> T getEntryValue(String key, foo<T> func, T defaultValue){
        return func.apply(getEntry(key), defaultValue);
    }
    private <T> void setEntryValue(String key, bar<T> func, T value){
        func.apply(getEntry(key), value);
    }

    public boolean getBoolean(String key){
        return getEntryValue(key, NetworkTableEntry::getBoolean, false);
    }
    public void setBoolean(String key, boolean value){
        setEntryValue(key, NetworkTableEntry::setBoolean, value);
    }

    public double getDouble(String key){
        return getEntryValue(key, NetworkTableEntry::getDouble, 0.0);
    }
    public void setDouble(String key, double value){
        setEntryValue(key, NetworkTableEntry::setDouble, value);
    }
    
    public float getFloat(String key){
        return getEntryValue(key, NetworkTableEntry::getFloat, 0.0f);
    }
    public void setFloat(String key, float value) {
        setEntryValue(key, NetworkTableEntry::setFloat, value);
    }

    public int getInteger(String key){
        return (int) (long) getEntryValue(key, NetworkTableEntry::getInteger, 0L);
    }
    public void setInteger(String key, int value) {
        setEntryValue(key, NetworkTableEntry::setInteger, (long) value);
    }

    public String getString(String key){
        return getEntryValue(key, NetworkTableEntry::getString, "");
    }
    public void setString(String key, String value) {
        setEntryValue(key, NetworkTableEntry::setString, value);
    }

    public double[] getDoubleArray(String key) {
        return getEntryValue(key, NetworkTableEntry::getDoubleArray, new double[]{});
    }
    public void setDoubleArray(String key, double[] value) {
        setEntryValue(key, NetworkTableEntry::setDoubleArray, value);
    }

    public Map<String, String> getAllEntries(){
        var keys = table.getKeys();
        Map<String, String> res = new HashMap<>();
        for(var key : keys){
            var entry = getEntry(key);
            var str = switch (entry.getType()) {
                case kBoolean -> Boolean.toString(entry.getBoolean(false));
                case kDouble -> Double.toString(entry.getDouble(0));
                case kFloat -> Float.toString(entry.getFloat(0));
                case kString -> entry.getString("UNKNOWN STRING");
                case kDoubleArray -> Arrays.toString(entry.getDoubleArray(new double[]{}));
                default -> "UNKNOWN VALUE TYPE: " + entry.getType().toString();
            };
            res.put(key, str);
        }

        return res;
    }
}