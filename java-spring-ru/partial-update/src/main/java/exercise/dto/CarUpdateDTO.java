package exercise.dto;

import org.openapitools.jackson.nullable.JsonNullable;

// BEGIN
public class CarUpdateDTO {
    private JsonNullable<String> model;
    private JsonNullable<String> manufacturer;
    private JsonNullable<Integer> enginePower;

    public JsonNullable<String> getModel() {
        return model;
    }

    public void setModel(JsonNullable<String> model) {
        this.model = model;
    }

    public JsonNullable<String> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(JsonNullable<String> manufacturer) {
        this.manufacturer = manufacturer;
    }

    public JsonNullable<Integer> getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(JsonNullable<Integer> enginePower) {
        this.enginePower = enginePower;
    }
}
// END
