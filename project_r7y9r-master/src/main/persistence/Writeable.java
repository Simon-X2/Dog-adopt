package persistence;

import org.json.JSONObject;

//Interface for JsonObjects
public interface Writeable {
    JSONObject toJson();
}
