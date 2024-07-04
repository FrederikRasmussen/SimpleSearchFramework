package Exploration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Document {
    private final HashMap<String, ArrayList<String>> stringFields = new HashMap<String, ArrayList<String>>();
    private final HashMap<String, ArrayList<byte[]>> byteFields = new HashMap<String, ArrayList<byte[]>>();

    private final String id;
    private String versionField = "version";
    private String nameField = "name";

    public Document(String id) {
        this.id = id;
    }

    public void add(String name, String value) {
        stringFields
                .computeIfAbsent(name, key -> new ArrayList<>())
                .add(value);
    }

    public void add(String name, byte[] value) {
        byteFields
                .computeIfAbsent(name, key -> new ArrayList<>())
                .add(value);
    }

    public void replace(String name, String value) {
        remove(name);
        add(name, value);
    }

    public void replace(String name, byte[] value) {
        remove(name);
        add(name, value);
    }

    public void remove(String name)
    {
        stringFields.remove(name);
        byteFields.remove(name);
    }

    public void remove(String name, String value)
    {
        if (!stringFields.containsKey(name))
            return;
        stringFields.get(name).remove(value);
    }

    public void remove(String name, byte[] value)
    {
        if (!byteFields.containsKey(name))
            return;
        byteFields.get(name).remove(value);
    }

    public void removeBytes(String name)
    {
        byteFields.remove(name);
    }

    public ArrayList<String> values(String name) {
        return stringFields.get(name);
    }

    public String value(String name) {
        return stringFields.get(name).getFirst();
    }

    public JSONObject asJson()
    {
        var output = new JSONObject();
        output.put("id", id);
        if (version() != null)
            output.put("version", version());

        var fields = new JSONObject();
        for (var key : stringFields.keySet())
        {
            fields.put(key, stringFields.get(key));
        }
        output.put("fields", fields);

        var binaryFields = new JSONObject();
        var encoder = Base64.getEncoder();
        for (var key : byteFields.keySet())
        {
            fields.put(key, byteFields.get(key).stream().map(encoder::encode).map(String::new).toList());
        }
        output.put("binaryFields", binaryFields);

        return output;
    }

    public String id() {
        return id;
    }

    public String version() {
        if (!stringFields.containsKey(versionField))
            return null;
        return String.join(", ", values(versionField));
    }

    public void versionField(String name) {
        versionField = name;
    }
}
